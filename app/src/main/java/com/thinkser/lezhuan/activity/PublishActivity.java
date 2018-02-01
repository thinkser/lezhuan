package com.thinkser.lezhuan.activity;

import android.content.Intent;

import com.thinkser.core.adapter.RecyclerAdapter;
import com.thinkser.core.base.BaseActivity;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityPublishBinding;
import com.thinkser.lezhuan.item.ADItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的发布
 */

public class PublishActivity extends BaseActivity<AppData, ActivityPublishBinding> {

    @Override
    protected int getLayout() {
        return R.layout.activity_publish;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData(Intent intent) {
        super.initData(intent);
        List<ADItem> list = new ArrayList<>();
        showList(list);
        data.adapter.set(new RecyclerAdapter(R.layout.item_ad));
        data.adapter.get().addNew(list);
    }

    private void showList(List<ADItem> list) {
        ADItem adItem = new ADItem(this);
        adItem.distance.set("100m");
        adItem.title.set("这是广告");
        adItem.label.set("美食|东港区");
        showPrize(adItem, 4);
        list.add(adItem);
    }

    private void showPrize(ADItem adItem, int size) {
        for (int i = 0; i < 10; i++) {
            adItem.prize.add(i < size);
        }
    }

    public void toCreatePublish() {
        skip(PublishCreateActivity.class);
    }

}
