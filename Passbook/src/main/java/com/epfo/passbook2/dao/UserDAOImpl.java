/*
 * 
 * 
 */
package com.epfo.passbook2.dao;

import com.epfo.passbook2.model.ClaimObj;
import com.epfo.passbook2.model.MemberData;
import com.epfo.passbook2.model.MemberModel;
import com.epfo.passbook2.model.OutputDB;
import com.epfo.passbook2.model.UserData;
import com.epfo.passbook2.model.UserModel;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author SANJAY-KUNJAM
 */
@Component
public class UserDAOImpl implements UserDAO {

    @Autowired
    DataSource dsnewpb;

    private JdbcTemplate getJdbcTemplate;

    @Override
    public OutputDB getUserData(HttpServletRequest request, String username) {

        OutputDB outObject = new OutputDB();

        ArrayList<MemberModel> memberList;

        UserModel usermodel = new UserModel();

        getJdbcTemplate = new JdbcTemplate();
        getJdbcTemplate.setDataSource(this.dsnewpb);

        if (getJdbcTemplate.getDataSource() == null) {
            outObject.setSuccess(false);
            outObject.setMessage("Data source exception.");
            outObject.setDbObject(null);
            return outObject;
        }

        Connection con = null;
        ResultSet rs = null;

        try {

            con = getJdbcTemplate.getDataSource().getConnection();

            CallableStatement callableStatement;

            String proc = "{call A_PASSBOOK_GET_UAN(?,?,?,?)}";

            callableStatement = con.prepareCall(proc);
            callableStatement.setString(1, username);

            callableStatement.registerOutParameter(2, OracleTypes.VARCHAR);
            callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
            callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
            callableStatement.execute();

            String status = (String) callableStatement.getObject(2);
            String message = (String) callableStatement.getObject(3);

            if (status.equalsIgnoreCase("1") || status.equalsIgnoreCase("9")) {
                outObject.setSuccess(false);
                outObject.setMessage(message);
                return outObject;
            }

            rs = (ResultSet) callableStatement.getObject(4);

            UserData ud = new UserData();

            while (rs.next()) {

                ud.setUan(rs.getString("UAN"));
                ud.setName(rs.getString("NAME"));

                UserData.BasicData bd = new UserData.BasicData();
                bd.setDob(rs.getDate("DOB"));
                bd.setFh_name(rs.getString("FATHER_OR_HUSBAND_NAME"));
                bd.setRelation(rs.getString("RELATION_WITH_MEMBER"));
                bd.setMobile(rs.getString("MOBILE_NUMBER"));
                bd.setEmail(rs.getString("EMAIL_ID"));
                bd.setPh(rs.getString("PHYSICALLY_HANDICAP"));
                bd.setIw(rs.getString("INTERNATIONAL_WORKER"));

                ud.setBasicData(bd);

                UserData.KycData kd = new UserData.KycData();
                kd.setAadhaar(rs.getString("AADHAAR"));
                kd.setAadhaarName(rs.getString("NAME_AS_ON_AADHAAR"));

                kd.setPan(rs.getString("PAN"));
                kd.setPanName(rs.getString("NAME_AS_ON_PAN"));

                kd.setBank(rs.getString("BANK_ACC_NO"));
                kd.setBankName(rs.getString("NAME_AS_ON_BANK"));
                kd.setBankIfsc(rs.getString("BANK_IFSC"));

                ud.setKycData(kd);

                UserData.VeriData vd = new UserData.VeriData();
                vd.setBio(rs.getString("VAR_BIO"));
                vd.setDmo(rs.getString("VAR_DMO"));
                vd.setOtp(rs.getString("VAR_OTP"));

                ud.setVeriData(vd);

                break;

            }
            rs.close();
            con.close();

            outObject.setDbObject(ud);
            outObject.setSuccess(true);

        } catch (Exception e) {
            outObject.setSuccess(false);
            outObject.setMessage(e.toString());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {

            }
        }

        return outObject;
    }

