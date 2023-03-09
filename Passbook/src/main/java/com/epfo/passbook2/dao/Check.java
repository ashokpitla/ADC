/*
 * 
 * 
 */
package com.epfo.passbook2.dao;

import com.epfo.passbook2.common.util;
import com.epfo.passbook2.model.UserData;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SANJAY-KUNJAM
 */
public class Check {

    public static String Session(HttpServletRequest request) {

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                return "error=timeout";
            }

            UserData userData = (UserData) session.getAttribute(util.session.userData);
            if (userData == null) {
                return "error=user-data-exception";
            }

            Boolean dataMid = (Boolean) session.getAttribute(util.session.dataMid);
            if (!dataMid) {
                return "error=member-data-exception";
            }

            Boolean dataClaims = (Boolean) session.getAttribute(util.session.dataClaims);
            if (!dataClaims) {
                return "error=claims-data-exception";
            }

            Boolean dataPassbook = (Boolean) session.getAttribute(util.session.dataPassbook);
            if (!dataPassbook) {
                return "error=passbook-data-exception";
            }

        } catch (Exception e) {
            return "error=exception";
        }

        return "";
    }

}
