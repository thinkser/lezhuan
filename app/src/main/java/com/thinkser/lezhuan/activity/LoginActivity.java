package com.thinkser.lezhuan.activity;

import android.content.Intent;
import android.view.View;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityLoginBinding;
import com.thinkser.lezhuan.entity.Customer;
import com.thinkser.lezhuan.model.BeginModel;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 用户登录界面
 */

public class LoginActivity extends BaseActivity<AppData, ActivityLoginBinding> {

    BeginModel model;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initView(ActivityLoginBinding binding) {
    }

    public void back(){
        finish();
    }

    @Override
    protected void initData() {
        model = new BeginModel();
    }

    public void login() {
        if (data.phone.get().equals("")) {
            toast(7000);
            return;
        }
        if (data.phone.get().length() < 11) {
            toast(7001);
            return;
        }
        if (data.password.get().equals("")) {
            toast(7002);
            return;
        }
        if (data.password.get().length() < 6) {
            toast(7002);
            return;
        }
        showProgressDialog("请稍候", false);
        model.login(data.phone.get(), new FindListener<Customer>() {
            @Override
            public void done(List<Customer> list, BmobException e) {
                if (e == null) {
                    Customer user = list.get(0);
                    if (user.getPassword().equals("" + data.password.get().hashCode())) {
                        user.login(LoginActivity.this);
                        toMain();
                    } else {
                        toast(7002);
                    }
                } else {
                    toast(e.getErrorCode());
                }
                cancelProgressDialog();
            }
        });
    }

    private void toMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void toForget(View view) {
        startActivity(new Intent(this, ForgetActivity.class));
    }
}
