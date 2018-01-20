package com.thinkser.lezhuan.fragment;

import com.thinkser.core.base.BaseFragment;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.FragmentPersonBinding;

/**
 * 个人中心
 */

public class PersonFragment extends BaseFragment<AppData, FragmentPersonBinding> {

    @Override
    protected int getLayout() {
        return R.layout.fragment_person;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initView(FragmentPersonBinding binding) {

    }
}
