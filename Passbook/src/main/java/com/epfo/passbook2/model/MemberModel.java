/*
 * 
 * 
 */
package com.epfo.passbook2.model;

import java.io.Serializable;
import org.springframework.stereotype.Component;

/**
 *
 * @author SANJAY-KUNJAM
 */
@Component
public class MemberModel implements Serializable{

    public String memberId;
    public String officeId;
    public String code;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
