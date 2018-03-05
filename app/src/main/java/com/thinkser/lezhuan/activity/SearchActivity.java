package com.thinkser.lezhuan.activity;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;

import com.thinkser.core.adapter.RecyclerAdapter;
import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.core.view.MyRecyclerView;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.ActivitySearchBinding;
import com.thinkser.lezhuan.entity.Publish;
import com.thinkser.lezhuan.item.ADItem;
import com.thinkser.lezhuan.item.PrizeItem;
import com.thinkser.lezhuan.model.MainModel;

import java.util.ArrayList;

/**
 * 搜索界面
 */

public class SearchActivity extends BaseActivity<AppData, ActivitySearchBinding>
        implements SwipeRefreshLayout.OnRefreshListener, MyRecyclerView.OnRecyclerScrollListener {

    private MainModel model;
    private ObservableList<ADItem> list;
    private RecyclerAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData(Intent intent) {
        super.initData(intent);
        model = new MainModel(this);
        data.adapter.set(new RecyclerAdapter(R.layout.item_ad));
        adapter = data.adapter.get();
    }

    public void search() {
        getList();
    }

    @Override
    public void onRefresh() {
        getList();
    }

    //根据关键字获取广告列表
    private void getList() {
        ArrayList<ADItem> list = new ArrayList<>();
        model.searchPublish(data.keyWord.get(),
                new BaseObserver<Publish>(null) {
                    @Override
                    protected void onSuccess(Publish publish) {
                        showList(publish, list);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
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
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra(CustomKey.info, publish);
        adItem.setADItemClickListener(() -> startActivity(intent));
        list.add(adItem);
    }

    @Override
    public void loadMore(MyRecyclerView view) {

    }
}
