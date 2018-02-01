package com.thinkser.lezhuan.model;

import android.app.Activity;

import com.google.gson.Gson;
import com.thinkser.core.base.BaseModel;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.lezhuan.api.StoreAPI;
import com.thinkser.lezhuan.entity.Store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

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
                .map(new Function<Map<String, List<Store>>, List<Store>>() {
                    @Override
                    public List<Store> apply(Map<String, List<Store>> map) throws Exception {
                        return map.get("results");
                    }
                })
                .filter(new Predicate<List<Store>>() {
                    @Override
                    public boolean test(List<Store> list) throws Exception {
                        return list != null && list.size() > 0;
                    }
                })
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
}
