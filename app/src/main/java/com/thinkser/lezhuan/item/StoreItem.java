package com.thinkser.lezhuan.item;

import android.databinding.ObservableField;

import com.thinkser.lezhuan.entity.Store;

/**
 * 广告列表项
 */

public class StoreItem {

    private Store store;
    private OnStoreItemClickListener listener;

    public final ObservableField<String>
            storeName = new ObservableField<>(""),
            storeAddress = new ObservableField<>(""),
            storePhone = new ObservableField<>("");

    public StoreItem(Store store) {
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
}
