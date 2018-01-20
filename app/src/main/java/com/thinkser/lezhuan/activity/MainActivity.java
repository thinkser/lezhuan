package com.thinkser.lezhuan.activity;

import android.support.v4.view.ViewPager;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityMainBinding;
import com.thinkser.lezhuan.fragment.HomeFragment;
import com.thinkser.lezhuan.fragment.MessageFragment;
import com.thinkser.lezhuan.fragment.PersonFragment;

/**
 * 主界面
 */

public class MainActivity extends BaseActivity<AppData, ActivityMainBinding>
        implements ViewPager.OnPageChangeListener {

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initView(ActivityMainBinding binding) {
        binding.viewpager.addOnPageChangeListener(this);
    }

    @Override
    protected void initData() {
        data.fragmentManager.set(getFragmentManager());
        data.fragments.add(new HomeFragment());
        data.fragments.add(new MessageFragment());
        data.fragments.add(new PersonFragment());
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
