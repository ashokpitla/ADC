/*
 * 
 * 
 */
package com.epfo.passbook2.model;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author SANJAY-KUNJAM
 */
@Getter
@Setter
public class AjaxOutput {

    private boolean success;
    private ArrayList<String> errorCodes;
    private String tag;
    private Object JsonData;
    private Object data;

}
