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
public class ServiceData {

    private long sr;
    private String memberId;
    private String estId;
    private String estName;
    private Date doj;
    private Date doe;
    private String exitReason;
    Boolean flag = true;

}
