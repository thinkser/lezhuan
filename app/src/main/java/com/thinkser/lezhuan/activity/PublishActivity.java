package com.thinkser.lezhuan.activity;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityPublishBinding;

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
        return AppData.getAppData();
    }
}
