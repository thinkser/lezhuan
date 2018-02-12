package com.thinkser.lezhuan.activity;

import android.support.v4.widget.SwipeRefreshLayout;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityCollectBinding;

/**
 * 我的收藏界面
 */

public class CollectActivity extends BaseActivity<AppData, ActivityCollectBinding>
        implements SwipeRefreshLayout.OnRefreshListener {

    @Override
    protected int getLayout() {
        return R.layout.activity_collect;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    public void onRefresh() {

    }
}
