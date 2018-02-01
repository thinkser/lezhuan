package com.thinkser.lezhuan.activity;

import android.content.Intent;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityForgetBinding;
import com.thinkser.lezhuan.dialog.ProgressDialog;
import com.thinkser.lezhuan.entity.Customer;
import com.thinkser.lezhuan.model.BeginModel;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;

/**
 * 忘记密码界面
 */

public class ForgetActivity extends BaseActivity<AppData, ActivityForgetBinding> {

    private BeginModel model;
    private ProgressDialog progressDialog;

    private Timer timer;
    private TimerTask timerTask;
    private int time;
    private Customer customer;

    @Override
    protected int getLayout() {
        return R.layout.activity_forget;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData(Intent intent) {
        super.initData( intent);
        model = new BeginModel(this);
        progressDialog = new ProgressDialog(this);
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                data.getCode.set(time + "s重新发送");
                if (time > 1) {
                    time--;
                } else {
                    data.getCode.set("获取验证码");
                    cancel();
                }
            }
        };
    }

    //获取验证码
    public void getCode() {
        //本地判断
        if (data.phone.get().equals("")) {
            toast(7000);
            return;
        }
        if (data.phone.get().length() < 11) {
            toast(7001);
            return;
        }
        data.getCode.set("获取中");
        checkPhone();
    }

    //检查手机号是否注册
    private void checkPhone() {
        model.login(data.phone.get(),
                new BaseObserver<Map<String, List<Customer>>>(progressDialog.dialog) {
                    @Override
                    protected void onSuccess(Map<String, List<Customer>> map) {
                        List<Customer> list = map.get("results");
                        if (list == null || list.size() == 0) {//手机号未注册
                            toast(7004);
                            data.getCode.set("获取验证码");
                        } else {
                            customer = list.get(0);
                            sendCode();
                        }
                    }
                });
    }

    //发送验证码
    private void sendCode() {
        BmobSMS.requestSMSCode(this,
                data.phone.get(), "验证码", new RequestSMSCodeListener() {
                    @Override
                    public void done(Integer smsId, BmobException ex) {
                        if (ex == null) {//验证码发送成功
                            toast(7006);
                            time = 60;
                            timer.schedule(timerTask, 0, 1000);
                        } else {
                            toast(7007);
                            data.getCode.set("获取验证码");
                        }
                    }
                });
    }

    //重置密码
    public void reset() {
        //本地判断用户输入信息
        if (data.phone.get().equals("")) {
            toast(7000);
            return;
        }
        if (data.phone.get().length() < 11) {
            toast(7001);
            return;
        }
        if (data.code.get().equals("")) {
            toast(7013);
            return;
        }
        if (data.code.get().length() < 6) {
            toast(7014);
            return;
        }
        if (data.password.get().equals("")) {
            toast(7002);
            return;
        }
        if (data.check.get().equals("")) {
            toast(7010);
            return;
        }
        if (data.password.get().length() < 6) {
            toast(7011);
            return;
        }
        if (!data.password.get().equals(data.check.get())) {
            toast(7012);
            return;
        }
        progressDialog.showProgressDialog("请稍候", false);
        verifyCode();
    }

    //检查验证码
    private void verifyCode() {
        BmobSMS.verifySmsCode(this,
                data.phone.get(), data.code.get(), new VerifySMSCodeListener() {
                    @Override
                    public void done(BmobException ex) {
                        if (ex == null) {//短信验证码已验证成功
                            changeUser();
                        } else {
                            toast(7008);
                            progressDialog.cancelProgressDialog();
                        }
                    }
                });
    }

    //修改密码
    private void changeUser() {
        customer.setPassword(data.password.get().hashCode() + "");
        model.forget(customer.getObjectId(), customer,
                new BaseObserver<Map<String, String>>(progressDialog.dialog) {
                    @Override
                    protected void onSuccess(Map<String, String> map) {
                        customer.saveUser(ForgetActivity.this);
                        toMain();
                        progressDialog.cancelProgressDialog();
                    }
                });
    }

    private void toMain() {
        Intent intent = new Intent(ForgetActivity.this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
