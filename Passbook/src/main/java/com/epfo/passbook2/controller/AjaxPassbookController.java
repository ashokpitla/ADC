/*
 * 
 * 
 */
package com.epfo.passbook2.controller;

import com.epfo.passbook2.common.JsonOutput;
import com.epfo.passbook2.dao.PassbookDAO;
import com.epfo.passbook2.model.MemberData;
import com.epfo.passbook2.model.OutputDB;
import com.google.gson.Gson;
import java.util.ArrayList;
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
public class AjaxPassbookController {

    @Autowired
    PassbookDAO pbdao;

    @ResponseBody
    @RequestMapping(value = "/passbook/api/ajax/get-passbook-data", method = RequestMethod.POST, consumes = "application/json", headers = "content-type=application/x-www-form-urlencoded; charset=UTF-8")
    public ResponseEntity<Object> get_passbook_data(HttpServletRequest request,
            HttpServletResponse response
    ) {
        String output = "";
        JsonOutput jOut = new JsonOutput();
        Gson gson;

        HttpSession session = request.getSession(false);

        try {

//            String year = request.getParameter("year");
//
//            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//
//            PassbookObj passbookObj = new PassbookObj();
//
//            ArrayList<PassbookTransaction> passbookTransactionList = new ArrayList();
//
//            PassbookTransaction passbookTransaction = new PassbookTransaction();
//
//            passbookTransaction.setWageMonth("Apr-2022");
//            passbookTransaction.setParticulars("Cont. For Due-Month 052022");
//            passbookTransaction.setTransDate(df.parse("13-05-2022"));
//
//            passbookTransaction.setEpfWages(Long.valueOf("542075"));
//            passbookTransaction.setEpsWages(Long.valueOf("15000"));
//
//            passbookTransaction.setEeCont(Long.valueOf("536654"));
//            passbookTransaction.setEeContNonTaxable(Long.valueOf("250000"));
//            passbookTransaction.setEeContTaxable(Long.valueOf("823308"));
//            passbookTransaction.setErCont(Long.valueOf("63799"));
//
//            passbookTransaction.setEeWith(Long.valueOf("0"));
//            passbookTransaction.setErWith(Long.valueOf("0"));
//
//            passbookTransaction.setPenCont(Long.valueOf("1250"));
//            passbookTransaction.setNcp(Long.valueOf("1250"));
//
//            passbookTransactionList.add(passbookTransaction);
//
//            //passbookObj.setPassbookTransaction(passbookTransactionList);
//
//            Random rnd = new Random();
//
//            ArrayList<MemberData> memList = Generate.Member.GetMembers(rnd.nextInt(5));
//
//            ArrayList<MemberData> memList1 = Generate.Service.genService(memList);
//
//            jOut.setStatus(0);
//            jOut.setObject(memList1);
            //jOut.setHtml(captcha.getImage64());
            ArrayList<MemberData> memList = new ArrayList();
            MemberData md = new MemberData();
            md.setMemberId("APHYD00726230001076361");
            memList.add(md);

            md = new MemberData();
            md.setMemberId("APHYD00726230001068307");
            memList.add(md);

            String mid = "APHYD00726230001076361";
            OutputDB passbookOdb;

            for (MemberData md1 : memList) {
                passbookOdb = (OutputDB) pbdao.getMemberPassbook(request, md1.getMemberId());
            }

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
    @RequestMapping(value = "/passbook/api/ajax/get-data-test", method = RequestMethod.POST, consumes = "application/json", headers = "content-type=application/x-www-form-urlencoded; charset=UTF-8")
    public ResponseEntity<Object> get_data_test(HttpServletRequest request,
            HttpServletResponse response
    ) {
        String output = "";
        JsonOutput jOut = new JsonOutput();
        Gson gson;

        //HttpSession session = request.getSession(false);
        try {

            String input = request.getParameter("input");

            System.out.println(input);

            //wait(1);
            jOut.setStatus(0);
            //jOut.setObject(memList1);
            //jOut.setHtml(captcha.getImage64());

        } catch (Exception e) {
            //session.invalidate();
            jOut.setStatus(1);
            jOut.setMessage(e.toString());
        }
        gson = new Gson();
        String json = gson.toJson(jOut);

        return new ResponseEntity<>(json, HttpStatus.OK);

    }

}
