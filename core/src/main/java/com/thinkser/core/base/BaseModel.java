package com.thinkser.core.base;

import android.app.Activity;

import com.thinkser.core.utils.NetUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thinkser.core.data.StaticData.APP_KEY;
import static com.thinkser.core.data.StaticData.REST_API;

/**
 * 所有model继承此类
 */

public abstract class BaseModel {

    protected List<Map<String, String>> headers;
    protected NetUtil netUtil;

    public BaseModel(Activity activity) {
        netUtil = new NetUtil(activity);
        Map<String, String> applicationID = new HashMap<>();
        applicationID.put("X-Bmob-Application-Id", APP_KEY);
        headers.add(applicationID);
        Map<String, String> restApiKey = new HashMap<>();
        restApiKey.put("X-Bmob-REST-API-Key", REST_API);
        headers.add(restApiKey);
        Map<String, String> contentType = new HashMap<>();
        contentType.put("Content-Type", "application/json");
        headers.add(contentType);
    }

    public void addHeader(String key, String value) {
        Map<String, String> header = new HashMap<>();
        header.put(key, value);
        headers.add(header);
    }
}
