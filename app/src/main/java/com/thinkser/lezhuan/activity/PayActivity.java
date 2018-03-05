package com.thinkser.lezhuan.activity;

import android.content.Intent;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.ActivityPayBinding;
import com.thinkser.lezhuan.dialog.ProgressDialog;
import com.thinkser.lezhuan.entity.Store;
import com.thinkser.lezhuan.model.PayModel;
import com.thinkser.lezhuan.model.StoreModel;

import java.util.Map;

/**
 * 支付界面
 */

public class PayActivity extends BaseActivity<AppData, ActivityPayBinding> {

    private String userId;
    private PayModel model;

    @Override
    protected int getLayout() {
        return R.layout.activity_pay;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData(Intent intent) {
        super.initData(intent);
        userId = intent.getStringExtra(CustomKey.info);
        model = new PayModel(this);
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.showProgressDialog("请稍候", false);
        new StoreModel(this).getStoreInfo(userId,
                new BaseObserver<Store>(dialog.dialog) {
                    @Override
                    protected void onSuccess(Store store) {
                        data.storeName.set(store.getStoreName());
                        userId = store.getUserId();
                    }
                });
    }

    public void pay() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.showProgressDialog("请稍候", false);
        //本地操作
        preferencesUtil.setFloat(CustomKey.money,
                preferencesUtil.getFloat(CustomKey.money) - Float.valueOf(data.pay.get()))
                .save();

        model.reduceMoney(preferencesUtil.getString(CustomKey.userId), Float.valueOf(data.pay.get()),
                new BaseObserver<Map<String, String>>(null) {
                    @Override
                    protected void onSuccess(Map<String, String> map) {
                        model.addMoney(userId, Float.valueOf(data.pay.get()),
                                new BaseObserver<Map<String, String>>(dialog.dialog) {
                                    @Override
                                    protected void onSuccess(Map<String, String> map) {
                                        Intent intent = new Intent(activity, PaySuccessActivity.class);
                                        intent.putExtra(CustomKey.info, data.storeName.get());
                                        intent.putExtra(CustomKey.money, Float.valueOf(data.pay.get()));
                                        activity.startActivity(intent);
                                        activity.finish();
                                    }
                                });
                    }
                });
    }
}
