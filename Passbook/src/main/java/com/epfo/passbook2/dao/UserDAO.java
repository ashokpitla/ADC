/*
 * 
 * 
 */
package com.epfo.passbook2.dao;

import com.epfo.passbook2.model.OutputDB;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author SANJAY-KUNJAM
 */
public interface UserDAO {

    public OutputDB getUserData(HttpServletRequest request, String username);

    public OutputDB getUanMid(HttpServletRequest request, String username);
    
    public OutputDB getUanClaims(HttpServletRequest request, String username);
}
