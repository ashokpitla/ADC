/*
 * 
 * 
 */
package com.epfo.passbook2.common;

import com.epfo.passbook2.model.UserModel;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author SANJAY-KUNJAM
 */
public class temp {

    public static UserModel setUser(HttpServletRequest request, String userId) {

        UserModel userModel = new UserModel();
        
        userModel.setUserId(userId);
        userModel.setUserName("Sanjay");
        userModel.setValidUser(true);

        return userModel;

    }

}
