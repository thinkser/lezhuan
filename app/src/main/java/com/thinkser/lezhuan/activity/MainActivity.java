package com.thinkser.lezhuan.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.thinkser.core.adapter.FragmentPagerAdapter;
import com.thinkser.core.base.BaseActivity;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.ActivityMainBinding;
import com.thinkser.lezhuan.fragment.FriendFragment;
import com.thinkser.lezhuan.fragment.HomeFragment;
import com.thinkser.lezhuan.fragment.PersonFragment;

import java.util.ArrayList;
import java.util.List;

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
        super.initView(binding);
        binding.viewpager.setOffscreenPageLimit(2);
        binding.viewpager.addOnPageChangeListener(this);
    }

    @Override
    protected void initData(Intent intent) {
        super.initData( intent);
        getLimit();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new FriendFragment());
        fragments.add(new PersonFragment());
        data.fragmentPagerAdapter.set(new FragmentPagerAdapter(getSupportFragmentManager(),fragments));
    }

    //点击下方按钮切换界面
    public void changePager(int position) {
        data.position.set(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        data.position.set(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
