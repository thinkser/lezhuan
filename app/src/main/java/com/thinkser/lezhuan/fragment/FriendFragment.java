package com.thinkser.lezhuan.fragment;

import com.thinkser.core.base.BaseFragment;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.FragmentFriendBinding;

/**
 * 好友
 */

public class FriendFragment extends BaseFragment<AppData, FragmentFriendBinding> {

    @Override
    protected int getLayout() {
        return R.layout.fragment_friend;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }
}
