package com.thinkser.lezhuan.item;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;

import com.thinkser.lezhuan.activity.StoreCreateActivity;
import com.thinkser.lezhuan.entity.Store;

/**
 * 广告列表项
 */

public class StoreItem {

    private Activity activity;
    private Store store;

    public final ObservableField<String>
            storeName = new ObservableField<>(""),
            storeAddress = new ObservableField<>(""),
            storePhone = new ObservableField<>("");

    public StoreItem(Activity activity, Store store) {
        this.activity = activity;
        this.store = store;
        storeName.set(store.getStoreName());
        storePhone.set(store.getStorePhone());
        storeAddress.set(store.getStoreAddress());
    }

    public void click() {
        Intent intent = new Intent(activity, StoreCreateActivity.class);
        intent.putExtra("info", store);
        activity.startActivityForResult(intent,1);
    }
}
