package com.thinkser.lezhuan.activity;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;

import com.thinkser.core.adapter.RecyclerAdapter;
import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.ActivityAddBinding;
import com.thinkser.lezhuan.entity.Customer;
import com.thinkser.lezhuan.item.FriendItem;
import com.thinkser.lezhuan.model.FriendModel;

import java.util.ArrayList;

/**
 * 添加好友界面
 */

public class AddFriendActivity extends BaseActivity<AppData, ActivityAddBinding>
        implements SwipeRefreshLayout.OnRefreshListener {

    private FriendModel model;
    private ArrayList<String> friends;
    private ObservableList<FriendItem> items;

    @Override
    protected int getLayout() {
        return R.layout.activity_add;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData(Intent intent) {
        super.initData(intent);
        model = new FriendModel(this);
        friends = intent.getStringArrayListExtra(CustomKey.info);
        items = new ObservableArrayList<>();
        data.adapter.set(new RecyclerAdapter(R.layout.item_friend, items));
    }

    public void search() {
        data.isRefresh.set(true);
        items.clear();
        model.search(data.keyWord.get(),
                new BaseObserver<Customer>(null) {
                    @Override
                    protected void onSuccess(Customer customer) {
                        boolean isFriend = false;
                        if (friends == null) {
                            getItem(customer, false);
                            return;
                        }
                        for (String fiendId : friends) {
                            if (customer.getObjectId().equals(fiendId)) {
                                isFriend = true;
                                break;
                            }
                        }
                        getItem(customer, isFriend);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        data.isRefresh.set(false);
                    }
                });
    }

    //获取好友列表项
    private void getItem(Customer customer, boolean isFriend) {
        FriendItem item = new FriendItem(this, "");
        item.friendId.set(customer.getObjectId());
        item.name.set(customer.getUsername());
        item.portrait.set(customer.getPortrait());
        item.signature.set(customer.getSignature());
        item.isFriend.set(isFriend);
        item.state.set(-1);
        items.add(item);
    }

    @Override
    public void onRefresh() {
        search();
    }
}
