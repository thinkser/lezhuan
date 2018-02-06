package com.thinkser.core.utils;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * 定位功能
 */

public class LocationUtil {

    private AMapLocationClient mLocationClient;

    public void getLocation(Context context, AMapLocationListener locationListener) {
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationClient = new AMapLocationClient(context.getApplicationContext());
        mLocationClient.setLocationListener(locationListener);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    public void stopLocation() {
        mLocationClient.stopLocation();
        mLocationClient.onDestroy();
    }

    public static Double getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {
        double lon1 = (Math.PI / 180) * longitude1;
        double lon2 = (Math.PI / 180) * longitude2;
        double lat1 = (Math.PI / 180) * latitude1;
        double lat2 = (Math.PI / 180) * latitude2;

        // 地球半径
        double R = 6378;
        // 两点间距离 km，如果想要米的话，结果*1000就可以了
        return Math.acos(Math.sin(lat1) * Math.sin(lat2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;
    }

    public static String showDistance(double d) {
        if (d > 1) {
            return d * 10 / 1 / 10.0 + "km";
        } else {
            return d * 1000 / 1 + "m";
        }
    }
}
