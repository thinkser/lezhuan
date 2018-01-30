package com.thinkser.core.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.thinkser.core.data.DynamicData;

/**
 * 本地数据保存
 */

public class PreferencesUtil {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private DynamicData dynamicData;

    public PreferencesUtil(Context activity) {
        preferences = activity.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        dynamicData = (DynamicData) activity.getApplicationContext();//全局变量
        editor = preferences.edit();
    }

    /*将数据保存到本地并设置为全局变量*/
    public PreferencesUtil setString(String name, String value) {
        editor.putString(name, value);
        dynamicData.preferences.put(name, value);
        return this;
    }

    public PreferencesUtil setInt(String name, int value) {
        editor.putInt(name, value);
        dynamicData.preferences.put(name, value);
        return this;
    }

    public PreferencesUtil setBoolean(String name, boolean value) {
        editor.putBoolean(name, value);
        dynamicData.preferences.put(name, value);
        return this;
    }

    public PreferencesUtil setFloat(String name, float value) {
        editor.putFloat(name, value);
        dynamicData.preferences.put(name, value);
        return this;
    }

    public void save() {
        editor.apply();
    }

    /*如果存在全局变量直接返回，否则从本地取出并设置为全局变量*/
    public String getString(String name) {
        String value = (String) dynamicData.preferences.get(name);
        if (value == null) {
            value = preferences.getString(name, "");
            dynamicData.preferences.put(name, value);
        }
        return value;
    }

    public int getInt(String name) {
        Integer value = (Integer) dynamicData.preferences.get(name);
        if (value == null) {
            value = preferences.getInt(name, 0);
            dynamicData.preferences.put(name, value);
        }
        return value;
    }

    public boolean getBoolean(String name) {
        Boolean value = (Boolean) dynamicData.preferences.get(name);
        if (value == null) {
            value = preferences.getBoolean(name, false);
            dynamicData.preferences.put(name, value);
        }
        return value;
    }

    public float getFloat(String name) {
        Float value = (Float) dynamicData.preferences.get(name);
        if (value == null) {
            value = preferences.getFloat(name, 0);
            dynamicData.preferences.put(name, value);
        }
        return value;
    }

    public void remove(String name) {
        editor.remove(name).apply();
        dynamicData.preferences.remove(name);
    }

    public void clear() {
        editor.clear().apply();
        dynamicData.preferences.clear();
    }
}
