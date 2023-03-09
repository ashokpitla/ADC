/*
 * 
 * 
 */
package com.epfo.passbook2.model;

import java.util.ArrayList;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Service {

    private ArrayList<ServiceHistory> serviceHistory;
    private Summary summary;

    @Getter
    @Setter
    public static class Summary {

        public Summary() {
        }
        ;
        private String experience;
        private Date doj;
        private long ncp;
    }

    @Getter
    @Setter
    public static class ServiceHistory {

        public ServiceHistory() {
        }
        ;

        private long sr;
        private String memberId;
        private String estId;
        private String estName;
        private Date doj;
        private Date doe;
        private String exitReason;

        private long balance;
        private long wth;
        private long trf;

    }

}
