package com.thinkser.core.data;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态全局变量。
 */

public class DynamicData extends Application {

    public Map<String, Object> preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = new HashMap<>();
    }

}
