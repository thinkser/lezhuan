package com.thinkser.lezhuan.activity;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityPublishCreateBinding;

/**
 * 我的发布
 */

public class PublishCreateActivity extends BaseActivity<AppData, ActivityPublishCreateBinding> {

    @Override
    protected int getLayout() {
        return R.layout.activity_publish_create;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

}
