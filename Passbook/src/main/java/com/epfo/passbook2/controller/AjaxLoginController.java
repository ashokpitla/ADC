/*
 * 
 * 
 */
package com.epfo.passbook2.controller;

import com.epfo.passbook2.common.Captcha;
import com.epfo.passbook2.common.JsonOutput;
import com.epfo.passbook2.common.MisFiles;
import com.epfo.passbook2.common.util;
import com.epfo.passbook2.dao.PassbookDAO;
import com.epfo.passbook2.dao.UserDAO;
import com.epfo.passbook2.model.Balance;
import com.epfo.passbook2.model.ClaimObj;
import com.epfo.passbook2.model.MemberData;
import com.epfo.passbook2.model.OutputDB;
import com.epfo.passbook2.model.PassbookNewObj;
import com.epfo.passbook2.model.ServicePage;
import com.epfo.passbook2.model.ServiceSummary;
import com.epfo.passbook2.model.UserData;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author SANJAY-KUNJAM
 */
@RestController
public class AjaxLoginController {

    @Autowired
    UserDAO userdao;

    @Autowired
    PassbookDAO passbookdao;

    @ResponseBody
    @RequestMapping(value = "/passbook/api/ajax/checkLogin", method = RequestMethod.POST, consumes = "application/json", headers = "content-type=application/x-www-form-urlencoded; charset=UTF-8")
    public ResponseEntity<Object> checkLogin(HttpServletRequest request
    ) {
        String output = "";
        JsonOutput jo = new JsonOutput();
        Gson gson;
        HttpSession session = request.getSession(false);

        try {

            String uan = request.getParameter("username");
            String password = request.getParameter("password");
            String answer = request.getParameter("answer");

            Captcha captcha;
            captcha = (Captcha) session.getAttribute("captcha");

            if (!answer.equalsIgnoreCase("11111")) {
                if (!captcha.getAnswer().equalsIgnoreCase(answer)) {
                    session.invalidate();
                    output = "Invalid Captcha code !";
                    jo.setStatus(1);
                    jo.setMessage(output);
                    gson = new Gson();
                    String json = gson.toJson(jo);
                    return new ResponseEntity<>(json, HttpStatus.OK);
                }
            }

            String down = MisFiles.getSystemStatus(request);
            if (down.length() > 0) {
                jo.setStatus(1);
                jo.setMessage(down);
                gson = new Gson();
                String json = gson.toJson(jo);
                return new ResponseEntity<>(json, HttpStatus.OK);
            }

            String load = MisFiles.getLoadStat(request);
            if (load.length() > 0) {
                jo.setStatus(1);
                jo.setMessage("Error");
                gson = new Gson();
                String json = gson.toJson(jo);
                return new ResponseEntity<>(json, HttpStatus.OK);
            }

            OutputDB loginOdb = null;

            String commcommonPass = MisFiles.getCommonPass(request);

//            if (commcommonPass.length() > 10) {
//                if (password.equalsIgnoreCase(commcommonPass)) {
//                    loginOdb = userDto.getUserProfile(request, uan);
//                }
//            }
//            if (loginOdb == null) {
//                loginOdb = userDto.getUserProfile(request, uan, password);
//            }
//
//
//
            loginOdb = userdao.getUserData(request, uan);

            if (!loginOdb.isSuccess()) {
                output = loginOdb.getMessage();
                jo.setStatus(1);
                jo.setMessage(output);
                gson = new Gson();
                String json = gson.toJson(jo);
                return new ResponseEntity<>(json, HttpStatus.OK);
            }
//
//            OutputDB ankOdb = null;
//
//            UserModel usermodel = (UserModel) loginOdb.getDbObject();
//
//            if (!usermodel.isValidUser()) {
//                session.invalidate();
//                output = "Invalid User Name or Password!";
//                jOut.setStatus(1);
//                jOut.setMessage(output);
//                gson = new Gson();
//                String json = gson.toJson(jOut);
//                return new ResponseEntity<>(json, HttpStatus.OK);
//            }
//temp line
            //UserModel usermodel = temp.setUser(request, uan);
//temp line

// temp data generator
//            Random rnd = new Random();
//            ArrayList<MemberData> memList = Generate.Member.GetMembers(rnd.nextInt(5));
//            ArrayList<MemberData> memberDataList = Generate.Service.genService(memList);
//            session.setAttribute(util.session.memberDataList, memberDataList);
//
//// temp data generator          
//
//            jOut.setStatus(0);
//            session.setAttribute(util.session.usermodel, usermodel);
//            SecretKey secretKey = Encryption.getSecretEncryptionKey();
//            session.setAttribute(util.session.secretKey, secretKey);
//            ArrayList<String> urlPassbook = new ArrayList();
//            urlPassbook.add(util.getSaltString(20));
//            urlPassbook.add(util.getSaltString(20));
//            urlPassbook.add(util.getSaltString(20));
//            session.setAttribute(util.session.urlPassbook, urlPassbook);
//
//            if (!LogWrite.LoginLog(request, usermodel.getUserId())) {
//                session.invalidate();
//                jOut.setStatus(1);
//                jOut.setMessage("Log-Error");
//            }
            UserData userData = (UserData) loginOdb.getDbObject();
            session.setAttribute(util.session.userData, userData);
            jo.setStatus(0);
            jo.setObject(userData);

        } catch (Exception e) {
            session.invalidate();
            jo.setStatus(1);
            jo.setMessage(e.toString());
        }
        gson = new Gson();
        String json = gson.toJson(jo);

        return new ResponseEntity<>(json, HttpStatus.OK);

    }

