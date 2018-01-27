package com.thinkser.lezhuan.model;

import android.app.Activity;

import com.google.gson.Gson;
import com.thinkser.core.base.BaseModel;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.lezhuan.api.BeginAPI;
import com.thinkser.lezhuan.entity.Customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录、注册、忘记密码的model。
 */

public class BeginModel extends BaseModel {

    public BeginModel(Activity activity) {
        super(activity);
    }

    public void login(String phone, BaseObserver<Map<String, List<Customer>>> baseObserver) {
        Map<String, String> where = new HashMap<>();
        where.put("phone", phone);
        netUtil.getInstance(headers, BeginAPI.class)
                .login(Customer.class.getSimpleName(), new Gson().toJson(where))
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    public void register(Customer customer, BaseObserver<Customer> baseObserver) {
        netUtil.getInstance(headers, BeginAPI.class)
                .register(Customer.class.getSimpleName(), getBody(customer))
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    public void forget(String id, Customer customer, BaseObserver<Map<String, String>> baseObserver) {
        netUtil.getInstance(headers, BeginAPI.class)
                .forget(Customer.class.getSimpleName(), id, getBody(customer))
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

}
