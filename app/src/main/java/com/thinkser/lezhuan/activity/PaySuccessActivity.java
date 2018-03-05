package com.thinkser.lezhuan.activity;

import android.content.Intent;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.ActivitySuccessBinding;

/**
 * 支付成功界面
 */

public class PaySuccessActivity extends BaseActivity<AppData, ActivitySuccessBinding> {

    @Override
    protected int getLayout() {
        return R.layout.activity_success;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData(Intent intent) {
        super.initData(intent);
        float money = intent.getFloatExtra(CustomKey.money, 0);
        if ((money + 0.99) / 1 == money / 1) {
            data.pay.set(money + ".00");
        } else {
            data.pay.set(money + "");
        }
        data.storeName.set(intent.getStringExtra(CustomKey.info));
    }
}
