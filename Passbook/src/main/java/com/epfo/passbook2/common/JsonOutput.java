/*
 * 
 * 
 */
package com.epfo.passbook2.common;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author SANJAY-KUNJAM
 */
@Getter
@Setter
public class JsonOutput {

    private int status;
    private String message;
    private String html;
    private String style;
    private boolean download = false;
    private Object object;

}
