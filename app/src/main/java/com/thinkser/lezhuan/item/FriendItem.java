package com.thinkser.lezhuan.item;

import android.app.Activity;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.widget.Toast;

import com.thinkser.core.base.BaseObserver;
import com.thinkser.core.utils.PreferencesUtil;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.entity.Friend;
import com.thinkser.lezhuan.model.FriendModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 好友列表项
 */

public class FriendItem {

    public final ObservableField<String>
            userId = new ObservableField<>(""),
            friendId = new ObservableField<>(""),
            portrait = new ObservableField<>(""),
            name = new ObservableField<>(""),
            signature = new ObservableField<>("");
    public final ObservableBoolean isFriend = new ObservableBoolean();
    public final ObservableInt state = new ObservableInt();

    private Activity activity;
    private String id;
    private FriendModel model;
    private PreferencesUtil util;

    public FriendItem(Activity activity, String id) {
        this.activity = activity;
        this.id = id;
        util = new PreferencesUtil(activity);
        model = new FriendModel(activity);
        userId.set(util.getString(CustomKey.userId));
    }

    public void add() {
        if (state.get() == 1) {//同意请求
            HashMap<String, Integer> map = new HashMap<>();
            map.put("state", 0);
            model.agree(id, map, new BaseObserver<Map<String, String>>(null) {
                @Override
                protected void onSuccess(Map<String, String> map) {
                    isFriend.set(true);
                }
            });
        } else {//发送请求
            Friend friend = new Friend();
            friend.setUserId(util.getString(CustomKey.userId));
            friend.setFriendId(friendId.get());
            friend.setPortrait(portrait.get());
            friend.setName(name.get());
            friend.setSignature(signature.get());
            friend.setState(1);
            model.addFriend(friend, new BaseObserver<Friend>(null) {
                @Override
                protected void onSuccess(Friend friend) {
                    Toast.makeText(activity, "已发送好友邀请", Toast.LENGTH_SHORT).show();
                    isFriend.set(true);
                }
            });
        }
    }
}
