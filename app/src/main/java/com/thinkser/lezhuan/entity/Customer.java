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
            portrait = "", findUrl = "", signature = "个性签名";
    private Float money = 8000f;

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getFindUrl() {
        return findUrl;
    }

    public void setFindUrl(String findUrl) {
        this.findUrl = findUrl;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    //保存用户信息
    public void saveUser(Activity activity) {
        new PreferencesUtil(activity)
                .setBoolean(CustomKey.isLogin, true)
                .setString(CustomKey.userId, getObjectId())
                .setString(CustomKey.username, username)
                .setString(CustomKey.phone, phone)
                .setString(CustomKey.portrait, portrait)
                .setString(CustomKey.signature, signature)
                .setString(CustomKey.findUrls, findUrl)
                .setFloat(CustomKey.money, money)
                .save();
    }

    //用户退出登录
    public void logout(Activity activity) {
        new PreferencesUtil(activity)
                .setBoolean(CustomKey.isLogin, false)
                .save();
    }
}
