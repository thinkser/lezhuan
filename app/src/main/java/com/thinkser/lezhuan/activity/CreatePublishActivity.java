package com.thinkser.lezhuan.activity;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityCreatePublishBinding;

/**
 * 我的发布
 */

public class CreatePublishActivity extends BaseActivity<AppData, ActivityCreatePublishBinding> {

    @Override
    protected int getLayout() {
        return R.layout.activity_create_publish;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    public void toCreatePublish() {

    }

}
