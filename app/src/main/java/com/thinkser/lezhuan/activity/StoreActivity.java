package com.thinkser.lezhuan.activity;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityStoreBinding;

/**
 * 我的发布
 */

public class StoreActivity extends BaseActivity<AppData, ActivityStoreBinding> {

    @Override
    protected int getLayout() {
        return R.layout.activity_store;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    public void toCreateStore() {

    }

}
