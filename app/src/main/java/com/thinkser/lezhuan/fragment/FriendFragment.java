package com.thinkser.lezhuan.fragment;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;

import com.thinkser.core.adapter.RecyclerAdapter;
import com.thinkser.core.base.BaseFragment;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.activity.AddFriendActivity;
import com.thinkser.lezhuan.activity.NewFriendActivity;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.FragmentFriendBinding;
import com.thinkser.lezhuan.entity.Friend;
import com.thinkser.lezhuan.item.FriendItem;
import com.thinkser.lezhuan.model.FriendModel;

import java.util.ArrayList;

/**
 * 好友
 */

public class FriendFragment extends BaseFragment<AppData, FragmentFriendBinding>
        implements SwipeRefreshLayout.OnRefreshListener {

    private FriendModel model;
    private ObservableList<FriendItem> list;

    private ArrayList<Friend> allFriends, newFriends, friends;

    @Override
    protected int getLayout() {
        return R.layout.fragment_friend;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ObservableArrayList<>();
        model = new FriendModel(getActivity());
        allFriends = new ArrayList<>();
        newFriends = new ArrayList<>();
        friends = new ArrayList<>();
        data.adapter.set(new RecyclerAdapter(R.layout.item_friend, list));
        getList();
    }

    //跳转到搜索界面
    public void toAdd() {
        ArrayList<String> info = new ArrayList<>();
        for (Friend friend : allFriends) {
            info.add(friend.getFriendId());
            info.add(friend.getUserId());
        }
        Intent intent = new Intent(getActivity(), AddFriendActivity.class);
        intent.putStringArrayListExtra(CustomKey.info, info);
        startActivity(intent);
    }

    //跳转到新朋友界面
    public void toNew() {
        Intent intent = new Intent(getActivity(), NewFriendActivity.class);
        intent.putExtra(CustomKey.info, newFriends);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        if (list.size()==0){
            getList();
        }else {
            data.isRefresh.set(true);
            data.isRefresh.set(false);
        }
    }

    //获取好友列表
    private void getList() {
        data.isRefresh.set(true);
        allFriends.clear();
        newFriends.clear();
        model.getFriend(preferencesUtil.getString(CustomKey.userId),
                new BaseObserver<Friend>(null) {
                    @Override
                    protected void onSuccess(Friend friend) {
                        allFriends.add(friend);
                        if (friend.getState() == 0) {
                            friends.add(friend);
                            getItem(friend);
                        } else {
                            newFriends.add(friend);
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        data.isRefresh.set(false);
                    }
                });
    }

    private void getItem(Friend friend) {
        FriendItem item = new FriendItem(activity, "");
        item.portrait.set(friend.getPortrait());
        item.name.set(friend.getName());
        item.signature.set(friend.getSignature());
        item.state.set(friend.getState());
        item.isFriend.set(true);
        list.add(item);
    }

}
