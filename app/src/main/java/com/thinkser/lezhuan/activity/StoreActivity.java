package com.thinkser.lezhuan.activity;

import android.content.Intent;

import com.thinkser.core.adapter.RecyclerAdapter;
import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.core.utils.PreferencesUtil;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.ActivityStoreBinding;
import com.thinkser.lezhuan.entity.Store;
import com.thinkser.lezhuan.item.StoreItem;
import com.thinkser.lezhuan.model.StoreModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的店铺
 */

public class StoreActivity extends BaseActivity<AppData, ActivityStoreBinding> {

    private List<StoreItem> list;
    private StoreModel model;
    private PreferencesUtil preferencesUtil;

    private int storeCount = -1;

    @Override
    protected int getLayout() {
        return R.layout.activity_store;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData(Intent intent) {
        super.initData(intent);
        list = new ArrayList<>();
        model = new StoreModel(this);
        preferencesUtil = new PreferencesUtil(this);
        data.adapter.set(new RecyclerAdapter(R.layout.item_store));
        getList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 7) {
            getList();
        }
    }

    //获取店铺列表信息
    private void getList() {
        model.getStoreList(preferencesUtil.getString(CustomKey.id),
                new BaseObserver<List<Store>>(null) {
                    @Override
                    protected void onSuccess(List<Store> stores) {
                        storeCount = stores.size();
                        list.clear();
                        for (Store store : stores) {
                            StoreItem storeItem = new StoreItem(StoreActivity.this, store);
                            list.add(storeItem);
                        }
                        data.adapter.get().addNew(list);
                    }
                });
    }

    public void toCreateStore() {
        if (storeCount == -1) {
            toast("正在获取列表信息，请稍候");
            return;
        }
        if (storeCount >= 3) {
            toast("最多创建三个店铺");
            return;
        }
        startActivityForResult(new Intent(this, StoreCreateActivity.class), 1);
    }

}
