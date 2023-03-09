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
public class PassbookObj {

    public PassbookObj() {
    }
    ;

    private String memberId;
    private String otherData;
    private ArrayList<PassbookYearData> passbookYearData;
    private ArrayList<PassbookYearData.PassbookModel> passbookData;
    private PassbookOtherData passbookOtherData;

    @Getter
    @Setter
    public static class PassbookYearData {

        public PassbookYearData() {
        }
        ;

        private String accYear;
        private ArrayList<PassbookModel> passbook;

        @Getter
        @Setter
        public static class PassbookModel {

            public PassbookModel() {
            }

            private String flag;
            private String accYear;
            private String finYear;
            private String transDate;
            private String transDateDmy;
            private String epfWages;
            private String epsWages;
            private String eeShare;
            private String eeT;
            private String eeN;
            private String erShare;
            private String penShare;
            private String ncp;
            private String drcrFalg;
            private String particular;
            private String monthYear;
            private String tableName;
            private String updDate;

        }

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

}
