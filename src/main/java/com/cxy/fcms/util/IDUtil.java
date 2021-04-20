package com.cxy.fcms.util;

import java.util.UUID;

public class IDUtil {

    public static String getID(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
