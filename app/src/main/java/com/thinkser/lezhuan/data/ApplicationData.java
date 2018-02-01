package com.thinkser.lezhuan.data;

import com.thinkser.core.base.BaseApplication;

/**
 * 全局变量
 */

public class ApplicationData extends BaseApplication {

    @Override
    protected String getBaseUrl() {
        return "https://api.bmob.cn/1/";
    }
}
