/*
 * 
 * 
 */
package com.epfo.passbook2.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author SANJAY-KUNJAM
 */
@Getter
@Setter
public class ServiceSummary implements Serializable {

    private String experience;
    private Date doj;
    private long ncp;
}
