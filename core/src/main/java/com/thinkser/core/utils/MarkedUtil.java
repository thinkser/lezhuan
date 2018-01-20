package com.thinkser.core.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 所有提示语
 */

public class MarkedUtil {

    private static Map<Integer, String> map = new HashMap<>();

    public static void addMark(int code, String message) {
        map.put(code, message);
    }

    public static String getMessage(int code) {
        String message = map.get(code);
        if (message == null) {
            message = code + "";
        }
        return message;
    }
}
