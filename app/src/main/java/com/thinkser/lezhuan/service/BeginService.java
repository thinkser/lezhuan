package com.thinkser.lezhuan.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.thinkser.lezhuan.data.Marked;

import cn.bmob.sms.BmobSMS;

import static com.thinkser.core.data.StaticData.APP_KEY;

/**
 * 应用启动时初始化各种服务
 */

public class BeginService extends IntentService {

    private static final String ACTION_UPLOAD_IMG = "com.thinkser.lezhuan.service.action.INIT_SERVICE";

    public static void startBeginService(Context context) {
        Intent intent = new Intent(context, BeginService.class);
        intent.setAction(ACTION_UPLOAD_IMG);
        context.startService(intent);
    }

    public BeginService() {
        super("BeginService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_UPLOAD_IMG.equals(action)) {
                InitService();
            }
        }
    }

    //初始化各种服务
    private void InitService() {
        //初始化Bmob短信服务
        BmobSMS.initialize(this, APP_KEY);
        //初始化提示语
        Marked.initMarked();
    }
}
