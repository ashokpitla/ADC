/*
 * 
 * 
 */
package com.epfo.passbook2.model;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author SANJAY-KUNJAM
 */
@Getter
@Setter
public class BarChart2 {

    private String labels;
    private String text;
    private int count;
    private ArrayList<DataSet> dataSetist;

    private Boolean success;
    private String error;

    @Getter
    @Setter
    public static class DataSet {

        private String data;
        private String label;
        private String color;
    }
}
