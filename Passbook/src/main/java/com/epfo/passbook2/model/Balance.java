/*
 * 
 * 
 */
package com.epfo.passbook2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 *
 * @author SANJAY-KUNJAM
 */
@Component
@Getter
@Setter
public class Balance implements Serializable {

    private long totalBalance;
    private long eeShare;
    private long erShare;
    private long interest;
    private long penShare;
    private long with;
    private long vdr;
    private Date LastWm;
    ArrayList<ChartData> chartDataList;

    @Getter
    @Setter
    public static class ChartData {

        private int year;
        private long eeShare;
        private long erShare;
        private long interest;

    }

}
