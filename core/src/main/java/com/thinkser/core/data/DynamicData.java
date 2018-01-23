package com.thinkser.core.data;

import android.support.multidex.MultiDexApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态全局变量。
 */

public class DynamicData extends MultiDexApplication {

    public Map<String, Object> preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = new HashMap<>();
    }
}
