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

    public static void main(String[] args){
        System.out.println(getToday());
    }


}
