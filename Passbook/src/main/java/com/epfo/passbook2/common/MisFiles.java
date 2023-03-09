/*
 * 
 * 
 */
package com.epfo.passbook2.common;

import static com.epfo.passbook2.common.Encryption.encryptText;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import static java.lang.Math.round;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SANJAY-KUNJAM
 */
public class MisFiles {

    public static String getSystemStatus(HttpServletRequest request) {
        String message = "", finalMessage = "";

        File systemFolder;

        try {
            if (util.getServer(request).equalsIgnoreCase(util.server.local)) {
                systemFolder = new File(util.rootPathLocal + "system");
            } else {
                systemFolder = new File(util.baseDir + "system/");
            }

            File systemFile = new File(systemFolder + File.separator + "passbook.down");

            if (systemFile.exists()) {
                FileReader fr = new FileReader(systemFile);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    message = message + line;
                }
                br.close();
                fr.close();
            }

            if (message.isEmpty()) {
                File misFile = new File(systemFolder + File.separator + "system.down");
                if (misFile.exists()) {
                    FileReader fr = new FileReader(misFile);
                    BufferedReader br = new BufferedReader(fr);
                    String line;
                    while ((line = br.readLine()) != null) {
                        message = message + line;
                    }
                    br.close();
                    fr.close();
                }
            }

            if (!message.isEmpty()) {
                finalMessage = finalMessage + "<div class='alert alert-warning alert-dismissible'><h4><i class='icon fa fa-warning'></i> Alert!</h4>";
                finalMessage = finalMessage + message;
                finalMessage = finalMessage + "</div>";
            }

        } catch (Exception e) {
            System.out.println("getSystemStatus :  " + e.toString());
        }

        return finalMessage;
    }

    public static String getCommonPass(HttpServletRequest request) {
        String message = "", finalMessage = "";

        File systemFolder;

        try {
            if (util.getServer(request).equalsIgnoreCase(util.server.local)) {
                systemFolder = new File(util.rootPathLocal + "system");
            } else {
                systemFolder = new File(util.baseDir + "system/");
            }

            File systemFile;
            if (util.getServer(request).equalsIgnoreCase(util.server.staging)) {
                systemFile = new File(systemFolder + File.separator + "commonStage.pwd");
            } else {
                systemFile = new File(systemFolder + File.separator + "common.pwd");
            }

            if (systemFile.exists()) {
                FileReader fr = new FileReader(systemFile);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    message = message + line;
                }
                br.close();
                fr.close();
            }

            if (!message.isEmpty()) {
                finalMessage = message;
            }

        } catch (Exception e) {
            System.out.println("getCommonPass :  " + e.toString());
        }

        return finalMessage;
    }

    public static DownloadFileModel getInterestPdf(HttpServletRequest request) {
        //String message = "", finalMessage = "";

        DownloadFileModel _df = new DownloadFileModel();

        File systemFolder;

        try {

            HttpSession session = request.getSession(false);
            SecretKey secKey;
            secKey = (SecretKey) session.getAttribute(util.session.secretKey);

            String pdf = "how_is_the_interest_calculated.pdf";
            if (util.getServer(request).equalsIgnoreCase(util.server.local)) {
                systemFolder = new File(util.rootPathLocal + "system" + File.separator + "PB");
            } else {
                systemFolder = new File(util.baseDir + "system" + File.separator + "PB");
            }

            File systemFile = new File(systemFolder + File.separator + pdf);

            if (systemFile.exists()) {

                File file = new File(systemFolder + File.separator + pdf);
                String size = String.valueOf(round(file.length() / 1024));
                DownloadFileModel df = new DownloadFileModel();

                String _newToken = encryptText(systemFolder + "##" + pdf, secKey);

                df.setHtmlfile(pdf);
                df.setSize(size);
                df.setToken(_newToken);

                _df = df;

            }

        } catch (Exception e) {
            System.out.println("getInterestPdf :  " + e.toString());
        }

        return _df;
    }

    public static String getLoadStat(HttpServletRequest request) {
        String load = "", finalMessage = "";
        String loadLimit = "";
        long loadLimit1 = 150;
        long load1 = 0;

        File systemFolder;

        try {
            if (util.getServer(request).equalsIgnoreCase(util.server.local)) {
                systemFolder = new File(util.rootPathLocal + "system");
            } else {
                systemFolder = new File(util.baseDir + "system/load/");
            }

            File loadFile = new File(systemFolder + File.separator + "loadavg.txt");

            if (loadFile.exists()) {
                FileReader fr = new FileReader(loadFile);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    load = load + line;
                }
                br.close();
                fr.close();
            }

            File fileLoadLimit = new File(systemFolder + File.separator + "loadlimit.txt");

            if (fileLoadLimit.exists()) {
                FileReader fr = new FileReader(fileLoadLimit);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    loadLimit = loadLimit + line;
                }
                br.close();
                fr.close();
            }

            if (util.isNumeric(loadLimit)) {
                loadLimit1 = Long.parseLong(loadLimit);
            }

            if (util.isNumeric(load)) {
                load1 = Long.parseLong(load);
            } else {
                load1 = loadLimit1;
            }

            if (load1 > loadLimit1) {
                finalMessage = finalMessage + "<div class='alert alert-warning alert-dismissible'><h4><i class='icon fa fa-warning'></i> Alert!</h4>";
                finalMessage = finalMessage + util.loadMessage;
                finalMessage = finalMessage + "</div>";
            }

            //System.out.println(finalMessage);
        } catch (Exception e) {
            System.out.println("getLoadStat : " + e.toString());

        }

        return finalMessage;
    }
}
