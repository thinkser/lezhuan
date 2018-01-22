package com.thinkser.lezhuan.fragment;

import com.thinkser.core.base.BaseFragment;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.FragmentHomeBinding;

/**
 * 首页
 */

public class HomeFragment extends BaseFragment<AppData, FragmentHomeBinding> {
    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected AppData getData() {
        return AppData.getAppData();
    }
}
