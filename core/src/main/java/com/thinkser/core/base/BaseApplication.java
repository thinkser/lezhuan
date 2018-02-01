package com.thinkser.core.base;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

/**
 * application基类
 */

public abstract class BaseApplication extends Application {

    public String baseUrl;
    public final String APP_KEY = "6dd8e7d83d557e31cf723d60ffe05b69",
            REST_API = "7ce873cc7c066677db8254d2733c2351";

    public Map<String, Object> preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = new HashMap<>();
        baseUrl = getBaseUrl();
    }

    protected abstract String getBaseUrl();

}
