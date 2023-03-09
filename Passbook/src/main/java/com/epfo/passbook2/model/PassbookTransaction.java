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
public class PassbookTransaction {

    private String memberId;
    private String wageMonth;
    private String particulars;
    private Date transDate;
    private int transType;
    private Long epfWages;
    private Long epsWages;
    private Long eeCont;
    private Long eeContTaxable;
    private Long eeContNonTaxable;
    private Long erCont;
    private Long eeWith;
    private Long erWith;
    private Long penCont;
    private Long ncp;

}
