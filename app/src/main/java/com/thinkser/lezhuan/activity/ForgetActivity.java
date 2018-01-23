package com.thinkser.lezhuan.activity;

import android.content.Intent;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.utils.BmobUtil;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityForgetBinding;
import com.thinkser.lezhuan.entity.Customer;
import com.thinkser.lezhuan.model.BeginModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.exception.BmobException;

/**
 * 忘记密码界面
 */

public class ForgetActivity extends BaseActivity<AppData, ActivityForgetBinding> {

    private BeginModel model;
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
        return AppData.getAppData();
    }

    public void back() {
        finish();
    }

    @Override
    protected void initData() {
        model = new BeginModel(this);
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
        model.verifyPhone(phone, new BmobUtil.FindListener<Customer>() {
            @Override
            public void success(List<Customer> list) {
                customer = list.get(0);
                sendCode();
            }

            @Override
            public void failed(BmobException e) {
                data.getCode.set("获取验证码");
                toast(7007);
                toast(e.getErrorCode());
            }
        });
    }

    //发送验证码
    private void sendCode() {
        new BmobUtil<>()
                .sendCode(data.phone.get(), new BmobUtil.QueryListener<Integer>() {
                    @Override
                    public void success(Integer integer) {
                        time = 60;
                        timer.schedule(timerTask, 0, 1000);
                        toast("验证码已发送");
                    }

                    @Override
                    public void failed(BmobException e) {
                        data.getCode.set("获取验证码");
                        toast(e.getErrorCode());
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
        showProgressDialog("请稍候", false);
        new BmobUtil().verifyCode(data.phone.get(), data.code.get(),
                new BmobUtil.VerifyListener() {
                    @Override
                    public void success() {
                        customer.setPassword(data.password.get().hashCode() + "");
                        customer.login(ForgetActivity.this);
                        updatePassword(customer);
                    }

                    @Override
                    public void failed(BmobException e) {
                        toast(e.getErrorCode());
                        cancelProgressDialog();
                    }
                });
    }

    //修改密码
    public void updatePassword(Customer customer) {
        model.forget(customer, new BmobUtil.UpdateListener() {
            @Override
            public void success() {
                toMain();
                cancelProgressDialog();
            }

            @Override
            public void failed(BmobException e) {
                toast(e.getErrorCode());
                cancelProgressDialog();
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
