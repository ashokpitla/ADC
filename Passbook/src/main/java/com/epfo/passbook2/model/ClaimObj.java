/*
 * 
 * 
 */
package com.epfo.passbook2.model;

import java.io.Serializable;
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
public class ClaimObj implements Serializable {

    private Long totalClaims;
    private Long pending;
    private Long settled;
    private Long settledAmount;
    private Long rejected;
    private ArrayList<Pending> penList;
    private ArrayList<Settled> sttList;
    private ArrayList<Rejected> rejList;
    private ArrayList<ChartSttRej> chartList;

    @Getter
    @Setter
    public static class Pending {

        private String tcId;
        private String memberId;
        private Date receiptDate;
        private String formType;
        private String formName;
        private String formDescription;
        private String officeName;

    }

    @Getter
    @Setter
    public static class Settled {

        private String tcId;
        private String memberId;
        private Date receiptDate;
        private String formType;
        private String formName;
        private String formDescription;
        private String officeName;
        private Long settledAmount;
        private Date approvedDate;

    }

    @Getter
    @Setter
    public static class Rejected {

        private String tcId;
        private String memberId;
        private Date receiptDate;
        private String formType;
        private String formName;
        private String formDescription;
        private String officeName;
        private String rejectReason;
        private Date rejectDate;

    }

    @Getter
    @Setter
    public static class ChartSttRej {

        private int year;
        private Long stt;
        private Long rej;
    }
}
