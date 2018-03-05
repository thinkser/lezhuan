package com.thinkser.lezhuan.activity;

import android.content.Intent;

import com.thinkser.core.adapter.RecyclerAdapter;
import com.thinkser.core.base.BaseActivity;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.ActivityNewBinding;
import com.thinkser.lezhuan.entity.Friend;
import com.thinkser.lezhuan.item.FriendItem;

import java.util.ArrayList;

/**
 * 新朋友界面
 */

public class NewFriendActivity extends BaseActivity<AppData, ActivityNewBinding> {

    @Override
    protected int getLayout() {
        return R.layout.activity_new;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData(Intent intent) {
        super.initData(intent);
        data.adapter.set(new RecyclerAdapter(R.layout.item_friend));
        ArrayList<Friend> newFriends = (ArrayList<Friend>) intent.getSerializableExtra(CustomKey.info);
        if (newFriends == null) return;
        ArrayList<FriendItem> list = new ArrayList<>();
        for (Friend friend : newFriends) {
            getItem(friend, list);
        }
        data.adapter.get().refresh(list);
    }

    //获取好友列表项
    private void getItem(Friend friend, ArrayList<FriendItem> list) {
        FriendItem item = new FriendItem(this, friend.getObjectId());
        item.friendId.set(friend.getFriendId());
        item.name.set(friend.getName());
        item.portrait.set(friend.getPortrait());
        item.signature.set(friend.getSignature());
        item.isFriend.set(false);
        item.state.set(friend.getState());
        list.add(item);
    }
}
