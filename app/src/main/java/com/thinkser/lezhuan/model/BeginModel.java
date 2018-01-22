package com.thinkser.lezhuan.model;

import com.thinkser.core.utils.BmobUtil;
import com.thinkser.lezhuan.data.PreferenceKey;
import com.thinkser.lezhuan.entity.Customer;

/**
 * 登录、注册、忘记密码的model。
 */

public class BeginModel {

    public void login(String phone, BmobUtil.FindListener<Customer> listener) {
        new BmobUtil<Customer>()
                .query("phone", phone)
                .findObjects(listener);
    }

    public void forget( Customer customer, BmobUtil.UpdateListener listener) {
        new BmobUtil<>().update(customer, listener);
    }

    //检查手机号是否存在
    public void verifyPhone(String phone, BmobUtil.FindListener<Customer> listener) {
        new BmobUtil<Customer>()
                .query(PreferenceKey.phone, phone)
                .findObjects(listener);
    }
}
