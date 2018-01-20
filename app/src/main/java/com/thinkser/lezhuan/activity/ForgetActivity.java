package com.thinkser.lezhuan.activity;

import android.content.Intent;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.utils.BmobUtil;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.PreferenceKey;
import com.thinkser.lezhuan.databinding.ActivityForgetBinding;
import com.thinkser.lezhuan.entity.Customer;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 忘记密码界面
 */

public class ForgetActivity extends BaseActivity<AppData, ActivityForgetBinding> {

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
    protected void initView(ActivityForgetBinding binding) {
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

    //检查手机号是否存在
    private void verifyPhone(String phone) {
        new BmobUtil<Customer>()
                .query(PreferenceKey.phone, phone)
                .findObjects(new FindListener<Customer>() {
                    @Override
                    public void done(List<Customer> list, BmobException e) {
                        if (e != null) {
                            data.getCode.set("获取验证码");
                            toast(7007);
                            toast(e.getErrorCode());
                        } else {
                            customer = list.get(0);
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

    public void reset() {
        if (data.password.get().length() < 6) {
            toast(7005);
            return;
        }
        if (!data.check.get().equals(data.password.get())) {
            toast(7006);
            return;
        }
        BmobSMS.verifySmsCode(data.phone.get(), data.code.get(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    customer.setPassword(data.password.get().hashCode() + "");
                    customer.login(ForgetActivity.this);
                    customer.update(customer.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            toMain();
                        }
                    });
                } else {
                    toast(e.getErrorCode());
                }
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
        data.getCode.set("获取验证码");
    }
}
