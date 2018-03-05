package com.thinkser.lezhuan.model;

import android.app.Activity;

import com.thinkser.core.base.BaseModel;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.lezhuan.api.PayAPI;
import com.thinkser.lezhuan.entity.Customer;

import java.util.Map;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 支付model
 */

public class PayModel extends BaseModel {

    public PayModel(Activity activity) {
        super(activity);
    }

    public void addMoney(String id, Float money, BaseObserver<Map<String, String>> baseObserver) {
        netUtil.getInstance(headers, PayAPI.class)
                .getMoney(Customer.class.getSimpleName(), id)
                .flatMap(new Function<Customer, ObservableSource<Map<String, String>>>() {
                    @Override
                    public ObservableSource<Map<String, String>> apply(Customer customer) throws Exception {
                        customer.clearSystemData();
                        customer.setMoney(customer.getMoney() + money);
                        return netUtil.getInstance(headers, PayAPI.class)
                                .resetMoney(Customer.class.getSimpleName(), id, getBody(customer));
                    }
                })
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    public void reduceMoney(String id, Float money, BaseObserver<Map<String, String>> baseObserver) {
        netUtil.getInstance(headers, PayAPI.class)
                .getMoney(Customer.class.getSimpleName(), id)
                .flatMap(new Function<Customer, ObservableSource<Map<String, String>>>() {
                    @Override
                    public ObservableSource<Map<String, String>> apply(Customer customer) throws Exception {
                        customer.clearSystemData();
                        customer.setMoney(customer.getMoney() - money);
                        return netUtil.getInstance(headers, PayAPI.class)
                                .resetMoney(Customer.class.getSimpleName(), id, getBody(customer));
                    }
                })
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    public void getMoney(String id, BaseObserver<Customer> baseObserver) {
        netUtil.getInstance(headers, PayAPI.class)
                .getMoney(Customer.class.getSimpleName(), id)
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }
}
