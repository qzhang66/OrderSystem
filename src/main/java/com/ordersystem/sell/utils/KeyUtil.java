package com.ordersystem.sell.utils;

import java.util.Random;

public class KeyUtil {
    // generate unique key
    // format: Time + random number
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        System.currentTimeMillis();
        Integer number = random.nextInt(900000)+100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
