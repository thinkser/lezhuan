package com.thinkser.lezhuan.data;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * 程序界面数据
 */

public class AppData {

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
    public final ObservableField<FragmentManager> fragmentManager = new ObservableField<>();
    public final ObservableList<Fragment> fragments = new ObservableArrayList<>();
    public final ObservableInt position = new ObservableInt();

    //person
    public final ObservableField<String>
            portrait = new ObservableField<>(""),
            signature = new ObservableField<>("个性签名");

}
