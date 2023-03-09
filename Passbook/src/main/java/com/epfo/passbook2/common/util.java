/*
 * 
 * 
 */
package com.epfo.passbook2.common;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author SANJAY-KUNJAM
 */
public class util {

    public static String rootPathLocal = "D:\\sharemis\\mis\\";
    public static String baseDir = "/sharemis/mis/";
    public static String timeout = "300";
    public static boolean logActiveUans = true;
    public static String loadMessage = "We are experiencing high load on our Servers. Please try after some time.";
    public static String invalidSession = "Invalid session / Session expired. Please logout and try again.";

    public static String national_emblem = "/" + util.war.warname + "/resources/images/emblem-dark.png";
    public static String epfo_logo = "/" + util.war.warname + "/resources/images/epfo_logo.png";
    public static String swach = "/" + util.war.warname + "/resources/images/swach-bharat.png";
    public static String azadi = "/" + util.war.warname + "/resources/images/azadi-ka-amrit-mahotsav.jpg";
    public static String msewa = "1. Give a Missed call to 9966044425 or 2. SMS EPFOHO<UAN><LAN> to 7738299899";

    public static class jscss {

        public static String favicon = "/" + util.war.warname + "/resources/favicon.ico";

        public static String jquery_ui_css = "/resources/jquery/jquery-ui.css?token=" + fun.getSaltId(15);
        public static String epfo_css = "/resources/css/epfostyle.css?token=" + fun.getSaltId(15);
        public static String bootstrap_css = "/resources/bootstrap/bootstrap.css?token=" + fun.getSaltId(15);
        public static String datatable_bootstrap_css = "/resources/datatable/dataTables.bootstrap5.min.css?token=" + fun.getSaltId(15);
        public static String fa_css = "/resources/fa/css/font-awesome.css?token=" + fun.getSaltId(15);
        public static String tab_css = "/resources/css/tab.css?token=" + fun.getSaltId(15);

        public static String jquery_js = "/resources/jquery/jquery-3.6.1.min.js?token=" + fun.getSaltId(15);
        public static String jquery_ui_js = "/resources/jquery/jquery-ui.js?token=" + fun.getSaltId(15);
        public static String bootstrap_js = "/resources/bootstrap/bootstrap.bundle.js?token=" + fun.getSaltId(15);

        public static String md5 = "/resources/js/md5.js?token=" + fun.getSaltId(15);
        public static String sha512 = "/resources/js/sha512.js?token=" + fun.getSaltId(15);

    }

    public static class war {

        public static String warname = "Passbook4";
        //public static String staging = "adcstage.epfindia.gov.in";
        //public static String production = "mis.epfindia.gov.in";
        public static String timeout = "300";
        //public static String apiwar = "epfo-mis-api";

    }

    public static class server {

        public static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

        public static String local = "localhost";
        public static String staging = "adcstage.epfindia.gov.in";
        public static String production = "passbook.epfindia.gov.in";

        public static String apiUrlStaging = "https://adcstage.epfindia.gov.in/" + war.warname;
        public static String apiUrlProduction = "https://mis.epfindia.gov.in/" + war.warname;

    }

    public static class session {

        public static String userData = "userData";
        public static String memberDataList = "memberDataList";
        public static String claimObjSession = "claimObjSession";
        public static String passbookNewObjSession = "passbookNewObjSession";

        public static String balance = "balance";

        public static String sessionOk = "sessionOk";
        public static String memberData = "memberData";
        
        
        public static String servicePageList = "servicePageList";
        public static String serviceSummary = "serviceSummary";

        ///
        public static String dataMid = "dataMid";
        public static String dataClaims = "dataClaims";
        public static String dataPassbook = "dataPassbook";
        ///

        public static String serviceSortedAll = "serviceSortedAll";

        public static String secretKey = "secretKey";
        public static String passbookSession = "passbookSession";
        public static String claimSession = "claimSession";
        public static String usermodel = "usermodel";
        public static String urlPassbook = "urlPassbook";
        public static String pageError = "pageError";
        public static String passbookObjSession = "passbookObjSession";

    }

    public static class code {

        public static String obb = "OBB";
        public static String ape = "APE";
        public static String mcw = "MCW";

        public static String interest = "INT";

        public static String tin = "TIN";
        public static String tds = "TDS";
        public static String cbb = "CBB";

    }

    public static String getBaseURL(HttpServletRequest request) {
        String _url;
        String getProtocol = request.getScheme();
        String getDomain = request.getServerName();
        String getPort = ":" + Integer.toString(request.getServerPort());

        if (getPort.equalsIgnoreCase(":80")) {
            getPort = "";
        }

        if (getServer(request).equalsIgnoreCase(server.local)) {
            _url = getProtocol + "://" + getDomain + getPort + "/" + war.warname + "/";
        } else {
            _url = "https://" + getDomain + getPort + "/" + war.warname + "/";
        }

        //System.out.println("_url :" + _url);
        return _url;
    }