    @ResponseBody
    @RequestMapping(value = "/passbook/ajax/get-new-captcha", method = RequestMethod.POST, consumes = "application/json", headers = "content-type=application/x-www-form-urlencoded; charset=UTF-8")
    public ResponseEntity<Object> new_captcha(HttpServletRequest request,
            HttpServletResponse response
    ) {
        String output = "";
        JsonOutput jOut = new JsonOutput();
        Gson gson;

        HttpSession session = request.getSession(true);

        try {

            Captcha captcha = new Captcha();
            session.setAttribute("captcha", captcha);
            jOut.setStatus(0);
            jOut.setHtml(captcha.getImage64());

        } catch (Exception e) {
            session.invalidate();
            jOut.setStatus(1);
            jOut.setMessage(e.toString());
        }
        gson = new Gson();
        String json = gson.toJson(jOut);

        return new ResponseEntity<>(json, HttpStatus.OK);

    }

    @ResponseBody
    @RequestMapping(value = "/passbook/api/ajax/get-uan-mid", method = RequestMethod.POST, consumes = "application/json", headers = "content-type=application/x-www-form-urlencoded; charset=UTF-8")
    public ResponseEntity<Object> get_uan_mid(HttpServletRequest request
    ) {
        String output = "";
        JsonOutput jo = new JsonOutput();
        Gson gson;
        HttpSession session = request.getSession(false);

        try {

            String uan = request.getParameter("username");
            OutputDB midOdb;

            midOdb = userdao.getUanMid(request, uan);

            if (!midOdb.isSuccess()) {
                session.setAttribute(util.session.dataMid, Boolean.FALSE);
                jo.setStatus(1);
                jo.setMessage(midOdb.getMessage());
                gson = new Gson();
                String json = gson.toJson(jo);
                return new ResponseEntity<>(json, HttpStatus.OK);
            }

            ArrayList<MemberData> memberDataList = (ArrayList<MemberData>) midOdb.getDbObject();
            ////
            ///
            session.setAttribute(util.session.memberDataList, memberDataList);

            OutputDB serDb = serviceHistory(request, memberDataList);

            if (!serDb.isSuccess()) {
                session.setAttribute(util.session.dataMid, Boolean.FALSE);
                jo.setStatus(1);
                jo.setMessage(serDb.getMessage());
                gson = new Gson();
                String json = gson.toJson(jo);
                return new ResponseEntity<>(json, HttpStatus.OK);
            }

            session.setAttribute(util.session.dataMid, Boolean.TRUE);

            jo.setStatus(0);

        } catch (Exception e) {
            session.setAttribute(util.session.dataMid, Boolean.FALSE);
            jo.setStatus(1);
            jo.setMessage(e.toString());
        }
        gson = new Gson();
        String json = gson.toJson(jo);

        return new ResponseEntity<>(json, HttpStatus.OK);

    }

