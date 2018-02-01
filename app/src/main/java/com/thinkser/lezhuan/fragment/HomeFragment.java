package com.thinkser.lezhuan.fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.thinkser.core.base.BaseFragment;
import com.thinkser.core.utils.LocationUtil;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.FragmentHomeBinding;

/**
 * 首页
 */

public class HomeFragment extends BaseFragment<AppData, FragmentHomeBinding> implements AMapLocationListener {

    private LocationUtil locationUtil;

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

    }

    public void toScan() {

    }

    public void toClasses(String s) {

    }
}
