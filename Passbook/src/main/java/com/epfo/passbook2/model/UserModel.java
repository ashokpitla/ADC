/*
 * 
 * 
 */
package com.epfo.passbook2.model;

import java.io.Serializable;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

/**
 *
 * @author SANJAY-KUNJAM
 */
@Component
public class UserModel implements Serializable {

    private String userId;
    private String userName;
    private String password;
    private boolean validUser;
    private String error;
    private String language;
    private String role;
    private String ank;
    private String pan;
    private String cont;

    private ArrayList<MemberModel> memberList;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isValidUser() {
        return validUser;
    }

    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String UserId) {
        this.userId = UserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String UserName) {
        this.userName = UserName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAnk() {
        return ank;
    }

    public void setAnk(String ank) {
        this.ank = ank;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ArrayList<MemberModel> getMemberList() {
        return memberList;
    }

    public void setMemberList(ArrayList<MemberModel> memberList) {
        this.memberList = memberList;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

}
