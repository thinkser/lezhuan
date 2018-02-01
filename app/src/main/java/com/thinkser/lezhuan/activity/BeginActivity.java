package com.thinkser.lezhuan.activity;

import android.content.Intent;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.utils.PreferencesUtil;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.ActivityBeginBinding;
import com.thinkser.lezhuan.service.BeginService;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 欢迎界面
 */

public class BeginActivity extends BaseActivity<AppData, ActivityBeginBinding> {

    @Override
    protected int getLayout() {
        return R.layout.activity_begin;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData(Intent intent) {
        super.initData(intent);
        //初始化各种服务
        BeginService.startBeginService(this);
        if (!getLimit()) {
            begin();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0)
            begin();
    }

    private void begin() {
        PreferencesUtil preferencesUtil = new PreferencesUtil(this);
        if (preferencesUtil.getBoolean(CustomKey.isLogin)) {
            toMain();
        } else {
            data.isLogin.set(false);
        }
    }

    //跳转到首页
    private void toMain() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(BeginActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }

    //跳转到登录界面
    public void toLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    //跳转到注册界面
    public void toRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

}
