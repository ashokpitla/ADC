/*
 * 
 * 
 */
package com.epfo.passbook2.dao;

import com.epfo.passbook2.common.fun;

import com.epfo.passbook2.common.util;
import com.epfo.passbook2.model.AjaxOutput;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author SANJAY-KUNJAM
 */
public class API {

    public static AjaxOutput getApiData1(HttpServletRequest request, String url, Map<String, String[]> params) {

        AjaxOutput ao = new AjaxOutput();
        String output = "";

        java.net.URL misUrl;

        ArrayList<String> errCodes = new ArrayList();

        try {

            String qs = "?";

            for (Map.Entry<String, String[]> data : params.entrySet()) {
                qs = qs + data.getKey() + "=" + data.getValue()[0] + "&";
            }
            String qs1 = StringUtils.chop(qs);

            String finalUrl;

            SSLContext ssl_ctx = SSLContext.getInstance("TLS");
            TrustManager[] trust_mgr = get_trust_mgr();
            ssl_ctx.init(null, trust_mgr, new SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(ssl_ctx.getSocketFactory());

            if (util.getServer(request).equalsIgnoreCase(util.server.local)) {

                finalUrl = util.server.apiUrlStaging + url + qs1;
                misUrl = new java.net.URL(finalUrl);
                HttpsURLConnection conn = (HttpsURLConnection) misUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    output = output + line;
                }
                reader.close();
                conn.disconnect();

                Gson gson = new Gson();
                ao = gson.fromJson(output, AjaxOutput.class);

            } else {

                finalUrl = util.server.apiUrlStaging + url + qs1;
                misUrl = new URL(finalUrl);
                HttpsURLConnection conn = (HttpsURLConnection) misUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    output = output + line;
                }
                reader.close();
                conn.disconnect();

                Gson gson = new Gson();
                ao = gson.fromJson(output, AjaxOutput.class);

            }

            System.out.println(finalUrl);

        } catch (JsonSyntaxException | IOException e) {
            System.out.println(e.toString());
            ao.setSuccess(false);
            errCodes.add(e.toString());
            ao.setErrorCodes(errCodes);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(fun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(fun.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ao;

    }

    public static TrustManager[] get_trust_mgr() {
        TrustManager[] certs = {new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String t) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String t) {
            }
        }};
        return certs;
    }

}
