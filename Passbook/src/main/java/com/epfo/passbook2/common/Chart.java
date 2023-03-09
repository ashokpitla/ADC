/*
 * 
 * 
 */
package com.epfo.passbook2.common;

import com.epfo.passbook2.model.Balance;
import com.epfo.passbook2.model.BarChart2;
import com.epfo.passbook2.model.ClaimObj;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author SANJAY-KUNJAM
 */
public class Chart {

    public static class Claims {

        public static BarChart2 YearlyChart(ClaimObj claimObj) {

            BarChart2 barChart2 = new BarChart2();

            try {
                String stt;
                String rjt;
                String label;
                String color;

                ArrayList<BarChart2.DataSet> dataSetList = new ArrayList();
                BarChart2.DataSet dataSet;

                String labels = "[";
                stt = "[";
                rjt = "[";

//                LocalDate ld = LocalDate.now();
//
//                int cYear = 0;
//
//                if (ld.getMonthValue() < 4) {
//                    cYear = ld.getYear() - 1;
//                } else {
//                    cYear = ld.getYear();
//                }
//
//                cYear = cYear - 4;
//
//                for (int k = 1; k <= 5; k++) {
//                    labels = labels + "'" + cYear + "',";
//                    cYear++;
//                }
                for (ClaimObj.ChartSttRej c : claimObj.getChartList()) {
                    labels = labels + "'" + c.getYear() + "',";
                    stt = stt + "'" + c.getStt() + "',";
                    rjt = rjt + "'" + c.getRej() + "',";
                }

                stt = stt.substring(0, stt.length() - 1) + "]";
                rjt = rjt.substring(0, rjt.length() - 1) + "]";

                labels = labels.substring(0, labels.length() - 1) + "]";

                dataSet = new BarChart2.DataSet();
                label = "Settled";
                color = fun.genColor();
                dataSet.setData(stt);
                dataSet.setLabel(label);
                dataSet.setColor(color);
                dataSetList.add(dataSet);

                dataSet = new BarChart2.DataSet();

                //data = "[0, 0, 0, 0, 0, 0, 0, 8000, 12000, 12000, 15000, 18000, 4000]";
                label = "Rejected";
                color = fun.genColor();
                dataSet.setData(rjt);
                dataSet.setLabel(label);
                dataSet.setColor(color);
                dataSetList.add(dataSet);

                barChart2.setLabels(labels);
                barChart2.setText("Claims settled/rejected in last 5 years");
                barChart2.setDataSetist(dataSetList);

                barChart2.setSuccess(true);

            } catch (Exception e) {
                barChart2.setSuccess(false);
                barChart2.setError(e.toString());
            }

            return barChart2;

        }
    }

    public static class Passbook {

        public static BarChart2 YearlyChart(Balance balance) {

            BarChart2 barChart2 = new BarChart2();

            try {
                String eeData;
                String erData;
                String intData;
                String label;
                String color;

                ArrayList<BarChart2.DataSet> dataSetList = new ArrayList();
                BarChart2.DataSet dataSet;

                String labels = "[";
                eeData = "[";
                erData = "[";
                intData = "[";

                LocalDate ld = LocalDate.now();

                int cYear = 0;

                if (ld.getMonthValue() < 4) {
                    cYear = ld.getYear() - 1;
                } else {
                    cYear = ld.getYear();
                }

                cYear = cYear - 11;

                Boolean dataFound;

                for (int k = 1; k <= 12; k++) {
                    dataFound = false;

                    for (Balance.ChartData cd : balance.getChartDataList()) {
                        if (cd.getYear() == cYear) {
                            dataFound = true;
                            eeData = eeData + "'" + cd.getEeShare() + "',";
                            erData = erData + "'" + cd.getErShare() + "',";
                            intData = intData + "'" + cd.getInterest() + "',";
                            labels = labels + "'" + cd.getYear() + "',";
                            break;
                        }
                    }
                    if (!dataFound) {
                        eeData = eeData + "'0',";
                        erData = erData + "'0',";
                        intData = intData + "'0',";
                        labels = labels + "'" + cYear + "',";
                    }
                    cYear++;
                }

//        for (int k = 0; k <= balance.getChartDataList().size() - 1; k++) {
//            if (k < balance.getChartDataList().size()) {
//                eeData = eeData + "'" + balance.getChartDataList().get(k).getEeShare() + "',";
//                erData = erData + "'" + balance.getChartDataList().get(k).getErShare() + "',";
//                intData = intData + "'" + balance.getChartDataList().get(k).getInterest() + "',";
//                labels = labels + "'" + balance.getChartDataList().get(k).getYear() + "',";
//            } else {
//                eeData = eeData + "'" + balance.getChartDataList().get(k).getEeShare() + "'";
//                erData = erData + "'" + balance.getChartDataList().get(k).getErShare() + "'";
//                intData = intData + "'" + balance.getChartDataList().get(k).getInterest() + "'";
//                labels = labels + "'" + balance.getChartDataList().get(k).getYear() + "'";
//            }
//        }
                eeData = eeData.substring(0, eeData.length() - 1) + "]";
                erData = erData.substring(0, erData.length() - 1) + "]";
                intData = intData.substring(0, intData.length() - 1) + "]";
                labels = labels.substring(0, labels.length() - 1) + "]";

                dataSet = new BarChart2.DataSet();
                label = "Employee Cont.";
                color = fun.genColor();
                dataSet.setData(eeData);
                dataSet.setLabel(label);
                dataSet.setColor(color);
                dataSetList.add(dataSet);

                dataSet = new BarChart2.DataSet();

                //data = "[0, 0, 0, 0, 0, 0, 0, 8000, 12000, 12000, 15000, 18000, 4000]";
                label = "Employer Cont.";
                color = fun.genColor();
                dataSet.setData(erData);
                dataSet.setLabel(label);
                dataSet.setColor(color);
                dataSetList.add(dataSet);

                dataSet = new BarChart2.DataSet();
                //data = "[0, 0, 0, 0, 0, 0, 0, 1000, 1500, 1500, 1800, 2000]";
                label = "Interest Earned";
                color = fun.genColor();
                dataSet.setData(intData);
                dataSet.setLabel(label);
                dataSet.setColor(color);
                dataSetList.add(dataSet);

                barChart2.setLabels(labels);
                barChart2.setText("Contribution Summary in last 12 years");
                barChart2.setDataSetist(dataSetList);

                barChart2.setSuccess(true);

            } catch (Exception e) {
                barChart2.setSuccess(false);
                barChart2.setError(e.toString());
            }

            return barChart2;

        }
    }

}
