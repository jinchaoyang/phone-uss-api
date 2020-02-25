package com.renhe.utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final String DATE_PATTERN="yyyy-MM-dd";

    public static final String DATE_TIME_PATTERN="yyyy-MM-dd HH:mm:ss";

    public static String getToday(){
        return  LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    public static String getNow(){
        return  LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    public static String getNow(String pattern){
        return  LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime getDateTime(String dateTimeStr,String pattern){
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime plusMonths(LocalDateTime dateTime,int months){
        return dateTime.plusMonths(months);
    }

    public static String localDateTimeFormat(LocalDateTime dateTime,String pattern){
       return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static void main(String[] args){
        System.out.println(getToday());
    }


}
