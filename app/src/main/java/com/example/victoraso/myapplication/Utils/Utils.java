package com.example.victoraso.myapplication.Utils;

public class Utils {



    /**CHECK IF HOUR IS BETWEEN 17 -20**/
    public static boolean checkHour(int hour) {
        if (hour < 17 || hour > 20) {
            return false;
        }
        return true;
    }
}
