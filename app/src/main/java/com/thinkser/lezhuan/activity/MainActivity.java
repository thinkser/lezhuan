package com.thinkser.lezhuan.activity;

import android.support.v4.view.ViewPager;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityMainBinding;
import com.thinkser.lezhuan.entity.Customer;
import com.thinkser.lezhuan.fragment.HomeFragment;
import com.thinkser.lezhuan.fragment.MessageFragment;
import com.thinkser.lezhuan.fragment.PersonFragment;
import com.thinkser.lezhuan.model.MainModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 主界面
 */

public class MainActivity extends BaseActivity<AppData, ActivityMainBinding>
        implements ViewPager.OnPageChangeListener {

    MainModel model;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected AppData getData() {
        return AppData.getAppData();
    }

    @Override
    protected void initView(ActivityMainBinding binding) {
        binding.viewpager.addOnPageChangeListener(this);
        binding.viewpager.setOffscreenPageLimit(3);
    }

    @Override
    protected void initData() {
        data.fragmentManager.set(getFragmentManager());
        data.fragments.add(new HomeFragment());
        data.fragments.add(new MessageFragment());
        data.fragments.add(new PersonFragment());
        model = new MainModel(this);
        model.text(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
log(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    //点击下方按钮切换界面
    public void changePager(int position) {
        data.position.set(position);
    }

    @Override
    public void onPageSelected(int position) {
        data.position.set(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
