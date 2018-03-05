package com.thinkser.lezhuan.model;

import android.app.Activity;

import com.google.gson.Gson;
import com.thinkser.core.base.BaseModel;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.lezhuan.activity.StoreActivity;
import com.thinkser.lezhuan.api.StoreAPI;
import com.thinkser.lezhuan.entity.Store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的店铺model
 */

public class StoreModel extends BaseModel {

    public StoreModel(Activity activity) {
        super(activity);
    }

    public void getStoreList(String userId, BaseObserver<List<Store>> baseObserver) {
        Map<String, String> where = new HashMap<>();
        where.put("userId", userId);
        netUtil.getInstance(headers, StoreAPI.class)
                .getStoreList(Store.class.getSimpleName(), new Gson().toJson(where))
                .map(map -> map.get("results"))
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    public void createStore(Store store, BaseObserver<Store> baseObserver) {
        netUtil.getInstance(headers, StoreAPI.class)
                .createStore(Store.class.getSimpleName(), getBody(store))
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    public void changeStore(String id, Store store, BaseObserver<Map<String, String>> baseObserver) {
        netUtil.getInstance(headers, StoreAPI.class)
                .changeStore(Store.class.getSimpleName(), id, getBody(store))
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    public void getStoreInfo(String id, BaseObserver<Store> baseObserver) {
        netUtil.getInstance(headers, StoreAPI.class)
                .getStoreInfo(Store.class.getSimpleName(), id)
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }
}
