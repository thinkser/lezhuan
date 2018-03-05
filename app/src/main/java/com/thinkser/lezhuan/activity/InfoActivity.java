package com.thinkser.lezhuan.activity;

import android.content.Intent;
import android.databinding.ObservableBoolean;

import com.thinkser.core.adapter.RecyclerAdapter;
import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.ActivityInfoBinding;
import com.thinkser.lezhuan.entity.Publish;
import com.thinkser.lezhuan.entity.Store;
import com.thinkser.lezhuan.entity.Transmit;
import com.thinkser.lezhuan.item.PrizeItem;
import com.thinkser.lezhuan.model.MainModel;
import com.thinkser.lezhuan.model.StoreModel;

import java.util.ArrayList;

/**
 * 广告详情界面
 */

public class InfoActivity extends BaseActivity<AppData, ActivityInfoBinding> {

    private StoreModel model;
    private MainModel mainModel;
    private String publishId;

    public final ObservableBoolean enable = new ObservableBoolean(true);

    @Override
    protected int getLayout() {
        return R.layout.activity_info;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData(Intent intent) {
        super.initData(intent);
        model = new StoreModel(this);
        mainModel = new MainModel(this);
        //显示广告信息
        Publish publish = (Publish) intent.getSerializableExtra(CustomKey.info);
        publishId = publish.getObjectId();
        data.images.addAll(publish.getPhotos());
        data.publishTitle.set(publish.getTitle());
        data.content.set(publish.getContent());
        data.images.addAll(publish.getPhotos());
        //显示奖品列表
        ArrayList<PrizeItem> ticket = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PrizeItem item = new PrizeItem();
            item.hasPrize.set(i < publish.getPrizeCount());
            ticket.add(item);
        }
        //初始化列表适配器
        data.adapter.set(new RecyclerAdapter(R.layout.item_prize));
        data.adapter.get().refresh(ticket);
        showStore(publish.getStoreId());
    }

    //显示店铺信息
    private void showStore(String id) {
        model.getStoreInfo(id, new BaseObserver<Store>(null) {
            @Override
            protected void onSuccess(Store store) {
                data.storeName.set(store.getStoreName());
                data.storePhone.set(store.getStorePhone());
                data.storeAddress.set(store.getStoreAddress());
            }
        });
    }

    public void transmit() {
        enable.set(false);
        mainModel.transmit(preferencesUtil.getString(CustomKey.userId), publishId,
                new BaseObserver<Transmit>(null) {
                    @Override
                    protected void onSuccess(Transmit transmit) {
                        toast("转发成功");
                    }
                });
    }
}
