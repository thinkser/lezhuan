package com.thinkser.core.utils;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * bmob工具类
 */

public class BmobUtil<E extends BmobObject> {

    private BmobQuery<E> query;

    public BmobUtil<E> query(String key, String value) {
        query = new BmobQuery<>();
        query.addWhereEqualTo(key, value);
        return this;
    }

    //设置返回数据，如果不加上这条语句，默认返回10条数据
    public BmobUtil<E> setLimit(int num) {
        query.setLimit(num);
        return this;
    }

    //执行查询方法
    public void findObjects(FindListener<E> listener) {
        query.findObjects(listener);
    }

}
