/*
 * 
 * 
 */
package com.epfo.passbook2.dao;

import com.epfo.passbook2.common.fun;
import com.epfo.passbook2.common.util;
import static com.epfo.passbook2.common.util.getServer;
import com.epfo.passbook2.model.Balance;
import com.epfo.passbook2.model.OutputDB;
import com.epfo.passbook2.model.PassbookNewObj;
import com.epfo.passbook2.model.PassbookObj;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PassbookDAOImpl implements PassbookDAO {

    @Autowired
    DataSource dataSource;

    private JdbcTemplate getJdbcTemplate;

    @Override
    public OutputDB getMemberPassbook(HttpServletRequest request, String memberid) {

        String _message = "";
        OutputDB dbObject = new OutputDB();

        PassbookObj pbObjFinal = new PassbookObj();

        String header, status, message, accYear = "0";

        getJdbcTemplate = new JdbcTemplate();
        getJdbcTemplate.setDataSource(this.dataSource);

        if (getJdbcTemplate.getDataSource() == null) {
            dbObject.setSuccess(false);
            dbObject.setMessage("Data source exception.");
            dbObject.setDbObject(null);
            return dbObject;
        }

        Connection con = null;
        ResultSet rs = null;

        try {

            con = getJdbcTemplate.getDataSource().getConnection();

            String pkg = "";

            if (getServer(request).equalsIgnoreCase(util.server.production)) {
                pkg = "PKG_PASSBOOK_FINAL";
            } else {
                pkg = "PKG_PASSBOOK_FINAL_STAGE";
            }

            CallableStatement callableStatement;

            String proc = "{call " + pkg + ".GET_PASSBOOK(?,?,?,?,?)}";
            callableStatement = con.prepareCall(proc);
            callableStatement.setString(1, memberid);

            callableStatement.registerOutParameter(2, OracleTypes.VARCHAR);
            callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
            callableStatement.registerOutParameter(4, OracleTypes.VARCHAR);
            callableStatement.registerOutParameter(5, OracleTypes.CURSOR);
            callableStatement.execute();

            status = (String) callableStatement.getObject(2);
            message = (String) callableStatement.getObject(3);
            header = (String) callableStatement.getObject(4);

            int minYear = 2020;
            int maxYear = 0;
            int tyr = 0;

            if (status.equalsIgnoreCase("1")) {
                dbObject.setSuccess(false);
                dbObject.setMessage(message);
                return dbObject;
            }

            PassbookObj pbObj = new PassbookObj();
            PassbookObj.PassbookYearData pbData = new PassbookObj.PassbookYearData();
            ArrayList<PassbookObj.PassbookYearData> pbDataList = new ArrayList();
            PassbookObj.PassbookYearData.PassbookModel passbook;

            ArrayList<PassbookObj.PassbookYearData.PassbookModel> passbookList = new ArrayList();

            PassbookNewObj.PassbookOtherData passbookOtherData = new PassbookNewObj.PassbookOtherData();

            rs = (ResultSet) callableStatement.getObject(5);

            while (rs.next()) {
                passbook = new PassbookObj.PassbookYearData.PassbookModel();

                passbook.setFlag(rs.getString("FLAG"));
                passbook.setAccYear(rs.getString("ACC_YEAR"));
                passbook.setFinYear(rs.getString("FIN_YEAR"));
                passbook.setTransDate(rs.getString("TR_DATE_MY"));
                passbook.setTransDateDmy(rs.getString("TR_DATE_DMY"));
                passbook.setEpfWages(rs.getString("EPF_WAGES"));
                passbook.setEpsWages(rs.getString("EPS_WAGES"));
                passbook.setEeShare(rs.getString("CR_EE_SHARE"));
                passbook.setErShare(rs.getString("CR_ER_SHARE"));

                if (rs.getString("FLAG").equalsIgnoreCase("1")) {
                    passbook.setEeN(rs.getString("CR_EE_SHARE"));
                    passbook.setEeT("0");
                }

                passbook.setPenShare(rs.getString("CR_PEN_BAL"));
                passbook.setNcp(rs.getString("NCP"));
                passbook.setDrcrFalg(rs.getString("DB_CR_FLAG"));
                passbook.setParticular(rs.getString("PARTICULAR"));
                passbook.setMonthYear(rs.getString("MONTH_YEAR"));
                passbook.setTableName(rs.getString("TABLE_NAME"));
                passbook.setUpdDate(rs.getString("TR_APPROVED"));

                tyr = Integer.parseInt(rs.getString("ACC_YEAR"));

                if (maxYear <= tyr) {
                    maxYear = tyr;
                }

                if (minYear >= tyr) {
                    minYear = tyr;
                }

                passbookList.add(passbook);
                //passbookData.add(passbook);

            }
            rs.close();

            pbData.setPassbook(passbookList);
            pbDataList.add(pbData);

            String[] header1 = header.split("#");

            if (header1.length != 12) {
                if (con != null) {
                    con.close();
                }
                if (rs != null) {
                    rs.close();
                }
                dbObject.setSuccess(false);
                dbObject.setMessage("Error in header data!");
                return dbObject;
            }

            passbookOtherData.setMemberName(header1[0]);
            passbookOtherData.setMemberDob(header1[1]);
            passbookOtherData.setEstName(header1[2]);
            passbookOtherData.setFhName(header1[3]);
            passbookOtherData.setFhFlag(header1[4]);
            passbookOtherData.setDoj(header1[5]);
            passbookOtherData.setOfficeName(header1[6]);
            passbookOtherData.setUan(header1[7]);
            passbookOtherData.setAadhaarVerified(Integer.parseInt(header1[8]));
            passbookOtherData.setFinalDob(header1[9]);
            passbookOtherData.setInt19(Integer.parseInt(header1[10]));
            passbookOtherData.setLastCont(header1[11]);

            pbObj.setMemberId(memberid);
            pbObj.setPassbookYearData(pbDataList);

            PassbookNewObj pbObjNew;

            OutputDB pb1Outdb = genPassbook1(memberid, minYear, maxYear, passbookList);
            if (!pb1Outdb.isSuccess()) {
                return pb1Outdb;
            }
            pbObjNew = (PassbookNewObj) pb1Outdb.getDbObject();

            //
//            ArrayList<PassbookNewObj.PassbookYearly> dataYearly = pbObjNew.getPassbookYearly();
//            int total = 0;
//            SimpleDateFormat dt2 = new SimpleDateFormat("MMM-yyyy");
//            for (PassbookNewObj.PassbookYearly py : dataYearly) {
//                ArrayList<PassbookNewObj.PassbookTrans> dataTrans = py.getPassbookTrans();
//                for (PassbookNewObj.PassbookTrans pt : dataTrans) {
//                    System.out.println(pt.getFlag() + " " + dt2.format(pt.getWageMonth()) + " " + fun.lpad(pt.getCount(), 3, " ") + " " + " " + fun.lpad(pt.getEpfWages(), 10, " ") + fun.lpad(pt.getEpsWages(), 6, " ") + " " + fun.lpad(pt.getEeShare(), 10, " ") + " [ " + fun.lpad(pt.getEeShareNonTaxable(), 8, " ") + " " + fun.lpad(pt.getEeShareTaxable(), 8, " ") + " ] " + fun.lpad(pt.getErShare(), 10, " ") + " " + fun.lpad(pt.getEeWith(), 10, " ") + " " + fun.lpad(pt.getErWith(), 10, " ") + " " + fun.lpad(pt.getPenShare(), 10, " "));
//                }
//            }
            //
            OutputDB pb2Outdb = genPassbook2(pbObjNew, minYear, maxYear);
            if (!pb2Outdb.isSuccess()) {
                return pb2Outdb;
            }
            PassbookNewObj passbookNewObj = (PassbookNewObj) pb2Outdb.getDbObject();
            passbookNewObj.setPassbookOtherData(passbookOtherData);

            dbObject.setDbObject(passbookNewObj);
            dbObject.setSuccess(true);

        } catch (Exception e) {
            dbObject.setSuccess(false);
            dbObject.setMessage(e.toString());
            dbObject.setDbObject(null);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                System.out.println("PbDAOImpl.getMemberPassbook() : " + e.toString());
            }
        }

        return dbObject;
    }

    private static OutputDB genPassbook1(String memberId, int minYear, int maxYear, ArrayList<PassbookObj.PassbookYearData.PassbookModel> passbookList) {

        OutputDB dbOutput = new OutputDB();

        PassbookNewObj pbNewObj = new PassbookNewObj();

        ArrayList<PassbookNewObj.PassbookTrans> tmpTrans;
        ArrayList<PassbookNewObj.PassbookYearly> tmpYearly = new ArrayList();
        PassbookNewObj.PassbookYearly pbYearly;

        try {

            for (int yr = minYear; yr <= maxYear; yr++) {
                OutputDB outDb = genYearlyPassbook1(yr, passbookList);
                if (!outDb.isSuccess()) {
                    return dbOutput;
                }
                pbYearly = (PassbookNewObj.PassbookYearly) outDb.getDbObject();

                pbYearly.setYear(yr);
                tmpYearly.add(pbYearly);
            }

//// test case 
//
//            int yr = 2015;
//            pbYearly = new PassbookNewObj.PassbookYearly();
//            OutputDB outDb = genYearlyPassbook1(yr, passbookList);
//            //System.out.println(yr);
//            if (!outDb.isSuccess()) {
//                //System.out.println("ERR");
//                return dbOutput;
//            }
//            tmpTrans = (ArrayList<PassbookNewObj.PassbookTrans>) outDb.getDbObject();           
//            
//
//            pbYearly.setYear(yr);
//            pbYearly.setPassbookTrans(tmpTrans);
//            tmpYearly.add(pbYearly);
//// test case 
            pbNewObj.setMemberId(memberId);
            pbNewObj.setPassbookYearly(tmpYearly);

            dbOutput.setDbObject(pbNewObj);
            dbOutput.setSuccess(true);

        } catch (Exception e) {
            dbOutput.setSuccess(false);
            dbOutput.setMessage(e.toString());
            return dbOutput;
        }

        return dbOutput;
    }

    private static OutputDB genYearlyPassbook1(int year, ArrayList<PassbookObj.PassbookYearData.PassbookModel> passbookList) {

        OutputDB dbOutput = new OutputDB();

        ArrayList<PassbookNewObj.PassbookTrans> outPbTrans = new ArrayList();
        ArrayList<PassbookNewObj.AllTrans> fullTrans = new ArrayList();

        try {

            int mn = 4;
            String mn1 = "";
            int yr = year;

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdfym = new SimpleDateFormat("yyyyMM");

            String ym = year + "04";

            Long epfWages = Long.valueOf(0), epsWages = Long.valueOf(0);
            Long eeShare = Long.valueOf(0), erShare = Long.valueOf(0);
            Long eeShareNonTaxable = Long.valueOf(0), eeShareTaxable = Long.valueOf(0);
            Long eeWith = Long.valueOf(0), erWith = Long.valueOf(0);
            Long ncp = Long.valueOf(0);
            Long penShare = Long.valueOf(0);

            String transDmy = "";

            ArrayList<String> particulars = new ArrayList();
            int count = 0;
            PassbookNewObj.PassbookTrans pt;

            Boolean obFound = false;

            // OB
            for (PassbookObj.PassbookYearData.PassbookModel pm : passbookList) {
                if (!pm.getAccYear().equalsIgnoreCase(String.valueOf(year))) {
                    continue;
                }

                if (pm.getFlag().equalsIgnoreCase("1")) {
                    obFound = true;
                    particulars = new ArrayList();
                    particulars.add(pm.getParticular());
                    eeShare = eeShare + Long.valueOf(pm.getEeShare());
                    erShare = erShare + Long.valueOf(pm.getErShare());
                    transDmy = pm.getTransDateDmy();
                    count++;

                    PassbookNewObj.AllTrans ft = new PassbookNewObj.AllTrans();
                    ft.setFlag(util.code.obb);
                    ft.setYm(Long.valueOf(ym));
                    ft.setWageMonth(sdf.parse("01-04-" + year));
                    ft.setParticular(pm.getParticular());
                    ft.setTransDmy(pm.getTransDateDmy());
                    ft.setEpfWages((pm.getEpfWages() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEpfWages()));
                    ft.setEpsWages((pm.getEpsWages() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEpsWages()));
                    ft.setEeShare((pm.getEeShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEeShare()));
                    ft.setErShare((pm.getErShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getErShare()));
                    ft.setEeWith(Long.valueOf(0));
                    ft.setErWith(Long.valueOf(0));
                    ft.setPenShare((pm.getPenShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getPenShare()));
                    ft.setNcp(Long.valueOf(0));
                    fullTrans.add(ft);
                }

            }

            if (obFound) {
                pt = new PassbookNewObj.PassbookTrans();

                pt.setWageMonth(sdf.parse("01-04-" + year));
                pt.setFlag(util.code.obb);
                pt.setParticular(particulars);
                pt.setCount(count);

                if (count == 1) {
                    pt.setTransDmy(transDmy);
                }

                pt.setTransMonth(Long.valueOf(ym));
                pt.setEpfWages(Long.valueOf(0));
                pt.setEpsWages(Long.valueOf(0));

                pt.setEeShare(eeShare);
                pt.setErShare(erShare);

                pt.setEeShareNonTaxable(Long.valueOf(0));
                pt.setEeShareTaxable(Long.valueOf(0));

                pt.setEeWith(Long.valueOf(0));
                pt.setErWith(Long.valueOf(0));

                pt.setPenShare(Long.valueOf(0));
                pt.setNcp(Long.valueOf(0));

                outPbTrans.add(pt);
            }
            //OB
            ////
            //APE
            eeShare = Long.valueOf(0);
            erShare = Long.valueOf(0);
            penShare = Long.valueOf(0);
            count = 0;
            transDmy = "";
            for (PassbookObj.PassbookYearData.PassbookModel pm : passbookList) {
                if (!pm.getAccYear().equalsIgnoreCase(String.valueOf(year))) {
                    continue;
                }

                if (pm.getFlag().equalsIgnoreCase("10")) {
                    particulars = new ArrayList();
                    particulars.add(pm.getParticular());

                    eeShare = eeShare + Long.valueOf(pm.getEeShare());
                    erShare = erShare + Long.valueOf(pm.getErShare());
                    penShare = penShare + Long.valueOf(pm.getPenShare());
                    transDmy = pm.getTransDateDmy();
                    count++;

                    PassbookNewObj.AllTrans ft = new PassbookNewObj.AllTrans();
                    ft.setFlag(util.code.ape);
                    ft.setYm(Long.valueOf(ym));
                    ft.setWageMonth(sdf.parse("01-04-" + year));
                    ft.setParticular(pm.getParticular());
                    ft.setTransDmy(pm.getTransDateDmy());
                    ft.setEpfWages((pm.getEpfWages() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEpfWages()));
                    ft.setEpsWages((pm.getEpsWages() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEpsWages()));
                    ft.setEeShare((pm.getEeShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEeShare()));
                    ft.setErShare((pm.getErShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getErShare()));
                    ft.setEeWith(Long.valueOf(0));
                    ft.setErWith(Long.valueOf(0));
                    ft.setPenShare((pm.getPenShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getPenShare()));
                    ft.setNcp(Long.valueOf(0));
                    fullTrans.add(ft);
                }

            }
            if (count > 0) {
                pt = new PassbookNewObj.PassbookTrans();
                pt.setWageMonth(sdf.parse("01-04-" + year));
                pt.setFlag(util.code.ape);
                pt.setParticular(particulars);
                pt.setCount(count);
                if (count == 1) {
                    pt.setTransDmy(transDmy);
                }
                pt.setTransMonth(Long.valueOf(ym));
                pt.setEpfWages(Long.valueOf(0));
                pt.setEpsWages(Long.valueOf(0));

                pt.setEeShare(eeShare);
                pt.setErShare(erShare);

                pt.setEeShareNonTaxable(Long.valueOf(0));
                pt.setEeShareTaxable(Long.valueOf(0));

                pt.setEeWith(Long.valueOf(0));
                pt.setErWith(Long.valueOf(0));

                pt.setPenShare(penShare);
                pt.setNcp(Long.valueOf(0));

                outPbTrans.add(pt);
            }
            //APE
            /////
            //CONT-WITH
            for (int i = 1; i <= 12; i++) {

                epfWages = Long.valueOf(0);
                epsWages = Long.valueOf(0);
                eeShare = Long.valueOf(0);
                erShare = Long.valueOf(0);
                eeShareNonTaxable = Long.valueOf(0);
                eeShareTaxable = Long.valueOf(0);
                eeWith = Long.valueOf(0);
                erWith = Long.valueOf(0);
                ncp = Long.valueOf(0);
                penShare = Long.valueOf(0);
                count = 0;
                transDmy = "";

                mn1 = fun.lpad(String.valueOf(mn), 2);
                //System.out.println(mn1 + " - " + yr);
                ym = yr + mn1;

                for (PassbookObj.PassbookYearData.PassbookModel pm : passbookList) {
                    if (!pm.getAccYear().equalsIgnoreCase(String.valueOf(year))) {
                        continue;
                    }

                    if (pm.getFlag().equalsIgnoreCase("20") || pm.getFlag().equalsIgnoreCase("21") || pm.getFlag().equalsIgnoreCase("22") || pm.getFlag().equalsIgnoreCase("30") || pm.getFlag().equalsIgnoreCase("31")) {

                        if (pm.getMonthYear().equalsIgnoreCase(ym)) {

                            if (pm.getDrcrFalg().equalsIgnoreCase("C")) {

                                particulars = new ArrayList();
                                particulars.add(pm.getParticular());

                                eeShare = eeShare + Long.valueOf(pm.getEeShare());
                                erShare = erShare + Long.valueOf(pm.getErShare());
                                epfWages = epfWages + Long.valueOf(pm.getEpfWages());
                                epsWages = epsWages + Long.valueOf(pm.getEpsWages());
                                ncp = ncp + Long.valueOf(pm.getNcp());
                                penShare = penShare + Long.valueOf(pm.getPenShare());
                                transDmy = pm.getTransDateDmy();

                                count++;

                                LocalDate ldWm = fun.toLocalDate(sdfym.parse(ym));
                                Date wm = fun.toDateFromLocal(ldWm.plusMonths(-1));
                                PassbookNewObj.AllTrans ft = new PassbookNewObj.AllTrans();
                                ft.setFlag(util.code.mcw);
                                ft.setYm(Long.valueOf(ym));
                                ft.setWageMonth(wm);
                                ft.setParticular(pm.getParticular());
                                ft.setTransDmy(pm.getTransDateDmy());
                                ft.setEpfWages((pm.getEpfWages() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEpfWages()));
                                ft.setEpsWages((pm.getEpsWages() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEpsWages()));
                                ft.setEeShare((pm.getEeShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEeShare()));

                                //ft.setEeShareNonTaxable((pm.getEeShareN == null) ? Long.valueOf(0) : Long.valueOf(pm.getEeShare()));
                                //ft.setEeShareTaxable((pm.getEeShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEeShare()));
                                ft.setErShare((pm.getErShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getErShare()));
                                ft.setEeWith(Long.valueOf(0));
                                ft.setErWith(Long.valueOf(0));
                                ft.setPenShare((pm.getPenShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getPenShare()));
                                ft.setNcp(Long.valueOf(0));
                                fullTrans.add(ft);
                            }

                            if (pm.getDrcrFalg().equalsIgnoreCase("D")) {

                                particulars = new ArrayList();
                                particulars.add(pm.getParticular());

                                eeWith = eeWith + Long.valueOf(pm.getEeShare());
                                erWith = erWith + Long.valueOf(pm.getErShare());
                                transDmy = pm.getTransDateDmy();
                                count++;

                                LocalDate ldWm = fun.toLocalDate(sdfym.parse(ym));
                                Date wm = fun.toDateFromLocal(ldWm.plusMonths(-1));
                                PassbookNewObj.AllTrans ft = new PassbookNewObj.AllTrans();
                                ft.setFlag(util.code.mcw);
                                ft.setYm(Long.valueOf(ym));
                                ft.setWageMonth(wm);
                                ft.setParticular(pm.getParticular());
                                ft.setTransDmy(pm.getTransDateDmy());
                                ft.setEpfWages((pm.getEpfWages() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEpfWages()));
                                ft.setEpsWages((pm.getEpsWages() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEpsWages()));
                                ft.setEeShare(Long.valueOf(0));
                                ft.setErShare(Long.valueOf(0));
                                ft.setEeWith((pm.getEeShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEeShare()));
                                ft.setErWith((pm.getErShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getErShare()));
                                ft.setPenShare((pm.getPenShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getPenShare()));
                                ft.setNcp(Long.valueOf(0));
                                fullTrans.add(ft);
                            }

                        }

                    }

                }

                mn++;
                if (mn == 13) {
                    mn = 1;
                    yr++;

                }

                LocalDate ldWm = fun.toLocalDate(sdfym.parse(ym));
                Date wm = fun.toDateFromLocal(ldWm.plusMonths(-1));

                pt = new PassbookNewObj.PassbookTrans();
                pt.setWageMonth(wm);
                pt.setFlag(util.code.mcw);
                pt.setParticular(particulars);
                pt.setCount(count);
                if (count == 1) {
                    pt.setTransDmy(transDmy);
                }
                pt.setTransMonth(Long.valueOf(ym));
                pt.setEpfWages(epfWages);
                pt.setEpsWages(epsWages);

                pt.setEeShare(eeShare);
                pt.setErShare(erShare);

                pt.setEeShareNonTaxable(eeShareNonTaxable);
                pt.setEeShareTaxable(eeShareTaxable);

                pt.setEeWith(eeWith);
                pt.setErWith(erWith);

                pt.setPenShare(penShare);
                pt.setNcp(ncp);

                outPbTrans.add(pt);

            }
            //CONT-WITH
            /////
            //INT
            eeShare = Long.valueOf(0);
            erShare = Long.valueOf(0);
            count = 0;
            transDmy = "";
            for (PassbookObj.PassbookYearData.PassbookModel pm : passbookList) {
                if (!pm.getAccYear().equalsIgnoreCase(String.valueOf(year))) {
                    continue;
                }

                if (pm.getFlag().equalsIgnoreCase("90") || pm.getFlag().equalsIgnoreCase("91")) {
                    particulars = new ArrayList();
                    particulars.add(pm.getParticular());

                    eeShare = eeShare + Long.valueOf(pm.getEeShare());
                    erShare = erShare + Long.valueOf(pm.getErShare());
                    transDmy = pm.getTransDateDmy();
                    count++;

                    PassbookNewObj.AllTrans ft = new PassbookNewObj.AllTrans();
                    ft.setFlag(util.code.interest);
                    ft.setYm(Long.valueOf(ym));
                    ft.setWageMonth(sdf.parse("31-03-" + (year + 1)));
                    ft.setParticular(pm.getParticular());
                    ft.setTransDmy(pm.getTransDateDmy());
                    ft.setEpfWages((pm.getEpfWages() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEpfWages()));
                    ft.setEpsWages((pm.getEpsWages() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEpsWages()));
                    ft.setEeShare((pm.getEeShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEeShare()));
                    ft.setErShare((pm.getErShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getErShare()));
                    ft.setEeWith(Long.valueOf(0));
                    ft.setErWith(Long.valueOf(0));
                    ft.setPenShare((pm.getPenShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getPenShare()));
                    ft.setNcp(Long.valueOf(0));
                    fullTrans.add(ft);
                }
                if (pm.getFlag().equalsIgnoreCase("190")) {
                    particulars = new ArrayList();
                    particulars.add(pm.getParticular());

                    eeShareTaxable = eeShareTaxable + Long.valueOf(pm.getEeShare());
                    transDmy = pm.getTransDateDmy();
                    count++;

                    PassbookNewObj.AllTrans ft = new PassbookNewObj.AllTrans();
                    ft.setFlag(util.code.interest);
                    ft.setYm(Long.valueOf(ym));
                    ft.setWageMonth(sdf.parse("31-03-" + (year + 1)));
                    ft.setParticular(pm.getParticular());
                    ft.setTransDmy(pm.getTransDateDmy());
                    ft.setEpfWages((pm.getEpfWages() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEpfWages()));
                    ft.setEpsWages((pm.getEpsWages() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEpsWages()));
                    ft.setEeShare((pm.getEeShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEeShare()));
                    ft.setErShare((pm.getErShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getErShare()));
                    ft.setEeWith(Long.valueOf(0));
                    ft.setErWith(Long.valueOf(0));
                    ft.setPenShare((pm.getPenShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getPenShare()));
                    ft.setNcp(Long.valueOf(0));
                    fullTrans.add(ft);
                }

            }

            pt = new PassbookNewObj.PassbookTrans();
            pt.setWageMonth(sdf.parse("31-03-" + (year + 1)));
            pt.setFlag(util.code.interest);
            pt.setParticular(particulars);
            pt.setCount(count);
            if (count == 1) {
                pt.setTransDmy(transDmy);
            }
            pt.setTransMonth(Long.valueOf(ym));
            pt.setEpfWages(Long.valueOf(0));
            pt.setEpsWages(Long.valueOf(0));

            pt.setEeShare(eeShare);
            pt.setErShare(erShare);

            pt.setEeShareNonTaxable(eeShare - eeShareTaxable);
            pt.setEeShareTaxable(eeShareTaxable);

            pt.setEeWith(Long.valueOf(0));
            pt.setErWith(Long.valueOf(0));

            pt.setPenShare(Long.valueOf(0));
            pt.setNcp(Long.valueOf(0));

            outPbTrans.add(pt);
            //INT
            /////
            //TIN/VDR
            eeShare = Long.valueOf(0);
            erShare = Long.valueOf(0);
            penShare = Long.valueOf(0);
            count = 0;
            transDmy = "";
            for (PassbookObj.PassbookYearData.PassbookModel pm : passbookList) {
                if (!pm.getAccYear().equalsIgnoreCase(String.valueOf(year))) {
                    continue;
                }
                if (pm.getFlag().equalsIgnoreCase("40") || pm.getFlag().equalsIgnoreCase("41") || pm.getFlag().equalsIgnoreCase("42")) {
                    if (pm.getParticular().contains("Deduction of TDS")) {
                        //eeShare = eeShare + 0;
                        //eeShareTaxable = eeShareTaxable + Long.valueOf(pm.getEeShare());
                        //penShare = penShare + 0;
                        //pm.setEeShare("0");
                        continue;
                    }
                    particulars = new ArrayList();
                    particulars.add(pm.getParticular());

                    eeShare = eeShare + Long.valueOf(pm.getEeShare());
                    erShare = erShare + Long.valueOf(pm.getErShare());
                    penShare = penShare + Long.valueOf(pm.getPenShare());
                    transDmy = pm.getTransDateDmy();

                    count++;

                    PassbookNewObj.AllTrans ft = new PassbookNewObj.AllTrans();
                    ft.setFlag(util.code.tin);
                    ft.setYm(Long.valueOf(ym));
                    ft.setWageMonth(sdf.parse("31-03-" + (year + 1)));
                    ft.setParticular(pm.getParticular());
                    ft.setTransDmy(pm.getTransDateDmy());
                    ft.setEpfWages((pm.getEpfWages() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEpfWages()));
                    ft.setEpsWages((pm.getEpsWages() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEpsWages()));
                    ft.setEeShare((pm.getEeShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEeShare()));
                    ft.setErShare((pm.getErShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getErShare()));
                    ft.setEeWith(Long.valueOf(0));
                    ft.setErWith(Long.valueOf(0));
                    ft.setPenShare((pm.getPenShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getPenShare()));
                    ft.setNcp(Long.valueOf(0));
                    fullTrans.add(ft);
                }

            }
            if (count > 0) {
                pt = new PassbookNewObj.PassbookTrans();
                pt.setWageMonth(sdf.parse("31-03-" + (year + 1)));
                pt.setFlag(util.code.tin);
                pt.setParticular(particulars);
                pt.setCount(count);
                if (count == 1) {
                    pt.setTransDmy(transDmy);
                }
                pt.setTransMonth(Long.valueOf(ym));
                pt.setEpfWages(Long.valueOf(0));
                pt.setEpsWages(Long.valueOf(0));

                pt.setEeShare(eeShare);
                pt.setErShare(erShare);

                pt.setEeShareNonTaxable(Long.valueOf(0));
                pt.setEeShareTaxable(Long.valueOf(0));

                pt.setEeWith(Long.valueOf(0));
                pt.setErWith(Long.valueOf(0));

                pt.setPenShare(penShare);
                pt.setNcp(Long.valueOf(0));

                outPbTrans.add(pt);
            }
            //TIN/VDR            
            /////
            if (year >= 2021) {
                //TDS
                eeShareNonTaxable = Long.valueOf(0);
                eeShareTaxable = Long.valueOf(0);
                count = 0;
                transDmy = "";
                for (PassbookObj.PassbookYearData.PassbookModel pm : passbookList) {
                    if (!pm.getAccYear().equalsIgnoreCase(String.valueOf(year))) {
                        continue;
                    }
                    if (pm.getFlag().equalsIgnoreCase("40") || pm.getFlag().equalsIgnoreCase("41") || pm.getFlag().equalsIgnoreCase("42")) {
                        if (pm.getParticular().contains("Deduction of TDS")) {
                            particulars = new ArrayList();
                            particulars.add(pm.getParticular());

                            eeShareTaxable = eeShareTaxable + Long.valueOf(pm.getEeShare());
                            transDmy = pm.getTransDateDmy();

                            count++;

                            PassbookNewObj.AllTrans ft = new PassbookNewObj.AllTrans();
                            ft.setFlag(util.code.tds);
                            ft.setYm(Long.valueOf(ym));
                            ft.setWageMonth(sdf.parse("31-03-" + (year + 1)));
                            ft.setParticular(pm.getParticular());
                            ft.setTransDmy(pm.getTransDateDmy());
                            ft.setEpfWages((pm.getEpfWages() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEpfWages()));
                            ft.setEpsWages((pm.getEpsWages() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEpsWages()));
                            ft.setEeShare((pm.getEeShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getEeShare()));
                            ft.setErShare((pm.getErShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getErShare()));
                            ft.setEeWith(Long.valueOf(0));
                            ft.setErWith(Long.valueOf(0));
                            ft.setPenShare((pm.getPenShare() == null) ? Long.valueOf(0) : Long.valueOf(pm.getPenShare()));
                            ft.setNcp(Long.valueOf(0));
                            fullTrans.add(ft);
                        }
                    }

                }
                if (count > 0) {
                    pt = new PassbookNewObj.PassbookTrans();
                    pt.setWageMonth(sdf.parse("31-03-" + (year + 1)));
                    pt.setFlag(util.code.tds);
                    pt.setParticular(particulars);
                    pt.setCount(count);
                    if (count == 1) {
                        pt.setTransDmy(transDmy);
                    }
                    pt.setTransMonth(Long.valueOf(ym));
                    pt.setEpfWages(Long.valueOf(0));
                    pt.setEpsWages(Long.valueOf(0));

                    pt.setEeShare(Long.valueOf(0));
                    pt.setErShare(Long.valueOf(0));

                    pt.setEeShareNonTaxable(Long.valueOf(0));
                    pt.setEeShareTaxable(eeShareTaxable);

                    pt.setEeWith(Long.valueOf(0));
                    pt.setErWith(Long.valueOf(0));

                    pt.setPenShare(penShare);
                    pt.setNcp(Long.valueOf(0));

                    outPbTrans.add(pt);
                }
                //TDS

            }

            PassbookNewObj.PassbookYearly pbYearly = new PassbookNewObj.PassbookYearly();
            pbYearly.setAllTrans(fullTrans);
            pbYearly.setPassbookTrans(outPbTrans);

            dbOutput.setDbObject(pbYearly);
            dbOutput.setSuccess(true);

        } catch (ParseException e) {
            dbOutput.setSuccess(false);
            dbOutput.setMessage(e.toString());
            return dbOutput;
        }

        return dbOutput;
    }

    private static OutputDB genPassbook2(PassbookNewObj pbObj, int minYear, int maxYear) {

        OutputDB dbOutput = new OutputDB();

        PassbookNewObj pbObjOut = new PassbookNewObj();

        try {

            ArrayList<PassbookNewObj.PassbookYearly> passYearly = pbObj.getPassbookYearly();
            ArrayList<PassbookNewObj.PassbookYearly> passYearlyNew = new ArrayList();

            for (int yr = minYear; yr <= maxYear; yr++) {
                for (PassbookNewObj.PassbookYearly py : passYearly) {

                    if (py.getYear() == yr) {
                        OutputDB pyOdb = genYearlyPassbook2(yr, py.getPassbookTrans());
                        if (!pyOdb.isSuccess()) {
                            return pyOdb;
                        }

                        ArrayList<PassbookNewObj.PassbookTrans> pbTransNew = (ArrayList<PassbookNewObj.PassbookTrans>) pyOdb.getDbObject();

                        PassbookNewObj.PassbookYearly pyNew = new PassbookNewObj.PassbookYearly();
                        pyNew.setYear(yr);
                        pyNew.setPassbookTrans(pbTransNew);
                        pyNew.setAllTrans(py.getAllTrans());
                        passYearlyNew.add(pyNew);

                        SimpleDateFormat sdfym = new SimpleDateFormat("yyyyMM");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                        for (PassbookNewObj.PassbookTrans ptn : pbTransNew) {
                            if (ptn.getFlag().equalsIgnoreCase(util.code.cbb)) {

                                PassbookNewObj.PassbookTrans ptnob = new PassbookNewObj.PassbookTrans();

                                String tm = String.valueOf(ptn.getTransMonth());
                                Date tmDate = sdfym.parse(tm);
                                LocalDate localTm = fun.toLocalDate(tmDate);
                                int tYear = localTm.getYear();

                                ptnob.setWageMonth(sdf.parse("01-04-" + tYear));
                                ptnob.setFlag(util.code.obb);

                                ArrayList<String> particulars = new ArrayList();
                                particulars.add("Opening Balance as on 01-04-" + tYear);

                                ptnob.setParticular(particulars);
                                ptnob.setCount(1);
                                ptnob.setTransMonth(Long.valueOf(tYear + "04"));
                                ptnob.setEpfWages(Long.valueOf(0));
                                ptnob.setEpsWages(Long.valueOf(0));

                                ptnob.setEeShare(ptn.getEeShare());
                                ptnob.setErShare(ptn.getErShare());

                                ptnob.setEeShareNonTaxable(ptn.getEeShareNonTaxable());
                                ptnob.setEeShareTaxable(ptn.getEeShareTaxable());

                                ptnob.setEeWith(ptn.getEeWith());
                                ptnob.setErWith(ptn.getErWith());

                                ptnob.setPenShare(ptn.getPenShare());
                                ptnob.setNcp(ptn.getNcp());

                                py.getPassbookTrans().add(ptnob);
                                OutputDB odb = addNextOB(pbObj, yr + 1, ptnob);
                                if (!odb.isSuccess()) {
                                    return odb;
                                }
                                break;
                            }
                        }

                    }

                }
            }

            pbObjOut.setMemberId(pbObj.getMemberId());
            pbObjOut.setPassbookYearly(passYearlyNew);

            dbOutput.setSuccess(true);
            dbOutput.setDbObject(pbObjOut);
        } catch (NumberFormatException | ParseException e) {
            dbOutput.setMessage(e.toString());
            dbOutput.setSuccess(false);
            return dbOutput;
        }

        return dbOutput;
    }

    private static OutputDB addNextOB(PassbookNewObj pbObj, int year, PassbookNewObj.PassbookTrans ptnob) {
        OutputDB dbOutput = new OutputDB();

        ArrayList<PassbookNewObj.PassbookYearly> passYearly = pbObj.getPassbookYearly();

        try {

            for (PassbookNewObj.PassbookYearly py : passYearly) {
                if (py.getYear() == year) {
                    py.getPassbookTrans().add(ptnob);
                    break;
                }
            }
            dbOutput.setSuccess(true);
        } catch (Exception e) {
            dbOutput.setMessage(e.toString());
            dbOutput.setSuccess(false);
            return dbOutput;
        }

        return dbOutput;
    }

    private static OutputDB genYearlyPassbook2(int year, ArrayList<PassbookNewObj.PassbookTrans> pbTrans) {
        OutputDB dbOutput = new OutputDB();

        ArrayList<PassbookNewObj.PassbookTrans> pbTransNew = new ArrayList();

        Long eeShare = Long.valueOf(0);
        Long erShare = Long.valueOf(0);
        Long penShare = Long.valueOf(0);

        Long ibbN = Long.valueOf(0);
        Long ibbT = Long.valueOf(0);

        Long ibbNob = Long.valueOf(0);
        Long ibbTob = Long.valueOf(0);

        Long interestTaxable = Long.valueOf(0);
        Long interestNonTaxable = Long.valueOf(0);

        Long eeNonTaxable = Long.valueOf(0);
        Long eeTaxable = Long.valueOf(0);

        Long cumContNonTaxable = Long.valueOf(0);
        Long cumContTaxable = Long.valueOf(0);

        //Long eeWith = Long.valueOf(0);
        //Long erWith = Long.valueOf(0);
        try {

            for (PassbookNewObj.PassbookTrans pt : pbTrans) {
                if (pt.getFlag().equalsIgnoreCase("OBB")) {
                    eeShare = eeShare + pt.getEeShare();
                    erShare = erShare + pt.getErShare();
                    penShare = penShare + pt.getPenShare();

                    if (year >= 2021) {

                        if (year == 2021) {
                            pt.setEeShareNonTaxable(pt.getEeShare());
                        }

                        eeNonTaxable = eeNonTaxable + pt.getEeShareNonTaxable();
                        eeTaxable = eeTaxable + pt.getEeShareTaxable();

                        ibbN = ibbN + eeNonTaxable;
                        ibbT = ibbT + eeTaxable;
                        ibbNob = ibbNob + eeNonTaxable;
                        ibbTob = ibbTob + eeTaxable;

                    }

                    pbTransNew.add(pt);
                }
            }

            for (PassbookNewObj.PassbookTrans pt : pbTrans) {
                if (pt.getFlag().equalsIgnoreCase("APE")) {
                    eeShare = eeShare + pt.getEeShare();
                    erShare = erShare + pt.getErShare();
                    penShare = penShare + pt.getPenShare();
                    pbTransNew.add(pt);
                }
            }

            String mn1;
            String ym;
            int mn = 4;
            int yr = year;

            Long contN;

            for (int i = 1; i <= 12; i++) {
                mn1 = fun.lpad(String.valueOf(mn), 2);
                ym = yr + mn1;

                contN = Long.valueOf(0);

                for (PassbookNewObj.PassbookTrans pt : pbTrans) {
                    if (pt.getFlag().equalsIgnoreCase("MCW") && (pt.getTransMonth().compareTo(Long.valueOf(ym)) == 0)) {

                        eeShare = (eeShare + pt.getEeShare()) - pt.getEeWith();
                        erShare = (erShare + pt.getErShare()) - pt.getErWith();
                        penShare = penShare + pt.getPenShare();

                        if (year >= 2021) {

                            Long tPart, nPart;

                            Long nWith = Long.valueOf(0);
                            Long with = Long.valueOf(0);

                            if (cumContNonTaxable + pt.getEeShare() > 250000) {
                                tPart = (cumContNonTaxable + pt.getEeShare()) - 250000;
                                nPart = pt.getEeShare() - tPart;
                            } else {
                                tPart = Long.valueOf(0);
                                nPart = pt.getEeShare();
                            }

                            cumContNonTaxable = cumContNonTaxable + pt.getEeShare();

                            if (cumContNonTaxable >= 250000) {
                                cumContTaxable = cumContTaxable + (cumContNonTaxable - 250000);
                                cumContNonTaxable = Long.valueOf(250000);
                            }

                            pt.setEeShareNonTaxable(cumContNonTaxable);
                            pt.setEeShareTaxable(cumContTaxable);

                            with = pt.getEeWith();

                            if ((pt.getEeWith() - ibbT) == 0) {
                                if (pt.getEeWith() > 0) {
                                    ibbT = Long.valueOf(0);
                                }
                            } else if ((pt.getEeWith() - ibbT) > 0) {
                                nWith = pt.getEeWith() - ibbT;
                                ibbT = Long.valueOf(0);
                            } else {
                                ibbT = ibbT - pt.getEeWith();
                            }

                            ibbT = ibbT + tPart;
                            ibbN = (ibbN + nPart) - nWith;

                        }

                        pbTransNew.add(pt);
                    }
                }

                mn++;
                if (mn == 13) {
                    mn = 1;
                    yr++;
                }
            }

            for (PassbookNewObj.PassbookTrans pt : pbTrans) {
                if (pt.getFlag().equalsIgnoreCase("INT")) {
                    eeShare = eeShare + pt.getEeShare();
                    erShare = erShare + pt.getErShare();
                    penShare = penShare + pt.getPenShare();

                    if (year >= 2021) {
                        interestNonTaxable = interestNonTaxable + pt.getEeShareNonTaxable();
                        interestTaxable = interestTaxable + pt.getEeShareTaxable();
                    }

                    pbTransNew.add(pt);
                }
            }

            for (PassbookNewObj.PassbookTrans pt : pbTrans) {
                if (pt.getFlag().equalsIgnoreCase("TDS")) {
                    eeShare = eeShare + pt.getEeShareTaxable();
                    erShare = erShare + pt.getErShare();
                    penShare = penShare + pt.getPenShare();

                    if (year >= 2021) {
                        interestNonTaxable = interestNonTaxable + pt.getEeShareNonTaxable();
                        interestTaxable = interestTaxable + pt.getEeShareTaxable();
                    }

                    pbTransNew.add(pt);
                }
            }

            for (PassbookNewObj.PassbookTrans pt : pbTrans) {
                if (pt.getFlag().equalsIgnoreCase("TIN")) {
                    eeShare = eeShare + pt.getEeShare();
                    ibbN = ibbN + pt.getEeShare();
                    erShare = erShare + pt.getErShare();
                    penShare = penShare + pt.getPenShare();
                    pbTransNew.add(pt);
                }
            }
            //
            // Taxable calculation
            if (year >= 2021) {
                //ibbN = (ibbN - ibbT) + interestNonTaxable;
                ibbN = ibbN + interestNonTaxable;
                ibbT = ibbT + interestTaxable;
            }
            // Taxable calculation
            //
            // Add CB
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            PassbookNewObj.PassbookTrans pt = new PassbookNewObj.PassbookTrans();
            pt.setWageMonth(sdf.parse("31-03-" + (year + 1)));
            pt.setFlag("CBB");

            ArrayList<String> particulars = new ArrayList();
            particulars.add("Closing Balance as on 31-03-" + (year + 1));

            pt.setParticular(particulars);
            pt.setCount(1);
            pt.setTransMonth(Long.valueOf((year + 1) + "03"));
            pt.setEpfWages(Long.valueOf(0));
            pt.setEpsWages(Long.valueOf(0));

            pt.setEeShare(eeShare);
            pt.setErShare(erShare);

            pt.setEeShareNonTaxable(ibbN);
            pt.setEeShareTaxable(ibbT);

            pt.setEeWith(Long.valueOf(0));
            pt.setErWith(Long.valueOf(0));

            pt.setPenShare(penShare);
            pt.setNcp(Long.valueOf(0));
            pbTransNew.add(pt);
            // Add CB
            //
            //

            dbOutput.setDbObject(pbTransNew);
            dbOutput.setSuccess(true);

        } catch (NumberFormatException | ParseException e) {
            dbOutput.setMessage(e.toString());
            dbOutput.setSuccess(false);
            return dbOutput;
        }

        return dbOutput;
    }

    public OutputDB getBalance(HttpServletRequest request, PassbookNewObj passbookObj) {

        OutputDB dbOutput = new OutputDB();
        try {

            Balance balance = new Balance();
            ArrayList<Balance.ChartData> chartDataList = new ArrayList();

            Long totBal = Long.valueOf(0);
            Long totEeCont = Long.valueOf(0);
            Long totErCont = Long.valueOf(0);
            Long totPenCont = Long.valueOf(0);
            Long totIntCont = Long.valueOf(0);
            Long totWith = Long.valueOf(0);
            Long totVdr = Long.valueOf(0);

            Date lastWm = null;

            for (PassbookNewObj.PassbookYearly py : passbookObj.getPassbookYearly()) {
                //System.out.println(py.getYear());

                Long eeCont = Long.valueOf(0);
                Long erCont = Long.valueOf(0);
                Long intCont = Long.valueOf(0);

                for (PassbookNewObj.PassbookTrans pt : py.getPassbookTrans()) {
                    if (pt.getFlag().equalsIgnoreCase(util.code.mcw)) {
                        eeCont = eeCont + pt.getEeShare();
                        erCont = erCont + pt.getErShare();

                        totEeCont = totEeCont + pt.getEeShare();
                        totErCont = totErCont + pt.getErShare();

                        totPenCont = totPenCont + pt.getPenShare();

                        totWith = totWith + (pt.getEeWith() + pt.getErWith());

                        if (pt.getEeShare() > 0 || pt.getErShare() > 0 || pt.getPenShare() > 0) {
                            lastWm = pt.getWageMonth();
                        }

                    }
                }

                for (PassbookNewObj.PassbookTrans pt : py.getPassbookTrans()) {
                    if (pt.getFlag().equalsIgnoreCase(util.code.interest)) {
                        intCont = intCont + (pt.getEeShare() + pt.getErShare());
                        totIntCont = totIntCont + (pt.getEeShare() + pt.getErShare());
                    }
                }

                for (PassbookNewObj.PassbookTrans pt : py.getPassbookTrans()) {
                    if (pt.getFlag().equalsIgnoreCase(util.code.tin)) {
                        //intCont = intCont + (pt.getEeShare() + pt.getErShare());
                        totVdr = totVdr + (pt.getEeShare() + pt.getErShare());
                    }
                }

                Balance.ChartData cd = new Balance.ChartData();
                cd.setYear(py.getYear());
                cd.setEeShare(eeCont);
                cd.setErShare(erCont);
                cd.setInterest(intCont);

                chartDataList.add(cd);

            }

            PassbookNewObj.PassbookYearly py = passbookObj.getPassbookYearly().get(passbookObj.getPassbookYearly().size() - 1);
            for (PassbookNewObj.PassbookTrans pt : py.getPassbookTrans()) {
                if (pt.getFlag().equalsIgnoreCase(util.code.cbb)) {
                    totBal = totBal + (pt.getEeShare() + pt.getErShare());
                    break;
                }
            }

            balance.setTotalBalance(totBal);
            balance.setEeShare(totEeCont);
            balance.setErShare(totErCont);
            balance.setPenShare(totPenCont);
            balance.setInterest(totIntCont);
            balance.setWith(totWith);
            balance.setVdr(totVdr);
            balance.setLastWm(lastWm);

            balance.setChartDataList(chartDataList);

            dbOutput.setDbObject(balance);
            dbOutput.setSuccess(true);

        } catch (Exception e) {
            dbOutput.setMessage(e.toString());
            dbOutput.setSuccess(false);
            return dbOutput;
        }

        return dbOutput;
    }

}
