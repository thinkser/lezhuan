package com.thinkser.lezhuan.activity;

import android.support.v4.widget.SwipeRefreshLayout;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivitySearchBinding;

/**
 * 搜索界面
 */

public class SearchActivity extends BaseActivity<AppData, ActivitySearchBinding>
        implements SwipeRefreshLayout.OnRefreshListener {

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    public void search() {

    }

    @Override
    public void onRefresh() {
        data.isRefresh.set(true);
    }

    //根据关键字获取广告列表
    private void getList() {

    }
}
