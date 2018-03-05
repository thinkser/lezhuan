package com.thinkser.lezhuan.item;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;

import com.thinkser.lezhuan.activity.CodeActivity;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.entity.Store;

/**
 * 广告列表项
 */

public class StoreItem {

    private Store store;
    private Activity activity;
    private OnStoreItemClickListener listener;

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
        listener.onClick(store);
    }

    public interface OnStoreItemClickListener {
        void onClick(Store store);
    }

    public void setListener(OnStoreItemClickListener listener) {
        this.listener = listener;
    }

    public void toCode() {
        Intent intent = new Intent(activity, CodeActivity.class);
        intent.putExtra(CustomKey.userId, store.getObjectId());
        activity.startActivity(intent);
    }
}
