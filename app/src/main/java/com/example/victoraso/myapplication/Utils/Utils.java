package com.example.victoraso.myapplication.Utils;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {



    /**CHECK IF HOUR IS BETWEEN 17 -20**/
    public static boolean checkHour(int hour) {
        if (hour < 17 || hour > 20) {
            return false;
        }
        return true;
    }

    /**CONVERT DATE**/
    public static Date getDate(String dateString) {
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**CONVERT DATE**/
    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}
