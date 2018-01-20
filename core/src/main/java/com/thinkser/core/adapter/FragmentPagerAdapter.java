package com.thinkser.core.adapter;

import android.app.Fragment;
import android.app.FragmentManager;

import com.thinkser.core.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 通用的FragmentPagerAdapter。
 */

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;
    private String[] titles;

    public FragmentPagerAdapter(FragmentManager manager, List<Fragment> fragments) {
        super(manager);
        this.fragments = fragments;
    }

    public void setTitles(String[] titles){
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }
}