    public static String getServer(HttpServletRequest request) {
        String _server = "", _url;

        String getProtocol = request.getScheme();
        String getDomain = request.getServerName();

        _url = getProtocol + "://" + getDomain + "/";

        if (_url.contains(server.local)) {
            _server = server.local;
        }
        if (_url.contains(server.staging)) {
            _server = server.staging;
        }
        if (_url.contains(server.production)) {
            _server = server.production;
        }

        return _server;
    }

    public static String getSaltString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890+=";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public static boolean isNumeric(String str) {

        if (str == null) {
            return false;
        }

        if (str.trim().isEmpty()) {
            return false;
        }

        String str1 = str.replace(",", "");

        try {
            //Double.parseDouble(str1);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

//        for (char c : str1.toCharArray()) {
//            if (!Character.isDigit(c) && !Character.toString(c).equalsIgnoreCase(".")) {
//                return false;
//            }
//        }
        // return true;
    }

    public static String getIp(HttpServletRequest request) {
        String remoteAddr;

        remoteAddr = request.getHeader("X-Forwarded-For");
        if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        }
        if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
            remoteAddr = request.getHeader("HTTP_CLIENT_IP");
        }
        if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
            remoteAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
            if (util.getServer(request).equalsIgnoreCase(util.server.local)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }

    public static LocalDate toLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static String formatN(Double input) {
        String output = "";

        try {
            String inputF = String.format("%.2f", input);

            int ldot;

            ldot = inputF.indexOf(".");

            String inputD = inputF.substring(ldot + 1, ldot + 3);

            String input1 = inputF.substring(0, ldot);

            ArrayList<String> str_rev = new ArrayList();
            int j = 0;

            int sindex;
            String _data;

            for (int i = 1; i <= input1.length(); i++) {

                sindex = input1.length() - i;
                _data = input1.substring(sindex, sindex + 1);
                str_rev.add(_data);

                if (i == 3 && i != input1.length()) {
                    str_rev.add((","));
                    j = 0;
                }

                if (j == 2 && i != input1.length()) {
                    str_rev.add((","));
                    j = 0;
                }

                j++;
            }

            for (int i = 0; i < str_rev.size(); i++) {
                output = str_rev.get(i) + output;
            }

            output = output + "." + inputD;
        } catch (Exception e) {
            System.out.print("formatN (Double) : " + e.toString());
            output = String.valueOf(input);
        }

        return output;
    }

    public static String formatN(String input) {
        String output = "";

        try {
            //String inputF = String.format("%.2f", input);

            int ldot;

            ldot = input.indexOf(".");
            String inputD = "";
            String input1;

            if (ldot > 0) {
                inputD = input.substring(ldot + 1, ldot + 3);
                input1 = input.substring(0, ldot);
            } else {
                input1 = input;
            }

            ArrayList<String> str_rev = new ArrayList();
            int j = 0;

            int sindex;
            String _data;

            for (int i = 1; i <= input1.length(); i++) {

                sindex = input1.length() - i;
                _data = input1.substring(sindex, sindex + 1);
                str_rev.add(_data);

                if (i == 3 && i != input1.length()) {
                    str_rev.add((","));
                    j = 0;
                }

                if (j == 2 && i != input1.length()) {
                    str_rev.add((","));
                    j = 0;
                }

                j++;
            }

            for (int i = 0; i < str_rev.size(); i++) {
                output = str_rev.get(i) + output;
            }

            if (inputD.length() > 0) {
                output = output + "." + inputD;
            }

        } catch (Exception e) {
            System.out.print("formatN (String) : " + e.toString());
            output = String.valueOf(input);
        }

        return output;
    }

    public static String formatN(Long input) {
        String output = "";

        try {
            String input1 = String.valueOf(input);

            ArrayList<String> str_rev = new ArrayList();
            int j = 0;

            int sindex;
            String _data;

            for (int i = 1; i <= input1.length(); i++) {

                sindex = input1.length() - i;
                _data = input1.substring(sindex, sindex + 1);
                str_rev.add(_data);

                if (i == 3 && i != input1.length()) {
                    str_rev.add((","));
                    j = 0;
                }

                if (j == 2 && i != input1.length()) {
                    str_rev.add((","));
                    j = 0;
                }

                j++;
            }

            for (int i = 0; i < str_rev.size(); i++) {
                output = str_rev.get(i) + output;
            }
        } catch (Exception e) {
            System.out.print("formatN (Long) : " + e.toString());
            output = String.valueOf(input);
        }

        return output;
    }

}
