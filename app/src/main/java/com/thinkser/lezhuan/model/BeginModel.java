package com.thinkser.lezhuan.model;

import com.thinkser.core.utils.BmobUtil;
import com.thinkser.lezhuan.entity.Customer;

import cn.bmob.v3.listener.FindListener;

/**
 * Created by thinkser on 2018/1/16.
 */

public class BeginModel {

    public void login(String phone, FindListener<Customer> listener) {
        new BmobUtil<Customer>()
                .query("phone", phone)
                .findObjects(listener);
    }
}
