package com.thinkser.lezhuan.entity;

import android.app.Activity;

import com.thinkser.core.base.BaseEntity;
import com.thinkser.core.utils.PreferencesUtil;
import com.thinkser.lezhuan.data.CustomKey;

/**
 * 用户实体类
 */

public class Customer extends BaseEntity {

    private String username, phone, password,
            portrait = "", sex = "男", signature = "个性签名";

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
    public void saveUser(Activity activity) {
        new PreferencesUtil(activity)
                .setBoolean(CustomKey.isLogin, true)
                .setString(CustomKey.id, getObjectId())
                .setString(CustomKey.username, username)
                .setString(CustomKey.phone, phone)
                .setString(CustomKey.sex, sex)
                .setString(CustomKey.portrait, portrait)
                .save();
    }

    //用户退出登录
    public void logout(Activity activity) {
        new PreferencesUtil(activity)
                .setBoolean(CustomKey.isLogin, false)
                .save();
    }
}
