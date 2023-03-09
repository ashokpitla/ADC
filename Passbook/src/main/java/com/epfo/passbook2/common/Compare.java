/*
 * 
 * 
 */
package com.epfo.passbook2.common;

import com.epfo.passbook2.model.ServiceData;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author SANJAY-KUNJAM
 */
public class Compare {

    @Getter
    @Setter
    public class ServiceCompare implements Comparable<ServiceData> {

        private long sr;
        private String memberId;
        private String estId;
        private String estName;
        private Date doj;
        private Date doe;
        private String exitReason;

        @Override
        public int compareTo(ServiceData sd) {
            return getDoj().compareTo(sd.getDoj());
        }
    }

}
