/*
 * 
 * 
 */
package com.epfo.passbook2.controller;

import com.epfo.passbook2.common.Chart;
import com.epfo.passbook2.common.fun;
import com.epfo.passbook2.common.util;
import com.epfo.passbook2.dao.Check;
import com.epfo.passbook2.model.Balance;
import com.epfo.passbook2.model.BarChart1;
import com.epfo.passbook2.model.BarChart2;
import com.epfo.passbook2.model.ClaimObj;
import com.epfo.passbook2.model.MemberData;
import com.epfo.passbook2.model.ServicePage;
import com.epfo.passbook2.model.ServiceSummary;
import com.epfo.passbook2.model.UserData;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author SANJAY-KUNJAM
 */
@Controller
public class MainController {

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
        model.setViewName("page_error");

        //model.addObject("balance", balance);
        return model;
    }

    @RequestMapping(value = "/blank", method = RequestMethod.GET)
    public ModelAndView blank(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
        model.setViewName("blank");

        //model.addObject("balance", balance);
        return model;
    }

    @RequestMapping(value = "/getting_data", method = RequestMethod.GET)
    public ModelAndView get_data(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            model.setViewName("redirect:/login?error=session-exception");
            return model;
        }

        UserData userData = (UserData) session.getAttribute(util.session.userData);
        if (userData == null) {
            model.setViewName("redirect:/login?error=user-exception");
            return model;
        }

        model.setViewName("get_data");
        return model;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {

        String redirect = Check.Session(request);
        if (redirect.length() > 0) {
            model.setViewName("redirect:/login?" + redirect);
            return model;
        }

        HttpSession session = request.getSession(false);
        try {

            model.setViewName("page_home");

            Balance balance = (Balance) session.getAttribute(util.session.balance);
            ArrayList<ServicePage> servicePageList = (ArrayList<ServicePage>) session.getAttribute(util.session.servicePageList);

            if (!servicePageList.isEmpty()) {
                ServicePage servicePage = servicePageList.get(0);
                model.addObject("servicePage", servicePage);
            }

            model.addObject("balance", balance);

        } catch (Exception e) {
            model.setViewName("redirect:/login?error=exception");
            return model;
        }

        return model;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {

        String redirect = Check.Session(request);
        if (redirect.length() > 0) {
            model.setViewName("redirect:/login?" + redirect);
            return model;
        }

        HttpSession session = request.getSession(false);

        try {
            model.setViewName("page_profile");

            UserData userData = (UserData) session.getAttribute(util.session.userData);

            model.addObject("userData", userData);

        } catch (Exception e) {
            model.setViewName("redirect:/login?error=exception");
            return model;
        }

        return model;
    }

    @RequestMapping(value = "/passbook", method = RequestMethod.GET)
    public ModelAndView passbook(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
        String redirect = Check.Session(request);
        if (redirect.length() > 0) {
            model.setViewName("redirect:/login?" + redirect);
            return model;
        }

        HttpSession session = request.getSession(false);
        try {

            model.setViewName("page_passbook");

            //PassbookNewObj passbookObj = (PassbookNewObj) session.getAttribute(util.session.passbookNewObjSession);
            //System.out.println(passbookObj.getPassbookYearly().size());
            Balance balance = (Balance) session.getAttribute(util.session.balance);

            ArrayList<String> yearList = new ArrayList();

            for (Balance.ChartData cd : balance.getChartDataList()) {
                yearList.add(String.valueOf(cd.getYear()));
            }

            BarChart2 barChart2 = Chart.Passbook.YearlyChart(balance);
            if (!barChart2.getSuccess()) {
                model.setViewName("redirect:/login?error=chart-exception");
                return model;
            }

            model.addObject("yearList", yearList);
            model.addObject("barChart2", barChart2);
            model.addObject("balance", balance);

        } catch (Exception e) {
            model.setViewName("redirect:/login?error=exception");
            return model;
        }

        return model;
    }

    @RequestMapping(value = "/claims", method = RequestMethod.GET)
    public ModelAndView claims(HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws ParseException {

        String redirect = Check.Session(request);
        if (redirect.length() > 0) {
            model.setViewName("redirect:/login?" + redirect);
            return model;
        }

        HttpSession session = request.getSession(false);

        try {
            model.setViewName("page_claims");

            ClaimObj claimObj = (ClaimObj) session.getAttribute(util.session.claimObjSession);

            ArrayList<ClaimObj.Pending> penList = claimObj.getPenList();

            BarChart2 barChart2 = Chart.Claims.YearlyChart(claimObj);
            if (!barChart2.getSuccess()) {
                model.setViewName("redirect:/login?error=chart-exception");
                return model;
            }

            model.addObject("barChart2", barChart2);
            model.addObject("claimObj", claimObj);
            model.addObject("claimPenList", penList);

        } catch (Exception e) {
            model.setViewName("redirect:/login?error=exception");
            return model;
        }
        return model;
    }

    @RequestMapping(value = "/service-history", method = RequestMethod.GET)
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws ParseException {

        String redirect = Check.Session(request);
        if (redirect.length() > 0) {
            model.setViewName("redirect:/login?" + redirect);
            return model;
        }

        HttpSession session = request.getSession(false);
        try {
            model.setViewName("page_service");

            ArrayList<MemberData> memberDataList = (ArrayList<MemberData>) session.getAttribute(util.session.memberDataList);

            ArrayList<ServicePage> servicePageList = (ArrayList<ServicePage>) session.getAttribute(util.session.servicePageList);

            ServiceSummary serviceSummary = (ServiceSummary) session.getAttribute(util.session.serviceSummary);

            //Chart
            BarChart1 barChart1 = new BarChart1();
            String labels;
            String data;

            labels = "[";
            data = "[";
            for (int k = 0; k <= servicePageList.size() - 1; k++) {
                if (k < servicePageList.size()) {
                    labels = labels + "'" + servicePageList.get(k).getMemberId() + "',";
                    data = data + "'" + servicePageList.get(k).getTotalServiceMonths() + "',";
                } else {
                    labels = labels + "'" + servicePageList.get(k).getMemberId() + "'";
                    data = data + "'" + servicePageList.get(k).getTotalServiceMonths() + "'";
                }
            }

            labels = labels + "]";
            data = data + "]";

            barChart1.setData(data);
            barChart1.setLabels(labels);
            barChart1.setText("Text/Header of Chart");
            barChart1.setLabel("Service in Months");
            barChart1.setColor(fun.genColor());
            barChart1.setCount(servicePageList.size());
            //Chart

            model.addObject("serviceSummary", serviceSummary);
            model.addObject("barChart1", barChart1);
            model.addObject("memberDataList", memberDataList);
            model.addObject("servicePageList", servicePageList);
        } catch (Exception e) {
            model.setViewName("redirect:/login?error=exception");
            return model;
        }
        return model;
    }

}
