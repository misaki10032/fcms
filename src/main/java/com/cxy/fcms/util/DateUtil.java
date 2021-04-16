package com.cxy.fcms.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 4.制作一个工具类:DateUtil,进行日和字符串之间的格式转换.
 *     定义两个方法:
 * 	一个用于将字符串日期转为Date类型,并返回该Date类型
 * 	一个用于将Date类型转为指定格式的字符串形式,并返回该字符串
 */
public class DateUtil {
    public static Date strToDate(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(s);
        return date;
    }
    public static String dateToString(Date d){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str = simpleDateFormat.format(d);
        return str;
    }
    public int daymidnow(Date Date1, Date Date2) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(Date1);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(Date2);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
    }
}
