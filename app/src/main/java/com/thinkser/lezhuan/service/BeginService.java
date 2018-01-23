package com.thinkser.lezhuan.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.thinkser.lezhuan.data.Marked;

import cn.bmob.v3.Bmob;

import static com.thinkser.lezhuan.data.StaticData.APP_KEY;

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
        //初始化bmob
        Bmob.initialize(getApplicationContext(), APP_KEY);
        Marked.initMarked();//初始化提示语
    }
}
