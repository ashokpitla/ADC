/*
 * 
 * 
 */
package com.epfo.passbook2.common;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Sanjay
 */
public class fun {

    

    public static String getSaltId(int length) {
        String SALTCHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyx";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public static String formatN(Double input) {
        String output = "";

        try {
            String inputF = String.format("%.2f", input);

            int ldot;

            ldot = inputF.indexOf(".");

            String inputD = inputF.substring(ldot + 1, ldot + 3);

            String input1 = inputF.substring(0, ldot);

            ArrayList<String> str_rev = new ArrayList();
            int j = 0;

            int sindex;
            String _data;

            for (int i = 1; i <= input1.length(); i++) {

                sindex = input1.length() - i;
                _data = input1.substring(sindex, sindex + 1);
                str_rev.add(_data);

                if (i == 3 && i != input1.length()) {
                    str_rev.add((","));
                    j = 0;
                }

                if (j == 2 && i != input1.length()) {
                    str_rev.add((","));
                    j = 0;
                }

                j++;
            }

            for (int i = 0; i < str_rev.size(); i++) {
                output = str_rev.get(i) + output;
            }

            output = output + "." + inputD;
        } catch (Exception e) {
            System.out.print("formatN (Double) : " + e.toString());
            output = String.valueOf(input);
        }

        return output;
    }

    public static String formatN(String input) {
        String output = "";

        try {
            //String inputF = String.format("%.2f", input);

            int ldot;

            ldot = input.indexOf(".");
            String inputD = "";
            String input1;

            if (ldot > 0) {
                inputD = input.substring(ldot + 1, ldot + 3);
                input1 = input.substring(0, ldot);
            } else {
                input1 = input;
            }

            ArrayList<String> str_rev = new ArrayList();
            int j = 0;

            int sindex;
            String _data;

            for (int i = 1; i <= input1.length(); i++) {

                sindex = input1.length() - i;
                _data = input1.substring(sindex, sindex + 1);
                str_rev.add(_data);

                if (i == 3 && i != input1.length()) {
                    str_rev.add((","));
                    j = 0;
                }

                if (j == 2 && i != input1.length()) {
                    str_rev.add((","));
                    j = 0;
                }

                j++;
            }

            for (int i = 0; i < str_rev.size(); i++) {
                output = str_rev.get(i) + output;
            }

            if (inputD.length() > 0) {
                output = output + "." + inputD;
            }

        } catch (Exception e) {
            System.out.print("formatN (String) : " + e.toString());
            output = String.valueOf(input);
        }

        return output;
    }

    public static String formatN(Long input) {
        String output = "";

        try {
            String input1 = String.valueOf(input);

            ArrayList<String> str_rev = new ArrayList();
            int j = 0;

            int sindex;
            String _data;

            for (int i = 1; i <= input1.length(); i++) {

                sindex = input1.length() - i;
                _data = input1.substring(sindex, sindex + 1);
                str_rev.add(_data);

                if (i == 3 && i != input1.length()) {
                    str_rev.add((","));
                    j = 0;
                }

                if (j == 2 && i != input1.length()) {
                    str_rev.add((","));
                    j = 0;
                }

                j++;
            }

            for (int i = 0; i < str_rev.size(); i++) {
                output = str_rev.get(i) + output;
            }
        } catch (Exception e) {
            System.out.print("formatN (Long) : " + e.toString());
            output = String.valueOf(input);
        }

        return output;
    }

    public static String genColor() {
        Random random = new Random();
        int nextInt = random.nextInt(0xffffff + 1);
        return String.format("#%06x", nextInt);
    }

    public static LocalDate toLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date toDateFromLocal(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static String lpad(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);
        return sb.toString();
    }

    public static String lpad(String inputValue, int length, String av) {
        if (inputValue.length() >= length) {
            return inputValue;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputValue.length()) {
            sb.append(av);
        }
        sb.append(inputValue);
        return sb.toString();
    }

    public static String lpad(int inputValue, int length, String av) {
        if (String.valueOf(inputValue).length() >= length) {
            return String.valueOf(inputValue);
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - String.valueOf(inputValue).length()) {
            sb.append(av);
        }
        sb.append(String.valueOf(inputValue));
        return sb.toString();
    }

    public static String lpad(Long inputValue, int length, String av) {
        if (String.valueOf(inputValue).length() >= length) {
            return String.valueOf(inputValue);
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - String.valueOf(inputValue).length()) {
            sb.append(av);
        }
        sb.append(String.valueOf(inputValue));
        return sb.toString();
    }

}
