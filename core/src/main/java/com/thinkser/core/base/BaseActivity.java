package com.thinkser.core.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkser.core.BR;
import com.thinkser.core.R;
import com.thinkser.core.utils.MarkedUtil;
import com.thinkser.core.utils.SystemBarTintManager;

/**
 * 所有activity继承此类。
 */

public abstract class BaseActivity<D extends BaseData, B extends ViewDataBinding>
        extends Activity {

    protected D data;
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        B binding = DataBindingUtil.setContentView(this, getLayout());
        data = getData();
        binding.setVariable(BR.data, data);
        binding.setVariable(BR.presenter, this);
        //添加动画效果
        binding.addOnRebindCallback(new OnRebindCallback() {
            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                ViewGroup sceneRoot = (ViewGroup) binding.getRoot();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(sceneRoot);
                }
                return true;
            }
        });
        initStatus();
        initView(binding);
        initData();
    }

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

    protected abstract int getLayout();

    protected abstract D getData();

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
        Log.e(this.getClass().getName(), message);
    }

    //显示加载中对话框
    public void showProgressDialog(String text, boolean cancelable) {
        if (dialog == null)
            dialog = new Dialog(this);
        dialog.show();
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_progress, null, false);
        dialog.setContentView(binding.getRoot());
        binding.setVariable(BR.content, text);
        dialog.setCancelable(cancelable);
    }

    //取消加载中对话框
    public void cancelProgressDialog() {
        if (dialog != null) {
            dialog.setCancelable(false);
            dialog.cancel();
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
