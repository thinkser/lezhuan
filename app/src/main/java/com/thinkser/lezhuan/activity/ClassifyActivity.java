package com.thinkser.lezhuan.activity;

import android.support.v4.widget.SwipeRefreshLayout;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityClassifyBinding;

/**
 * 分类界面
 */

public class ClassifyActivity extends BaseActivity<AppData, ActivityClassifyBinding>
        implements SwipeRefreshLayout.OnRefreshListener {

    @Override
    protected int getLayout() {
        return R.layout.activity_classify;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    public void onRefresh() {

    }
}
