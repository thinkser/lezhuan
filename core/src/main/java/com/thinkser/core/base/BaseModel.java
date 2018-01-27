package com.thinkser.core.base;

import android.app.Activity;

import com.google.gson.Gson;
import com.thinkser.core.utils.NetUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.thinkser.core.data.StaticData.APP_KEY;
import static com.thinkser.core.data.StaticData.REST_API;

/**
 * 所有model继承此类
 */

public abstract class BaseModel {

    protected NetUtil netUtil;
    protected Map<String, String> headers;

    public BaseModel(Activity activity) {
        netUtil = new NetUtil(activity);
        headers = new HashMap<>();
        initHeader();
    }

    //添加常用header
    private void initHeader() {
        headers.put("X-Bmob-Application-Id", APP_KEY);
        headers.put("X-Bmob-REST-API-Key", REST_API);
        headers.put("Content-Type", "application/json");
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public RequestBody getBody(Object o) {
        return RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(o));
    }
}
