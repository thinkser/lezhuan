package com.thinkser.core.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.thinkser.core.BR;
import com.thinkser.core.R;
import com.thinkser.core.utils.MarkedUtil;
import com.thinkser.core.utils.SystemBarTintManager;

/**
 * 所有activity继承此类。
 */

public abstract class BaseActivity<D, B extends ViewDataBinding>
        extends AppCompatActivity {

    protected D data;
    public final static int REQUEST_READ_PHONE_STATE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        data = getData();
        super.onCreate(savedInstanceState);
        B binding = DataBindingUtil.setContentView(this, getLayout());
        binding.setVariable(BR.data, data);
        binding.setVariable(BR.presenter, this);
        //添加动画效果
        binding.addOnRebindCallback(new OnRebindCallback() {
            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                ViewGroup sceneRoot = (ViewGroup) binding.getRoot();
                TransitionManager.beginDelayedTransition(sceneRoot);
                return true;
            }
        });
        initStatus();
        initData();
        initView(binding);
        getLimit();
    }

    protected abstract int getLayout();

    protected abstract D getData();

    //初始化通知栏
    private void initStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorStatus);
    }

    protected void initView(B binding) {
    }

    protected void initData() {
    }

    protected void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void toast(int code) {
        Toast.makeText(this, MarkedUtil.getMessage(code), Toast.LENGTH_SHORT).show();
    }

    protected void log(String message) {
        Log.e(this.getClass().getSimpleName(), message);
    }

    /**
     * 获取系统权限
     */
    private void getLimit() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            toast("权限获取失败");
        }
    }

    /**
     * 以下方法为点击空白处隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    //根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘.
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    //获取InputMethodManager，隐藏软键盘
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
