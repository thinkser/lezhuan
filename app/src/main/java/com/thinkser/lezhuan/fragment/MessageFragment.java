package com.thinkser.lezhuan.fragment;

import com.thinkser.core.base.BaseFragment;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.FragmentMessageBinding;

/**
 * 社交
 */

public class MessageFragment extends BaseFragment<AppData, FragmentMessageBinding> {

    @Override
    protected int getLayout() {
        return R.layout.fragment_message;
    }

    @Override
    protected AppData getData() {
        return AppData.getAppData();
    }
}
