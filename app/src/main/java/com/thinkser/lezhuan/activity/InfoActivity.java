package com.thinkser.lezhuan.activity;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityInfoBinding;

/**
 * 广告详情界面
 */

public class InfoActivity extends BaseActivity<AppData, ActivityInfoBinding> {
    @Override
    protected int getLayout() {
        return R.layout.activity_info;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }
}
