package com.thinkser.lezhuan.model;

import com.thinkser.core.utils.BmobUtil;
import com.thinkser.lezhuan.entity.Customer;

/**
 * 首页、好友、个人中心的model。
 */

public class MainModel {

    //获取用户信息
    public void getPerson(String id, BmobUtil.QueryListener<Customer> listener) {
        new BmobUtil<Customer>()
                .getObject(id, listener);
    }
}
