package com.thinkser.lezhuan.data;

import android.app.Fragment;
import android.app.FragmentManager;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;

import com.thinkser.core.base.BaseData;

/**
 * 程序界面数据
 */

public class AppData extends BaseData {

    //登录和注册
    public final ObservableBoolean isLogin = new ObservableBoolean(true);
    public final ObservableField<String>
            phone = new ObservableField<>(""),
            password = new ObservableField<>(""),
            check = new ObservableField<>(""),
            username = new ObservableField<>(""),
            code = new ObservableField<>(""),
            getCode = new ObservableField<>("获取验证码");

    //main界面
    public final ObservableField<FragmentManager> fragmentManager = new ObservableField<>();
    public final ObservableList<Fragment> fragments = new ObservableArrayList<>();
    public final ObservableInt position = new ObservableInt();
}
