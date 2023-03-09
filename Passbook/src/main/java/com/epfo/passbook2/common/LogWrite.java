/*
 * 
 * 
 */
package com.epfo.passbook2.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SANJAY-KUNJAM
 */
public class LogWrite {

    private static String CheckOkBl(HttpServletRequest request, String uanmem, String menu) {

        String out;
        String okbl = "OK";
        String remoteAddr = "";
        String userAgent = "";

        File pbFolder;
        try {

            if (util.getServer(request).equalsIgnoreCase(util.server.local)) {
                pbFolder = new File(util.rootPathLocal + "system" + File.separator);
            } else {
                pbFolder = new File(util.baseDir + "system" + File.separator);
            }

            ArrayList<String> arrStr = new ArrayList();
            ArrayList<String> ipStr = new ArrayList();

            String strFilename = "passbook-string.block", ipFilename = "passbook-ip.block";

            BufferedReader br;
            String st;

            File strFile = new File(pbFolder + File.separator + strFilename);
            if (strFile.exists()) {
                br = new BufferedReader(new FileReader(strFile));
                while ((st = br.readLine()) != null) {
                    arrStr.add(st);
                }
                br.close();
            }

            File ipFile = new File(pbFolder + File.separator + ipFilename);
            if (ipFile.exists()) {
                br = new BufferedReader(new FileReader(ipFile));
                while ((st = br.readLine()) != null) {
                    ipStr.add(st);
                }
                br.close();
            }

            remoteAddr = util.getIp(request);

            if (remoteAddr == null || "".equals(remoteAddr) || "unknown".equalsIgnoreCase(remoteAddr)) {
                okbl = "BL";
            } else {
                String[] remotAdd;

                remotAdd = remoteAddr.split(",");
                for (String ra1 : remotAdd) {
                    for (String ip : ipStr) {
                        if (ip.trim().equalsIgnoreCase(ra1.trim()) && ra1.length() > 0) {
                            okbl = "BL";
                        }
                    }
                }
            }

            userAgent = request.getHeader("User-Agent");

            if (userAgent == null) {
                okbl = "BL";
            } else {
                for (String str : arrStr) {
                    if (userAgent.toLowerCase().contains(str.toLowerCase())) {
                        okbl = "BL";
                    }
                }
            }
        } catch (Exception e) {
            okbl = "BL";
            System.out.println("LogWrite : CheckOkBl : " + uanmem + " : " + e.toString());
        }

        out = menu + "-" + okbl + "##" + remoteAddr + "##" + userAgent;

        return out;
    }

    public static boolean Log(HttpServletRequest request, String uan, String memberId, String menu) {
        boolean out;
        //String rootPath;
        String finalMessage;

        try {

            String okbl = CheckOkBl(request, memberId, menu);

            if (okbl.contains("OK")) {
                out = true;
            } else {
                out = false;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");

            SimpleDateFormat sdf1 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

            String today = sdf.format(new Date());
            String ttime = sdf1.format(new Date());

            String filename = today + "_passbook.log";

            HttpSession session = request.getSession(false);

            String sid = session.getId();

            finalMessage = uan + "##" + sid + "##" + memberId + "##" + okbl + "##" + ttime;

            File logFolder;

            if (util.getServer(request).equalsIgnoreCase(util.server.local)) {
                logFolder = new File(util.rootPathLocal + "system" + File.separator + "PB" + File.separator);
            } else {
                logFolder = new File(util.baseDir + "system" + File.separator + "PB" + File.separator);
            }

            File logfile = new File(logFolder + File.separator + filename);

            if (!logfile.exists()) {
                logfile.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(logfile, true));
            writer.newLine();
            writer.write(finalMessage);
            writer.close();
        } catch (Exception e) {
            out = false;
            System.out.println("LogWrite : " + memberId + " : " + e.toString());
        }
        return out;
    }

    public static boolean LoginLog(HttpServletRequest request, String uan) {

        boolean out = true;
        String finalMessage;

        try {

            File pbFolder;

            if (util.getServer(request).equalsIgnoreCase(util.server.local)) {
                pbFolder = new File(util.rootPathLocal + "system" + File.separator + "PB" + File.separator);
            } else {
                pbFolder = new File(util.baseDir + "system" + File.separator + "PB" + File.separator);
            }

            if (!pbFolder.exists()) {
                return false;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");

            SimpleDateFormat sdf1 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

            String today = sdf.format(new Date());
            String ttime = sdf1.format(new Date());

            String filename = today + "_login.log";

            String okbl = CheckOkBl(request, uan, "LOGIN");

            if (okbl.contains("OK")) {
                out = true;
            } else {
                out = false;
            }

            HttpSession session = request.getSession(false);
            String sid = session.getId();

            finalMessage = uan + "##" + sid + "##" + okbl + "##" + ttime;

            File logfile = new File(pbFolder + File.separator + filename);

            if (!logfile.exists()) {
                logfile.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(logfile, true));
            writer.newLine();
            writer.write(finalMessage);
            writer.close();

        } catch (Exception e) {
            out = false;
            System.out.println("LogWrite : " + uan + " : " + e.toString());
        }

        return out;
    }

}
