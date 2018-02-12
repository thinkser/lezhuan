package com.thinkser.lezhuan.activity;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityCardBinding;

/**
 * 我的卡片界面
 */

public class CardActivity extends BaseActivity<AppData, ActivityCardBinding> {

    @Override
    protected int getLayout() {
        return R.layout.activity_card;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }
}
