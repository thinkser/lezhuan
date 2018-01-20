package com.thinkser.lezhuan.activity;

import android.content.Intent;
import android.databinding.generated.callback.OnClickListener;
import android.view.View;

import com.thinkser.core.BR;
import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.utils.BmobUtil;
import com.thinkser.core.view.Title;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.PreferenceKey;
import com.thinkser.lezhuan.databinding.ActivityRegisterBinding;
import com.thinkser.lezhuan.entity.Customer;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 用户注册界面
 */

public class RegisterActivity extends BaseActivity<AppData, ActivityRegisterBinding> {

    private Timer timer;
    private TimerTask timerTask;
    private int time;

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initView(ActivityRegisterBinding binding) {
        binding.title.setOnClick((view) -> finish());
    }

    @Override
    protected void initData() {
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
        //检查手机号格式
        if (data.phone.get().equals("")) {
            toast(7000);
            return;
        }
        if (data.phone.get().length() < 11) {
            toast(7001);
            return;
        }
        data.getCode.set("获取中");
        verifyPhone(data.phone.get());
    }

    //检查手机号是否已被注册
    private void verifyPhone(String phone) {
        new BmobUtil<Customer>()
                .query(PreferenceKey.phone, phone)
                .findObjects(new FindListener<Customer>() {
                    @Override
                    public void done(List<Customer> list, BmobException e) {
                        if (e == null) {
                            data.getCode.set("获取验证码");
                            toast(7003);
                        } else {
                            sendCode();
                        }
                    }
                });
    }

    //发送验证码
    private void sendCode() {
        BmobSMS.requestSMSCode(data.phone.get(), "验证码",
                new QueryListener<Integer>() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null) {
                            time = 60;
                            timer.schedule(timerTask, 0, 1000);
                            toast("验证码已发送");
                        } else {
                            data.getCode.set("获取验证码");
                            toast(e.getErrorCode());
                        }
                    }
                });
    }

    public void register() {
        if (data.username.get().equals("")) {
            toast(7004);
            return;
        }
        if (data.password.get().length() < 6) {
            toast(7005);
            return;
        }
        if (!data.check.get().equals(data.password.get())) {
            toast(7006);
            return;
        }
        showProgressDialog("请稍候", false);
        BmobSMS.verifySmsCode(data.phone.get(), data.code.get(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Customer customer = new Customer();
                    customer.setPhone(data.phone.get());
                    customer.setUsername(data.username.get());
                    customer.setPassword(data.password.get().hashCode() + "");
                    customer.login(RegisterActivity.this);
                    customer.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            toMain();
                        }
                    });
                } else {
                    toast(e.getErrorCode());
                }
                cancelProgressDialog();
            }
        });
    }

    //跳转到主界面
    private void toMain() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        data.getCode.set("获取验证码");
    }
}
