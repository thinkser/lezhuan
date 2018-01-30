package com.thinkser.lezhuan.item;

import android.app.Activity;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;

/**
 * 广告列表项
 */

public class ADItem {

    private Activity activity;
    public final ObservableList<PrizeItem> prize = new ObservableArrayList<>();
    public final ObservableField<String>
            title = new ObservableField<>(""),
            distance = new ObservableField<>(""),
            url = new ObservableField<>(""),
            label = new ObservableField<>("");

    public ADItem(Activity activity) {
        this.activity = activity;
    }

    public void click() {
//        activity.startActivity(new Intent(activity, ));
    }
}
