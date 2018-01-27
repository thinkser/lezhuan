package com.thinkser.lezhuan.activity;

import android.content.Intent;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityLoginBinding;
import com.thinkser.lezhuan.dialog.ProgressDialog;
import com.thinkser.lezhuan.entity.Customer;
import com.thinkser.lezhuan.model.BeginModel;

import java.util.List;
import java.util.Map;

/**
 * 用户登录界面
 */

public class LoginActivity extends BaseActivity<AppData, ActivityLoginBinding> {

    private BeginModel model;
    private ProgressDialog progressDialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    public void back() {
        finish();
    }

    @Override
    protected void initData() {
        super.initData();
        model = new BeginModel(this);
        progressDialog = new ProgressDialog(this);
    }

    public void login() {
        checkInfo();
    }

    //检查输入信息的合法性
    private void checkInfo() {
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
            toast(7003);
            return;
        }
        getUser();
    }

    //获取用户信息
    private void getUser() {
        progressDialog.showProgressDialog("请稍候", false);
        model.login(data.phone.get(),
                new BaseObserver<Map<String, List<Customer>>>(this, progressDialog.dialog) {
                    @Override
                    protected void onSuccess(Map<String, List<Customer>> map) {
                        disposeUser(map);
                    }
                });
    }

    //检查并处理用户信息
    private void disposeUser(Map<String, List<Customer>> map) {
        List<Customer> results = map.get("results");
        if (results == null || results.size() == 0) {
            toast(7004);
            return;
        }
        Customer user = results.get(0);
        if (user.getPassword().equals("" + data.password.get().hashCode())) {
            user.saveUser(this);
            toMain();
        } else {
            toast(7003);
        }
    }

    private void toMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void toForget() {
        startActivity(new Intent(this, ForgetActivity.class));
    }
}
