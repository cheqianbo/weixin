package com.onesuo.app.common.utils;

import java.util.UUID;

public class UUIDUtils {
    /**
     * 获取UUID
     * @return 32位UUID
     */
    public static String getRandomChar(){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        return uuid;
    }

    public static void main(String[] args) {
        System.out.println(UUIDUtils.getRandomChar());
    }
}
