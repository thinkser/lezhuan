package com.thinkser.lezhuan.fragment;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.thinkser.core.adapter.RecyclerAdapter;
import com.thinkser.core.base.BaseFragment;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.core.utils.LocationUtil;
import com.thinkser.core.view.MyRecyclerView;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.activity.ClassifyActivity;
import com.thinkser.lezhuan.activity.InfoActivity;
import com.thinkser.lezhuan.activity.PayActivity;
import com.thinkser.lezhuan.activity.SearchActivity;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.FragmentHomeBinding;
import com.thinkser.lezhuan.entity.Publish;
import com.thinkser.lezhuan.item.ADItem;
import com.thinkser.lezhuan.item.PrizeItem;
import com.thinkser.lezhuan.model.MainModel;
import com.xys.zxing.zxing.activity.CaptureActivity;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * 首页
 */

public class HomeFragment extends BaseFragment<AppData, FragmentHomeBinding>
        implements AMapLocationListener, SwipeRefreshLayout.OnRefreshListener,
        MyRecyclerView.OnRecyclerScrollListener {

    private MainModel model;
    private LocationUtil locationUtil;
    private RecyclerAdapter adapter;

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
        model = new MainModel(activity);
        //初始化定位
        locationUtil = new LocationUtil();
        locationUtil.getLocation(getActivity(), this);
        //设置列表适配器
        data.adapter.set(new RecyclerAdapter(R.layout.item_ad));
        adapter = data.adapter.get();
        //获取列表数据
        getList();
    }

    //获取好友转发列表
    private void getList() {
        data.isRefresh.set(true);
        ArrayList<ADItem> adItems = new ArrayList<>();
        model.getTransmit(preferencesUtil.getString(CustomKey.userId),
                new BaseObserver<Publish>(null) {
                    @Override
                    protected void onSuccess(Publish publish) {
                        showListItem(publish, adItems);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        adapter.refresh(adItems);
                        data.isRefresh.set(false);
                    }
                });
    }

    //显示发布列表项
    private void showListItem(Publish publish, ArrayList<ADItem> list) {
        //显示奖品列表
        ObservableList<PrizeItem> prizes = new ObservableArrayList<>();
        for (int i = 0; i < 10; i++) {
            PrizeItem item = new PrizeItem();
            item.hasPrize.set(i < publish.getPrizeCount());
            prizes.add(item);
        }
        //显示列表项数据
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            String result = data.getExtras().getString("result");
            Intent intent = new Intent(activity, PayActivity.class);
            intent.putExtra(CustomKey.info, result);
            activity.startActivity(intent);
        }
    }

    //跳转到搜索界面
    public void toSearch() {
        skip(SearchActivity.class);
    }

    //跳转到扫码界面
    public void toScan() {
        startActivityForResult(new Intent(activity, CaptureActivity.class), 0);
    }

    public void toClasses(String s) {
        Intent intent = new Intent(activity, ClassifyActivity.class);
        intent.putExtra(CustomKey.classes, s);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        getList();
    }

    @Override
    public void loadMore(MyRecyclerView view) {

    }
}
