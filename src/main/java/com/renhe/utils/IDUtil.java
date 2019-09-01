package com.renhe.utils;

import java.util.UUID;

public class IDUtil {

    public static String generate(){
       return  UUID.randomUUID().toString().replace("-","").toLowerCase();
    }

}
