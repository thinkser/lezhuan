package com.thinkser.lezhuan.entity;

import android.app.Activity;

import com.thinkser.core.utils.PreferencesUtil;
import com.thinkser.lezhuan.data.PreferenceKey;

import cn.bmob.v3.BmobObject;

/**
 * 用户实体类
 */

public class Customer extends BmobObject {

    private String username, phone, password,
            portrait = "", sex = "男";

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //保存用户信息
    public void saveData(Activity activity) {
        new PreferencesUtil(activity)
                .setString(PreferenceKey.token, getObjectId())
                .setString(PreferenceKey.username, username)
                .setString(PreferenceKey.phone, phone)
                .setString(PreferenceKey.sex, sex)
                .setString(PreferenceKey.portrait, portrait)
                .save();
    }

    //缓存当前用户信息
    public void login(Activity activity) {
        new PreferencesUtil(activity)
                .setBoolean(PreferenceKey.isLogin, true)
                .setString(PreferenceKey.token, getObjectId())
                .setString(PreferenceKey.username, username)
                .setString(PreferenceKey.phone, phone)
                .save();
    }

    //用户退出登录
    public void logout(Activity activity) {
        new PreferencesUtil(activity)
                .setBoolean(PreferenceKey.isLogin, false)
                .save();
    }
}
