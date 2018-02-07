package com.thinkser.lezhuan.activity;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.thinkser.core.adapter.RecyclerAdapter;
import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.ActivityStoreBinding;
import com.thinkser.lezhuan.entity.Store;
import com.thinkser.lezhuan.item.StoreItem;
import com.thinkser.lezhuan.model.StoreModel;

import java.util.List;

/**
 * 我的店铺
 */

public class StoreActivity extends BaseActivity<AppData, ActivityStoreBinding>
        implements StoreItem.OnStoreItemClickListener {

    private StoreModel model;
    private ObservableList<StoreItem> list;

    private int storeCount = -1;//-1代表未获取到数据
    public String titleText = "";
    private static final int REQUEST_CREATE_STORE = 1;

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
        titleText = intent.getStringExtra("titleText");
        model = new StoreModel(this);
        list = new ObservableArrayList<>();
        data.adapter.set(new RecyclerAdapter(R.layout.item_store, list));
        getList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CREATE_STORE && resultCode == RESULT_OK) {
            getList();
        }
    }

    //获取店铺列表信息
    private void getList() {
        model.getStoreList(preferencesUtil.getString(CustomKey.userId),
                new BaseObserver<List<Store>>(null) {
                    @Override
                    protected void onSuccess(List<Store> stores) {
                        if (stores == null || stores.size() == 0) {
                            storeCount = 0;
                            return;
                        }
                        storeCount = stores.size();
                        list.clear();
                        for (Store store : stores) {
                            StoreItem storeItem = new StoreItem(store);
                            storeItem.setListener(StoreActivity.this);
                            list.add(storeItem);
                        }
                    }
                });
    }

    //点击新建按钮
    public void toCreateStore() {
        if (storeCount == -1) {
            toast("正在获取列表信息，请稍候");
            return;
        }
        if (storeCount >= 3) {
            toast("最多创建三个店铺");
            return;
        }
        startActivityForResult(new Intent(this, StoreCreateActivity.class), REQUEST_CREATE_STORE);
    }

    @Override
    public void onClick(Store store) {
        if (titleText.equals("选择店铺")) {
            Intent intent = new Intent();
            intent.putExtra(CustomKey.info, store);
            activity.setResult(RESULT_OK, intent);
            activity.finish();
        } else {
            Intent intent = new Intent(activity, StoreCreateActivity.class);
            intent.putExtra(CustomKey.info, store);
            activity.startActivityForResult(intent, REQUEST_CREATE_STORE);
        }
    }
}
