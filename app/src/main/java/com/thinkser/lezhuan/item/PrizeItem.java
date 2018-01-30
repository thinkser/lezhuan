package com.thinkser.lezhuan.item;

import android.databinding.ObservableBoolean;
import android.util.Log;

/**
 * 奖品列表项
 */

public class PrizeItem {

    public final ObservableBoolean hasTicket = new ObservableBoolean();

    public PrizeItem(boolean b) {
        Log.e("prize", String.valueOf(b));
        hasTicket.set(b);
    }

}
