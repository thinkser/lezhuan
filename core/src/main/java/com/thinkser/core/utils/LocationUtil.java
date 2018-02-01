package com.thinkser.core.utils;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * 定位功能
 */

public class LocationUtil {

    private AMapLocationClient mLocationClient;

    public void getLocation(Context context, AMapLocationListener locationListener){
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            mLocationClient = new AMapLocationClient(context.getApplicationContext());
            mLocationClient.setLocationListener(locationListener);
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
    }

    public void stopLocation(){
        mLocationClient.stopLocation();
        mLocationClient.onDestroy();
    }
}
