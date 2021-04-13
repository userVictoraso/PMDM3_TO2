package com.example.victoraso.myapplication.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    /**CHECK IF PHONE IS BETWEEN 9 DIGITS**/
    public static boolean checkPhone(String phone) {
        String pattern = "^\\d{9}$";
        if (phone.matches(pattern)) {
            return true;
        }
        return false;
    }

    /**CHECK IF HOUR IS BETWEEN 17 -20**/
    public static boolean checkHour(int hour) {
        if (hour < 17 || hour > 20) {
            return false;
        }
        return true;
    }

    /**CONVERT DATE**/
    public static Long getDateTimestamp(String stringDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date parsedDate = dateFormat.parse(stringDate);
            Long timestamp = parsedDate.getTime();
            return timestamp;
        } catch(Exception e) { //this generic but you can control another types of exception
            // look the origin of excption
        }
        return null;
    }

    public static String getDateString(Long longDate) {
        Date date = new Date(longDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateText = simpleDateFormat.format(date);
        return dateText;
    }
}

