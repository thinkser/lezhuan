package com.thinkser.lezhuan.model;

import android.app.Activity;

import com.thinkser.core.base.BaseModel;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.core.utils.BmobUtil;
import com.thinkser.lezhuan.api.BeginAPI;
import com.thinkser.lezhuan.entity.Customer;

import io.reactivex.Observable;
import io.reactivex.Observer;

import static com.thinkser.lezhuan.data.StaticData.APP_KEY;
import static com.thinkser.lezhuan.data.StaticData.BASE_URL;
import static com.thinkser.lezhuan.data.StaticData.REST_API;
import static com.thinkser.lezhuan.data.StaticData.TIME_OUT;

/**
 * 首页、好友、个人中心的model。
 */

public class MainModel extends BaseModel {


    public MainModel(Activity activity) {
        super(activity);
    }

    //获取用户信息
    public void getPerson(String id, BmobUtil.QueryListener<Customer> listener) {
        new BmobUtil<Customer>()
                .getObject(id, listener);
    }

    public void text( Observer<String> observer){
        netUtil.getInstance(headers, BeginAPI.class)
                .text()
                .compose(netUtil.compose())
                .subscribe(observer);
    }

    @Override
    protected String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    protected String getApplicationID() {
        return APP_KEY;
    }

    @Override
    protected String getRestApiKey() {
        return REST_API;
    }

    @Override
    protected int getTimeOut() {
        return TIME_OUT;
    }
}
