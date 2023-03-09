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
public class UserData implements Serializable {

    private String uan;
    private String name;
    private BasicData basicData;
    private KycData kycData;
    private VeriData veriData;

    @Getter
    @Setter
    public static class BasicData {

        private String gender;
        private Date dob;
        private String fh_name;
        private String relation;
        private String mobile;
        private String email;
        private String ph;
        private String iw;
    }

    @Getter
    @Setter
    public static class KycData {

        private String aadhaar;
        private String aadhaarName;

        private String pan;
        private String panName;

        private String bank;
        private String bankIfsc;
        private String bankName;

    }

    @Getter
    @Setter
    public static class VeriData {

        private String bio;
        private String dmo;
        private String otp;

    }

}
