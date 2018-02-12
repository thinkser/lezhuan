package com.thinkser.lezhuan.fragment;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.thinkser.core.adapter.RecyclerAdapter;
import com.thinkser.core.base.BaseFragment;
import com.thinkser.core.utils.LocationUtil;
import com.thinkser.core.view.MyRecyclerView;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.activity.ClassifyActivity;
import com.thinkser.lezhuan.activity.InfoActivity;
import com.thinkser.lezhuan.activity.SearchActivity;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.FragmentHomeBinding;
import com.thinkser.lezhuan.entity.Publish;
import com.thinkser.lezhuan.item.ADItem;
import com.thinkser.lezhuan.item.PrizeItem;

/**
 * 首页
 */

public class HomeFragment extends BaseFragment<AppData, FragmentHomeBinding>
        implements AMapLocationListener, SwipeRefreshLayout.OnRefreshListener,
        MyRecyclerView.OnRecyclerScrollListener {

    private LocationUtil locationUtil;
    private ObservableList<ADItem> list;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData() {
        super.initData();
        locationUtil = new LocationUtil();
        locationUtil.getLocation(getActivity(), this);
        list = new ObservableArrayList<>();
        data.adapter.set(new RecyclerAdapter(R.layout.item_ad, list));
        data.isRefresh.set(true);
        getList();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                data.district.set(aMapLocation.getDistrict());
                locationUtil.stopLocation();
                //获取纬度
//                aMapLocation.getLatitude();
                //获取经度
//                aMapLocation.getLongitude();
//                aMapLocation.getProvince();
//                aMapLocation.getCity();
//                aMapLocation.getDistrict();
            }
        }
    }

    public void toSearch() {
        skip(SearchActivity.class);
    }

    public void toScan() {

    }

    public void toClasses(String s) {
        Intent intent = new Intent(activity, ClassifyActivity.class);
        intent.putExtra(CustomKey.classes, s);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        data.isRefresh.set(true);
        getList();
    }

    //获取好友转发列表
    private void getList() {
        data.isRefresh.set(false);
    }

    //显示发布列表
    private void showList(Publish publish) {
        ObservableList<PrizeItem> prizes = new ObservableArrayList<>();
        //显示奖品列表
        for (int i = 0; i < 10; i++) {
            PrizeItem item = new PrizeItem();
            item.hasPrize.set(i < publish.getPrizeCount());
            prizes.add(item);
        }
        ADItem adItem = new ADItem(prizes);
        adItem.url.set(publish.getPhotos().get(0));
        adItem.title.set(publish.getTitle());
        adItem.integral.set("今日积分：" + publish.getIntegral());
        //设置列表项点击事件
        Intent intent = new Intent(activity, InfoActivity.class);
        intent.putExtra(CustomKey.info, publish);
        adItem.setADItemClickListener(() -> startActivity(intent));
        list.add(adItem);
    }

    @Override
    public void loadMore(MyRecyclerView view) {

    }
}
