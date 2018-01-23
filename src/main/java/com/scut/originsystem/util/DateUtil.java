package com.scut.originsystem.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    protected static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Calendar stringToCalendar(String str){
        try{
            Date date = format.parse(str);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date stringToDate(String str){
        try {
            return format.parse(str);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String getFutureDate(int future){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + future);
        Date date = calendar.getTime();
        String result = format.format(date);
        return result;
    }
}
