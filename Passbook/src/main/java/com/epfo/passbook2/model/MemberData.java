/*
 * 
 * 
 */
package com.epfo.passbook2.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author SANJAY-KUNJAM
 */
@Getter
@Setter
public class MemberData implements Serializable {

    private String memberId;
    private Boolean selected;
    private ArrayList<ServiceData> serviceList;
    private long balance;
    private long wth;
    private long trf;

    @Getter
    @Setter
    public static class ServiceData {

        private long sr;
        private String memberId;
        private String estId;
        private String estName;
        private Date doj;
        private Date doe;

        private Date doj_epf;
        private Date doj_eps;
        private Date doe_epf;
        private Date doe_eps;

        private String exitReason;
        Boolean flag = true;

    }

}
