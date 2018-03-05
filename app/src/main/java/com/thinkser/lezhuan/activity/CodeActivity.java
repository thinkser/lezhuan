package com.thinkser.lezhuan.activity;

import android.content.Context;
import android.content.Intent;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.ActivityCodeBinding;
import com.xys.zxing.zxing.encoding.EncodingUtils;

/**
 * 显示二维码界面
 */

public class CodeActivity extends BaseActivity<AppData, ActivityCodeBinding> {

    private String userId;

    @Override
    protected int getLayout() {
        return R.layout.activity_code;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData(Intent intent) {
        super.initData(intent);
        userId = intent.getStringExtra(CustomKey.userId);
    }

    @Override
    protected void initView(ActivityCodeBinding binding) {
        super.initView(binding);
        binding.code.setImageBitmap(EncodingUtils.createQRCode(userId,
                dp2px(500), dp2px(500), null));
    }

    private int dp2px(float dpValue) {
        float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
