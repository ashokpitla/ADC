/*
 * 
 * 
 */
package com.epfo.passbook2.dao;

import com.epfo.passbook2.model.OutputDB;
import com.epfo.passbook2.model.PassbookNewObj;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 *
 * @author SANJAY-KUNJAM
 */
@Component
public interface PassbookDAO {

    public OutputDB getMemberPassbook(HttpServletRequest request, String memberid);

    public OutputDB getBalance(HttpServletRequest request, PassbookNewObj passbookObj);

}
