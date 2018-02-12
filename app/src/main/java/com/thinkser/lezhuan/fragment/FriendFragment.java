package com.thinkser.lezhuan.fragment;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;

import com.thinkser.core.adapter.RecyclerAdapter;
import com.thinkser.core.base.BaseFragment;
import com.thinkser.core.view.MyRecyclerView;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.activity.AddFriendActivity;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.FragmentFriendBinding;
import com.thinkser.lezhuan.item.FriendItem;

/**
 * 好友
 */

public class FriendFragment extends BaseFragment<AppData, FragmentFriendBinding>
        implements SwipeRefreshLayout.OnRefreshListener, MyRecyclerView.OnRecyclerScrollListener {

    private ObservableList<FriendItem> list;

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
        data.adapter.set(new RecyclerAdapter(R.layout.item_friend, list));
        data.isRefresh.set(true);
        getList();
    }

    public void toAdd() {
        skip(AddFriendActivity.class);
    }

    @Override
    public void onRefresh() {
        data.isRefresh.set(true);
        getList();
    }

    //获取好友列表
    private void getList() {
        FriendItem item = new FriendItem();
        item.name.set("我的好友");
        item.signature.set("个性签名");
        list.add(item);
        data.isRefresh.set(false);
    }

    @Override
    public void loadMore(MyRecyclerView view) {

    }
}
