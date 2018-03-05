package com.thinkser.lezhuan.activity;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;

import com.thinkser.core.adapter.RecyclerAdapter;
import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.ActivityPublishBinding;
import com.thinkser.lezhuan.entity.Publish;
import com.thinkser.lezhuan.item.ADItem;
import com.thinkser.lezhuan.item.PrizeItem;
import com.thinkser.lezhuan.model.PublishModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的发布
 */

public class PublishActivity extends BaseActivity<AppData, ActivityPublishBinding>
        implements SwipeRefreshLayout.OnRefreshListener {

    private PublishModel model;
    private RecyclerAdapter adapter;

    private int publishCount = -1;//广告数量
    private static final int REQUEST_CREATE = 1;

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
        model = new PublishModel(this);
        //初始化列表适配器
        data.adapter.set(new RecyclerAdapter(R.layout.item_ad));
        adapter = data.adapter.get();

        getList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //新建或修改信息后返回
        if (requestCode == REQUEST_CREATE && resultCode == RESULT_OK) {
            data.isRefresh.set(true);
            getList();
        }
    }

    //获取我的发布列表数据
    public void getList() {
        data.isRefresh.set(true);
        model.getStoreList(preferencesUtil.getString(CustomKey.userId),
                new BaseObserver<List<Publish>>(null) {
                    @Override
                    protected void onSuccess(List<Publish> publishes) {
                        if (publishes == null || publishes.size() == 0) {
                            publishCount = 0;
                            data.isRefresh.set(false);
                            return;
                        }
                        publishCount = publishes.size();
                        ArrayList<ADItem> list = new ArrayList<>();
                        for (Publish publish : publishes) {
                            showList(publish, list);
                        }
                        adapter.refresh(list);
                        data.isRefresh.set(false);
                    }
                });
    }

    //显示发布列表
    private void showList(Publish publish, ArrayList<ADItem> list) {
        ObservableList<PrizeItem> prizes = new ObservableArrayList<>();
        //显示奖品列表
        for (int i = 0; i < 10; i++) {
            PrizeItem item = new PrizeItem();
            item.hasPrize.set(i < publish.getPrizeCount());
            prizes.add(item);
        }
        ADItem adItem = new ADItem(prizes);
        adItem.url.set(publish.getPhotos().get(0));
        adItem.title.set(publish.getTitle());
        adItem.integral.set("今日积分：" + publish.getIntegral());
        //设置列表项点击事件
        Intent intent = new Intent(activity, PublishCreateActivity.class);
        intent.putExtra(CustomKey.info, publish);
        adItem.setADItemClickListener(() -> activity.startActivityForResult(intent, REQUEST_CREATE));
        list.add(adItem);
    }

    //跳转到新建发布界面
    public void toCreatePublish() {
        if (publishCount == -1) {
            toast("正在获取列表信息，请稍候");
            return;
        }
        if (publishCount >= 5) {
            toast("最多发布五条广告");
            return;
        }
        startActivityForResult(new Intent(this,
                PublishCreateActivity.class), REQUEST_CREATE);
    }

    @Override
    public void onRefresh() {
        getList();
    }
}
