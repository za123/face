package com.qmr.common;

import java.util.UUID;

public class utils {

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


}
