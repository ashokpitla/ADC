/*
 * 
 * 
 */
package com.epfo.passbook2.common;

/**
 *
 * @author SANJAY-KUNJAM
 */
public class DownloadFileModel {

    private String htmlfile;
    private String size;
    private String records = "";
    private String token;

    public String getHtmlfile() {
        return htmlfile;
    }

    public void setHtmlfile(String htmlfile) {
        this.htmlfile = htmlfile;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
