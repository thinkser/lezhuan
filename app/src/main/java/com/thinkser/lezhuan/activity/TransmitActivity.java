package com.thinkser.lezhuan.activity;

import android.support.v4.widget.SwipeRefreshLayout;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.view.MyRecyclerView;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityTransmitBinding;

/**
 * 我的转发界面
 */

public class TransmitActivity extends BaseActivity<AppData, ActivityTransmitBinding>
        implements SwipeRefreshLayout.OnRefreshListener, MyRecyclerView.OnRecyclerScrollListener {

    @Override
    protected int getLayout() {
        return R.layout.activity_transmit;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void loadMore(MyRecyclerView view) {

    }
}
