package com.example.asus.subthreemvvm.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    @SuppressLint("SimpleDateFormat")
    private static String format(String date, String format) {
        String result = "";
        try {
            @SuppressLint("SimpleDateFormat") java.text.DateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date oldDate = oldDateFormat.parse(date);
            java.text.DateFormat newDateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");

            result = newDateFormat.format(oldDateFormat);
            return result;
        } catch (Exception e) {
            Log.d("DateFormat", "error date format");
        }
        return result;
    }

    public static String getDateFormat(String date) {
        return format(date, Utils.DATE_FORMAT);
    }

    public static String getDateDayFormat(String date) {
        return format(date, Utils.DATE_FORMAT_DAY);
    }
}
