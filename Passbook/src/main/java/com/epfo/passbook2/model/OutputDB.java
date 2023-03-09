/*
 * 
 * 
 */
package com.epfo.passbook2.model;

import java.io.Serializable;

/**
 *
 * @author SANJAY-KUNJAM
 */
public class OutputDB implements Serializable{

    private boolean success;
    private String message;
    private Object dbObject;

    public OutputDB() {
        this.success = true;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getDbObject() {
        return dbObject;
    }

    public void setDbObject(Object dbObject) {
        this.dbObject = dbObject;
    }
}
