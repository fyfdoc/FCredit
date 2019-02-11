package com.fcredit.util;

import java.util.UUID;

public class AppUtil {

    /**
     * 获取UUID
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
