package com.thinkser.core.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.thinkser.core.base.BaseApplication;

/**
 * 本地数据保存
 */

public class PreferencesUtil {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private BaseApplication baseApplication;

    public PreferencesUtil(Context activity) {
        preferences = activity.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        baseApplication = (BaseApplication) activity.getApplicationContext();//全局变量
        editor = preferences.edit();
    }

    /*将数据保存到本地并设置为全局变量*/
    public PreferencesUtil setString(String name, String value) {
        editor.putString(name, value);
        baseApplication.preferences.put(name, value);
        return this;
    }

    public PreferencesUtil setInt(String name, int value) {
        editor.putInt(name, value);
        baseApplication.preferences.put(name, value);
        return this;
    }

    public PreferencesUtil setBoolean(String name, boolean value) {
        editor.putBoolean(name, value);
        baseApplication.preferences.put(name, value);
        return this;
    }

    public PreferencesUtil setFloat(String name, float value) {
        editor.putFloat(name, value);
        baseApplication.preferences.put(name, value);
        return this;
    }

    public void save() {
        editor.apply();
    }

    /*如果存在全局变量直接返回，否则从本地取出并设置为全局变量*/
    public String getString(String name) {
        String value = (String) baseApplication.preferences.get(name);
        if (value == null) {
            value = preferences.getString(name, "");
            baseApplication.preferences.put(name, value);
        }
        return value;
    }

    public int getInt(String name) {
        Integer value = (Integer) baseApplication.preferences.get(name);
        if (value == null) {
            value = preferences.getInt(name, 0);
            baseApplication.preferences.put(name, value);
        }
        return value;
    }

    public boolean getBoolean(String name) {
        Boolean value = (Boolean) baseApplication.preferences.get(name);
        if (value == null) {
            value = preferences.getBoolean(name, false);
            baseApplication.preferences.put(name, value);
        }
        return value;
    }

    public float getFloat(String name) {
        Float value = (Float) baseApplication.preferences.get(name);
        if (value == null) {
            value = preferences.getFloat(name, 0);
            baseApplication.preferences.put(name, value);
        }
        return value;
    }

    public void remove(String name) {
        editor.remove(name).apply();
        baseApplication.preferences.remove(name);
    }

    public void clear() {
        editor.clear().apply();
        baseApplication.preferences.clear();
    }
}
