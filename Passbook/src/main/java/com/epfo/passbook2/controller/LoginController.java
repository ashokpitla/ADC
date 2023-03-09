/*
 * 
 * 
 */
package com.epfo.passbook2.controller;

import com.epfo.passbook2.common.Captcha;
import com.epfo.passbook2.common.util;
import java.io.IOException;
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
public class LoginController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void index(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(util.getBaseURL(request) + "login");
            System.out.println(util.getBaseURL(request));
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
        model.setViewName("login");
//
        Captcha captcha = new Captcha();
        String systemMessage = "";
        //MisFiles.getSystemStatus(request);
        String loadMessage = "";
        //MisFiles.getLoadStat(request);
        HttpSession session = request.getSession(false);

        String pageError = "";

        if (session == null) {
            session = request.getSession(true);
        } else {
            //clearSession(request);
            pageError = (String) session.getAttribute(util.session.pageError);
            if (pageError == null || pageError.length() == 0) {
                session.invalidate();
                session = request.getSession(true);
            }
        }

        session.setAttribute("captcha", captcha);
        model.addObject("captcha", captcha);
        model.addObject("pageError", pageError);
        model.addObject("systemMessage", systemMessage);
        model.addObject("loadMessage", loadMessage);

        return model;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(true);
        session.invalidate();
        try {
            response.sendRedirect(util.getBaseURL(request) + "login");
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }
    }
}