    @Override
    public OutputDB getUanMid(HttpServletRequest request, String username) {

        OutputDB outObject = new OutputDB();

        ArrayList<MemberData> midList = new ArrayList();
        ArrayList<MemberData.ServiceData> serList = new ArrayList();

        getJdbcTemplate = new JdbcTemplate();
        getJdbcTemplate.setDataSource(this.dsnewpb);

        if (getJdbcTemplate.getDataSource() == null) {
            outObject.setSuccess(false);
            outObject.setMessage("Data source exception.");
            outObject.setDbObject(null);
            return outObject;
        }

        Connection con = null;
        ResultSet rs = null;

        try {

            con = getJdbcTemplate.getDataSource().getConnection();

            CallableStatement callableStatement;

            String proc = "{call A_PASSBOOK_GET_MID(?,?,?,?,?)}";

            callableStatement = con.prepareCall(proc);
            callableStatement.setString(1, username);

            callableStatement.registerOutParameter(2, OracleTypes.VARCHAR);
            callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
            callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
            callableStatement.registerOutParameter(5, OracleTypes.CURSOR);
            callableStatement.execute();

            String status = (String) callableStatement.getObject(2);
            String message = (String) callableStatement.getObject(3);

            if (status.equalsIgnoreCase("1") || status.equalsIgnoreCase("9")) {
                outObject.setSuccess(false);
                outObject.setMessage(message);
                return outObject;
            }

            rs = (ResultSet) callableStatement.getObject(4);

            while (rs.next()) {
                MemberData md = new MemberData();
                md.setMemberId(rs.getString("MEMBER_ID"));
                midList.add(md);
            }

            rs = (ResultSet) callableStatement.getObject(5);

            SimpleDateFormat dtf = new SimpleDateFormat("DD-MM-yyyy");

            String doj, doe;
            while (rs.next()) {
                MemberData.ServiceData sd = new MemberData.ServiceData();

                sd.setMemberId(rs.getString("MEMBER_ID"));
                sd.setEstId(rs.getString("EST_ID"));
                sd.setEstName(rs.getString("EST_NAME"));
                doj = rs.getString("DOJ");
                doe = rs.getString("DOE");
                if (doj != null) {
                    sd.setDoj(dtf.parse(doj));
                }
                if (doe != null) {
                    sd.setDoe(dtf.parse(doe));
                }
                sd.setDoj_epf(rs.getDate("DOJ_EPF"));
                sd.setDoj_eps(rs.getDate("DOJ_EPS"));
                sd.setDoe_epf(rs.getDate("DOE_EPF"));
                sd.setDoe_eps(rs.getDate("DOE_EPS"));

                serList.add(sd);
            }

            rs.close();
            con.close();

            ArrayList<MemberData> midFinalList = new ArrayList();

            for (MemberData md : midList) {

                ArrayList<MemberData.ServiceData> serviceList = new ArrayList();
                for (MemberData.ServiceData sd : serList) {
                    if (md.getMemberId().equalsIgnoreCase(sd.getMemberId())) {
                        serviceList.add(sd);
                    }
                }

                md.setServiceList(serviceList);

                midFinalList.add(md);

            }

            outObject.setDbObject(midFinalList);
            outObject.setSuccess(true);

        } catch (Exception e) {
            outObject.setSuccess(false);
            outObject.setMessage(e.toString());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {

            }
        }

        return outObject;
    }

