package com.thinkser.lezhuan.model;

import android.app.Activity;

import com.thinkser.core.base.BaseModel;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.core.utils.BmobUtil;
import com.thinkser.core.utils.NetUtil;
import com.thinkser.lezhuan.api.BeginAPI;
import com.thinkser.lezhuan.data.PreferenceKey;
import com.thinkser.lezhuan.entity.Customer;

import io.reactivex.Observable;
import io.reactivex.Observer;

import static com.thinkser.lezhuan.data.StaticData.APP_KEY;
import static com.thinkser.lezhuan.data.StaticData.BASE_URL;
import static com.thinkser.lezhuan.data.StaticData.REST_API;
import static com.thinkser.lezhuan.data.StaticData.TIME_OUT;

/**
 * 登录、注册、忘记密码的model。
 */

public class BeginModel extends BaseModel {

    private Activity activity;

    public BeginModel(Activity activity) {
        super(activity);
        this.activity = activity;
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

//    public void login(Customer customer, BaseObserver<String> observer) {
//        netUtil.getInstance(headers, BeginAPI.class)
//                .login(customer)
//                .compose(netUtil.compose())
//                .subscribe(observer);
//    }

    public void login(String phone, BmobUtil.FindListener<Customer> listener) {
        new BmobUtil<Customer>()
                .query("phone", phone)
                .findObjects(listener);
    }

    public void forget(Customer customer, BmobUtil.UpdateListener listener) {
        new BmobUtil<>().update(customer, listener);
    }

    //检查手机号是否存在
    public void verifyPhone(String phone, BmobUtil.FindListener<Customer> listener) {
        new BmobUtil<Customer>()
                .query(PreferenceKey.phone, phone)
                .findObjects(listener);
    }
}
