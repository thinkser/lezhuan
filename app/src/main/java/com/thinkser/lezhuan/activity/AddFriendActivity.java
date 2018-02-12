package com.thinkser.lezhuan.activity;

import android.support.v4.widget.SwipeRefreshLayout;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.view.MyRecyclerView;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityAddBinding;

/**
 * 添加好友界面
 */

public class AddFriendActivity extends BaseActivity<AppData, ActivityAddBinding>
        implements SwipeRefreshLayout.OnRefreshListener, MyRecyclerView.OnRecyclerScrollListener {

    @Override
    protected int getLayout() {
        return R.layout.activity_add;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    public void search() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void loadMore(MyRecyclerView view) {

    }
}
