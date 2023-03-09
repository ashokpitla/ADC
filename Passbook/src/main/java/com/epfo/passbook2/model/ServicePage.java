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
public class ServicePage implements Serializable {

    private String header;
    private String headerClass;

    private String estName;
    private String estId;
    private String memberId;
    private Date doj;
    private Date doe;
    private String totalServiceString;

    private int totalServiceMonths;
    private String exitReason;

}