    @Override
    public OutputDB getUanClaims(HttpServletRequest request, String username) {

        OutputDB outObject = new OutputDB();

        //ArrayList<MemberModel> memberList;
        //UserModel usermodel = new UserModel();
        ClaimObj claimObj = new ClaimObj();

        getJdbcTemplate = new JdbcTemplate();
        getJdbcTemplate.setDataSource(this.dsnewpb);

        if (getJdbcTemplate.getDataSource() == null) {
            outObject.setSuccess(false);
            outObject.setMessage("Data source exception.");
            outObject.setDbObject(null);
            return outObject;
        }

        Connection con = null;
        ResultSet rs = null;

        try {

            con = getJdbcTemplate.getDataSource().getConnection();

            CallableStatement callableStatement;

            String proc = "{call A_PASSBOOK_GET_CLAIMS(?,?,?,?,?,?,?,?)}";

            callableStatement = con.prepareCall(proc);
            callableStatement.setString(1, username);

            callableStatement.registerOutParameter(2, OracleTypes.VARCHAR);
            callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
            callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
            callableStatement.registerOutParameter(5, OracleTypes.CURSOR);
            callableStatement.registerOutParameter(6, OracleTypes.CURSOR);
            callableStatement.registerOutParameter(7, OracleTypes.CURSOR);
            callableStatement.registerOutParameter(8, OracleTypes.CURSOR);
            callableStatement.execute();

            String status = (String) callableStatement.getObject(2);
            String message = (String) callableStatement.getObject(3);

            if (status.equalsIgnoreCase("1") || status.equalsIgnoreCase("9")) {
                outObject.setSuccess(false);
                outObject.setMessage(message);
                return outObject;
            }

            rs = (ResultSet) callableStatement.getObject(4);

            while (rs.next()) {
                claimObj.setTotalClaims(rs.getLong("TOTAL_CLAIMS"));
                claimObj.setPending(rs.getLong("PEN"));
                claimObj.setSettled(rs.getLong("STT"));
                claimObj.setSettledAmount(rs.getLong("STT_AMOUNT"));
                claimObj.setRejected(rs.getLong("RJT"));
                break;
            }

            rs = (ResultSet) callableStatement.getObject(5);
            ArrayList<ClaimObj.Pending> penList = new ArrayList();

            while (rs.next()) {
                ClaimObj.Pending pen = new ClaimObj.Pending();

                pen.setTcId(rs.getString("TC_ID"));
                pen.setReceiptDate(rs.getDate("RECEIPT_DATE"));
                pen.setMemberId(rs.getString("MEMBER_ID"));
                pen.setFormType(rs.getString("CLAIM_FORM_TYPE"));
                pen.setFormName(rs.getString("FORM_NAME"));
                pen.setFormDescription(rs.getString("FORM_DESCRIPTION"));
                pen.setOfficeName(rs.getString("OFFICE_NAME"));

                penList.add(pen);

            }
            claimObj.setPenList(penList);

            rs = (ResultSet) callableStatement.getObject(6);
            ArrayList<ClaimObj.Settled> setList = new ArrayList();

            while (rs.next()) {
                ClaimObj.Settled stt = new ClaimObj.Settled();

                stt.setTcId(rs.getString("TC_ID"));
                stt.setReceiptDate(rs.getDate("RECEIPT_DATE"));
                stt.setMemberId(rs.getString("MEMBER_ID"));
                stt.setFormType(rs.getString("CLAIM_FORM_TYPE"));
                stt.setFormName(rs.getString("FORM_NAME"));
                stt.setFormDescription(rs.getString("FORM_DESCRIPTION"));
                stt.setOfficeName(rs.getString("OFFICE_NAME"));
                stt.setSettledAmount(rs.getLong("TOTAL_AMOUNT"));
                stt.setApprovedDate(rs.getDate("APPROVED_DATE"));

                setList.add(stt);

            }
            claimObj.setSttList(setList);

            rs = (ResultSet) callableStatement.getObject(7);
            ArrayList<ClaimObj.Rejected> rejList = new ArrayList();

            while (rs.next()) {
                ClaimObj.Rejected rej = new ClaimObj.Rejected();

                rej.setTcId(rs.getString("TC_ID"));
                rej.setReceiptDate(rs.getDate("RECEIPT_DATE"));
                rej.setMemberId(rs.getString("MEMBER_ID"));
                rej.setFormType(rs.getString("CLAIM_FORM_TYPE"));
                rej.setFormName(rs.getString("FORM_NAME"));
                rej.setFormDescription(rs.getString("FORM_DESCRIPTION"));
                rej.setOfficeName(rs.getString("OFFICE_NAME"));
                rej.setRejectReason(rs.getString("REJECT_REASON"));
                rej.setRejectDate(rs.getDate("REJECT_DATE"));

                rejList.add(rej);

            }
            claimObj.setRejList(rejList);

            rs = (ResultSet) callableStatement.getObject(8);
            ArrayList<ClaimObj.ChartSttRej> chartList = new ArrayList();

            while (rs.next()) {
                ClaimObj.ChartSttRej c = new ClaimObj.ChartSttRej();

                c.setYear(rs.getInt("FY"));
                c.setStt(rs.getLong("STT"));
                c.setRej(rs.getLong("RJT"));
                chartList.add(c);

            }
            claimObj.setChartList(chartList);

            rs.close();
            con.close();

            outObject.setDbObject(claimObj);
            outObject.setSuccess(true);

        } catch (SQLException e) {
            outObject.setSuccess(false);
            outObject.setMessage(e.toString());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {

            }
        }

        return outObject;
    }

}
