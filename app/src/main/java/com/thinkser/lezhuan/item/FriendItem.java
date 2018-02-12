package com.thinkser.lezhuan.item;

import android.databinding.ObservableField;

/**
 * 好友列表项
 */

public class FriendItem {

    public final ObservableField<String>
            portrait = new ObservableField<>(""),
            name = new ObservableField<>(""),
            signature = new ObservableField<>("");

}
