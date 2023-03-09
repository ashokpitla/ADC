/*
 * 
 * 
 */
package com.epfo.passbook2.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author SANJAY-KUNJAM
 */
@Getter
@Setter
public class Passbook {

    private String memberId;
    private int transType;
    private Date transDate;
    private Date wageMonth;
    private String particular;
    private Long epfWages;
    private Long epsWages;
    private Long eeCont;
    private Long eeContNonTax;
    private Long eeContTax;
    private Long erCont;
    private Long eeWith;
    private Long erWith;
    private Long ncp;
    private Long penCont;

}