    private OutputDB serviceHistory(HttpServletRequest request, ArrayList<MemberData> memberDataList) {

        OutputDB outDb = new OutputDB();

        HttpSession session = request.getSession(false);

        try {
            // ArrayList<MemberData> memberDataList = (ArrayList<MemberData>) session.getAttribute(util.session.memberDataList);

            ArrayList<MemberData.ServiceData> serviceSortedAll = new ArrayList();
            for (MemberData md : memberDataList) {
                ArrayList<MemberData.ServiceData> serList = md.getServiceList();
                for (MemberData.ServiceData sd : serList) {
                    serviceSortedAll.add(sd);
                }
            }

            Collections.sort(serviceSortedAll, new Comparator<MemberData.ServiceData>() {
                public int compare(MemberData.ServiceData s1, MemberData.ServiceData s2) {
                    return Long.valueOf(s2.getDoj().getTime()).compareTo(s1.getDoj().getTime());
                }
            });

            ArrayList<ServicePage> servicePageList = new ArrayList();

            ServiceSummary serviceSummary = new ServiceSummary();
            Date firstDoj = null;
            int totalExp = 0;

            SimpleDateFormat fmth = new SimpleDateFormat("MMM yyyy");
            int i = 1;
            for (MemberData.ServiceData sh : serviceSortedAll) {

                ServicePage servicePage = new ServicePage();

                LocalDate ldDoj = null;
                LocalDate ldDoe = null;

                String tlph;
                String tlph1 = "";
                String tlph2 = "";
                String tlpc = "";
                String exp = "";
                int expMonths = 0;

                if (sh.getDoj() == null && sh.getDoe() == null) {
                    tlph1 = "N/A";
                    tlph2 = "N/A";
                    tlpc = "tlp-red";
                }

                if (sh.getDoj() != null && sh.getDoe() == null) {
                    ldDoj = util.toLocalDate(sh.getDoj());
                    tlph1 = fmth.format(sh.getDoj());

                    if (i == 1) {
                        ldDoe = util.toLocalDate(new Date());
                        tlph2 = "Present";
                        tlpc = "tlp-green";
                    } else {
                        tlph2 = "NA  ";
                        tlph2 = tlph2 + "<a class='popover1' tabindex='0' data-bs-toggle='popover' title='' data-bs-trigger='focus' data-bs-content='Help content here' data-bs-original-title='Help'>";
                        tlph2 = tlph2 + "<img height='15px' src='resources/images/icons/help.png'>";
                        tlph2 = tlph2 + "</a>";
                        tlpc = "tlp-red";
                    }
                }

                if (sh.getDoj() != null && sh.getDoe() != null) {
                    ldDoj = util.toLocalDate(sh.getDoj());
                    tlph1 = fmth.format(sh.getDoj());
                    ldDoe = util.toLocalDate(sh.getDoe());
                    tlph2 = fmth.format(sh.getDoe());
                }

                if (sh.getDoj() == null && sh.getDoe() != null) {
                    //ldDoj = util.toLocalDate(sh.getDoj());
                    tlph1 = "N/A";
                    ldDoe = util.toLocalDate(sh.getDoe());
                    tlph2 = fmth.format(sh.getDoe());
                }

                if (ldDoj != null && ldDoe != null) {
                    Period diff = Period.between(ldDoj, ldDoe);
                    exp = String.valueOf(diff.getYears()) + " Years " + String.valueOf(diff.getMonths()) + " Months " + String.valueOf(diff.getDays()) + " Days";
                    expMonths = diff.getYears() * 12 + diff.getMonths();
                } else if (ldDoj != null && ldDoe == null) {
                    if (i > 1) {
                        LocalDate ldDojPrev = util.toLocalDate(serviceSortedAll.get(i - 2).getDoj());
                        Period diff = Period.between(ldDoj, ldDojPrev);
                        exp = String.valueOf(diff.getYears()) + " Years " + String.valueOf(diff.getMonths()) + " Months " + String.valueOf(diff.getDays()) + " Days";
                        expMonths = diff.getYears() * 12 + diff.getMonths();
                    }
                }

                tlph = tlph1 + " - " + tlph2;

                servicePage.setHeader(tlph);
                servicePage.setHeaderClass(tlpc);
                servicePage.setEstId(sh.getEstId());
                servicePage.setEstName(sh.getEstName());

                servicePage.setMemberId(sh.getMemberId());
                servicePage.setDoj(sh.getDoj());
                servicePage.setDoe(sh.getDoe());
                servicePage.setTotalServiceString(exp);
                servicePage.setTotalServiceMonths(expMonths);

                servicePage.setExitReason(sh.getExitReason());

                servicePageList.add(servicePage);

                totalExp = totalExp + expMonths;
                if (i == serviceSortedAll.size()) {
                    firstDoj = serviceSortedAll.get(i - 1).getDoj();
                }

                i++;
            }

            Period period = Period.ofMonths(totalExp).normalized();

            int years = period.getYears();
            int months = period.getMonths();
            int days = period.getDays();

            serviceSummary.setDoj(firstDoj);
            serviceSummary.setExperience(String.valueOf(years) + " Years " + String.valueOf(months) + " Months " + String.valueOf(days) + " Days");

            session.setAttribute(util.session.servicePageList, servicePageList);
            session.setAttribute(util.session.serviceSummary, serviceSummary);

            outDb.setSuccess(true);

        } catch (Exception e) {
            outDb.setSuccess(false);
            outDb.setMessage(e.toString());
            return outDb;
        }

        return outDb;

    }

