package com.thinkser.core.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thinkser.core.utils.MarkedUtil;

/**
 * 所有fragment继承此类。
 */

public abstract class BaseFragment<D, B extends ViewDataBinding> extends Fragment {

    protected D data;
    protected B binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = getData();
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), getLayout(), null, false);
        binding.setVariable(com.thinkser.core.BR.data, data);
        binding.setVariable(com.thinkser.core.BR.presenter, this);
        //添加动画效果
//        binding.addOnRebindCallback(new OnRebindCallback() {
//            @Override
//            public boolean onPreBind(ViewDataBinding binding) {
//                ViewGroup sceneRoot = (ViewGroup) binding.getRoot();
//                TransitionManager.beginDelayedTransition(sceneRoot);
//                return true;
//            }
//        });
        initData();
        initView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        data = getData();
//        B binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
//        binding.setVariable(com.thinkser.core.BR.data, data);
//        binding.setVariable(com.thinkser.core.BR.presenter, this);
        //添加动画效果
//        binding.addOnRebindCallback(new OnRebindCallback() {
//            @Override
//            public boolean onPreBind(ViewDataBinding binding) {
//                ViewGroup sceneRoot = (ViewGroup) binding.getRoot();
//                TransitionManager.beginDelayedTransition(sceneRoot);
//                return true;
//            }
//        });
//        initData();
//        initView(binding);
        return binding.getRoot();
    }

    protected abstract int getLayout();

    protected abstract D getData();

    protected void initView() {
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

    protected void skip(Class activity) {
        getActivity().startActivity(new Intent(getActivity(), activity));
    }

}
