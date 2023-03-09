/*
 * 
 * 
 */
package com.epfo.passbook2.model;

import java.util.ArrayList;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Claims {

    private long totalClaims;
    private long settledClaims;
    private long settledAmount;
    private long rejectedClaims;
    private long pendingClaims;    
    ArrayList<PendingClaims> pendingClaimsList;
    
    @Getter
    @Setter
    public static class PendingClaims {

        private String claimId;
        private String trackingId;
        private Date receiptDate;
        private String memberId;
        private String claimType;
        private String claimTypeName;

    }

}
