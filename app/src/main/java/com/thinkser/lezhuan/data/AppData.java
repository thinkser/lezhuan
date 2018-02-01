package com.thinkser.lezhuan.data;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.thinkser.core.adapter.FragmentPagerAdapter;
import com.thinkser.core.adapter.RecyclerAdapter;

/**
 * 程序界面数据
 */

public class AppData {

    //通用
    public final ObservableField<RecyclerAdapter> adapter = new ObservableField<>();
    public final ObservableBoolean isLoading = new ObservableBoolean(false);
    public final ObservableField<String> district = new ObservableField<>("定位中");

    //登录和注册
    public final ObservableBoolean isLogin = new ObservableBoolean(true);
    public final ObservableField<String>
            phone = new ObservableField<>(""),
            password = new ObservableField<>(""),
            check = new ObservableField<>(""),
            username = new ObservableField<>(""),
            code = new ObservableField<>(""),
            getCode = new ObservableField<>("获取验证码");

    //main
    public final ObservableField<FragmentPagerAdapter> fragmentPagerAdapter = new ObservableField<>();
    public final ObservableInt position = new ObservableInt();

    //person
    public final ObservableField<String>
            portrait = new ObservableField<>(""),
            signature = new ObservableField<>("");

    //store
    public final ObservableField<String>
            storeName = new ObservableField<>(""),
            storeLocation = new ObservableField<>("正在获取位置信息"),
            storeAddress = new ObservableField<>(""),
            storePhone = new ObservableField<>("");

}
