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

@Getter
@Setter
public class PassbookNewObj implements Serializable {

    private String memberId;
    private ArrayList<PassbookYearly> passbookYearly;
    private PassbookOtherData passbookOtherData;

    @Getter
    @Setter
    public static class PassbookYearly {

        public PassbookYearly() {
        }
        private int year;
        private ArrayList<PassbookTrans> passbookTrans;
        private ArrayList<AllTrans> allTrans;
    }

    @Getter
    @Setter
    public static class PassbookTrans {

        public PassbookTrans() {
        }
        private Date wageMonth;
        private String flag;
        private ArrayList<String> particular;
        private int count;
        private String transDmy;
        private Long transMonth;
        private Long epfWages;
        private Long epsWages;
        private Long eeShare;
        private Long erShare;
        private Long eeShareNonTaxable;
        private Long eeShareTaxable;
        private Long eeWith;
        private Long erWith;
        private Long penShare;
        private Long ncp;
    }

    @Getter
    @Setter
    public static class PassbookOtherData {

        private String memberName;
        private String memberDob;
        private String estName;
        private String fhName;
        private String fhFlag;
        private String doj;
        private String officeName;
        private String uan;
        private int aadhaarVerified;
        private String finalDob;
        private int int19;
        private String lastCont;

    }

    @Getter
    @Setter
    public static class AllTrans {

        public AllTrans() {
        }
        private String flag;
        private Date wageMonth;
        private String particular;
        private String transDmy;
        private Long ym;
        private Long epfWages;
        private Long epsWages;
        private Long eeShare;
        private Long erShare;
        private Long eeWith;
        private Long erWith;
        private Long penShare;
        private Long ncp;
    }
}