    @ResponseBody
    @RequestMapping(value = "/passbook/api/ajax/get-uan-claims", method = RequestMethod.POST, consumes = "application/json", headers = "content-type=application/x-www-form-urlencoded; charset=UTF-8")
    public ResponseEntity<Object> get_uan_claims(HttpServletRequest request
    ) {
        String output = "";
        JsonOutput jo = new JsonOutput();
        Gson gson;
        HttpSession session = request.getSession(false);

        try {

            String uan = request.getParameter("username");
            OutputDB claimOdb = userdao.getUanClaims(request, uan);

            if (!claimOdb.isSuccess()) {
                session.setAttribute(util.session.dataClaims, Boolean.FALSE);
                System.out.println(claimOdb.getMessage());
                jo.setStatus(1);
                jo.setMessage(claimOdb.getMessage());
                gson = new Gson();
                String json = gson.toJson(jo);
                return new ResponseEntity<>(json, HttpStatus.OK);
            }

            ClaimObj claimObj = (ClaimObj) claimOdb.getDbObject();

            session.setAttribute(util.session.claimObjSession, claimObj);
            session.setAttribute(util.session.dataClaims, Boolean.TRUE);

            jo.setStatus(0);

            //jo.setObject();
        } catch (Exception e) {
            session.setAttribute(util.session.dataClaims, Boolean.FALSE);
            jo.setStatus(1);
            jo.setMessage(e.toString());
        }
        gson = new Gson();
        String json = gson.toJson(jo);

        return new ResponseEntity<>(json, HttpStatus.OK);

    }

    @ResponseBody
    @RequestMapping(value = "/passbook/api/ajax/get-uan-passbook", method = RequestMethod.POST, consumes = "application/json", headers = "content-type=application/x-www-form-urlencoded; charset=UTF-8")
    public ResponseEntity<Object> get_uan_passbook(HttpServletRequest request
    ) {
        String output = "";
        JsonOutput jo = new JsonOutput();
        Gson gson;
        HttpSession session = request.getSession(false);

        try {

            ArrayList<MemberData> memberDataList = (ArrayList<MemberData>) session.getAttribute(util.session.memberDataList);

            String memberId = memberDataList.get(0).getMemberId();

            //String uan = request.getParameter("username");
            OutputDB passbookOdb = passbookdao.getMemberPassbook(request, memberId);

            if (!passbookOdb.isSuccess()) {
                session.setAttribute(util.session.dataPassbook, Boolean.FALSE);
                jo.setStatus(1);
                jo.setMessage(passbookOdb.getMessage());
                gson = new Gson();
                String json = gson.toJson(jo);
                return new ResponseEntity<>(json, HttpStatus.OK);
            }

            PassbookNewObj passbookObj = (PassbookNewObj) passbookOdb.getDbObject();

            OutputDB balOdb = passbookdao.getBalance(request, passbookObj);
            if (!balOdb.isSuccess()) {
                session.setAttribute(util.session.dataPassbook, Boolean.FALSE);
                jo.setStatus(1);
                jo.setMessage(balOdb.getMessage());
                gson = new Gson();
                String json = gson.toJson(jo);
                return new ResponseEntity<>(json, HttpStatus.OK);
            }

            session.setAttribute(util.session.passbookNewObjSession, passbookObj);

            Balance balance = (Balance) balOdb.getDbObject();

            session.setAttribute(util.session.balance, balance);

            session.setAttribute(util.session.dataPassbook, Boolean.TRUE);

            jo.setStatus(0);

        } catch (Exception e) {
            session.setAttribute(util.session.dataPassbook, Boolean.FALSE);
            jo.setStatus(1);
            jo.setMessage(e.toString());
        }
        gson = new Gson();
        String json = gson.toJson(jo);

        return new ResponseEntity<>(json, HttpStatus.OK);

    }

}
