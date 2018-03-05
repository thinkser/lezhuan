package com.thinkser.lezhuan.activity;

import android.content.Intent;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.ActivityWalletBinding;
import com.thinkser.lezhuan.entity.Customer;
import com.thinkser.lezhuan.model.PayModel;

/**
 * 我的钱包界面
 */

public class WalletActivity extends BaseActivity<AppData, ActivityWalletBinding> {

    private PayModel model;

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
        data.balance.set(preferencesUtil.getFloat(CustomKey.money) + "");
        model = new PayModel(this);
        model.getMoney(preferencesUtil.getString(CustomKey.userId),
                new BaseObserver<Customer>(null) {
                    @Override
                    protected void onSuccess(Customer customer) {
                        preferencesUtil.setFloat(CustomKey.money, customer.getMoney())
                                .save();
                        if ((customer.getMoney() + 0.99) / 1 == customer.getMoney() / 1) {
                            data.balance.set(customer.getMoney() + ".00");
                        } else {
                            data.balance.set(customer.getMoney() + "");
                        }
                    }
                });
    }

    public void toRecharge() {

    }

    public void toWithdraw() {

    }
}
