package com.thinkser.lezhuan.activity;

import android.content.Intent;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityWalletBinding;

/**
 * 我的钱包界面
 */

public class WalletActivity extends BaseActivity<AppData, ActivityWalletBinding> {

    @Override
    protected int getLayout() {
        return R.layout.activity_wallet;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData(Intent intent) {
        super.initData(intent);
        data.balance.set("3.60");
    }

    public void toRecharge(){

    }

    public void toWithdraw(){

    }
}
