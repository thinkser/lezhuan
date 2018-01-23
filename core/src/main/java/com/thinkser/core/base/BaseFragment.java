package com.thinkser.core.base;

import android.app.Dialog;
import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thinkser.core.BR;
import com.thinkser.core.R;
import com.thinkser.core.utils.MarkedUtil;

/**
 * 所有fragment继承此类。
 */

public abstract class BaseFragment<D, B extends ViewDataBinding> extends Fragment {

    protected D data;
    protected Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        data = getData();
        B binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        binding.setVariable(com.thinkser.core.BR.data, data);
        binding.setVariable(com.thinkser.core.BR.presenter, this);
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
        initView(binding);
        initData();
        return binding.getRoot();
    }

    protected abstract int getLayout();

    protected abstract D getData();

    protected void initView(B binding) {
    }

    protected void initData() {
    }

    protected void toast(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void toast(int code) {
        Toast.makeText(this.getActivity(), MarkedUtil.getMessage(code), Toast.LENGTH_SHORT).show();
    }

    protected void log(String message) {
        Log.e(this.getClass().getName(), message);
    }

    //显示加载中对话框
    public void showProgressDialog(String text, boolean cancelable) {
        if (dialog == null)
            dialog = new Dialog(this.getActivity());
        dialog.show();
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this.getActivity()), R.layout.dialog_progress, null, false);
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

}
