package com.thinkser.lezhuan.item;

import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.thinkser.core.adapter.RecyclerAdapter;
import com.thinkser.lezhuan.R;

import java.util.ArrayList;

/**
 * 广告列表项
 */

public class ADItem {

    private OnADItemClickListener listener;

    public final ObservableField<String>
            title = new ObservableField<>(""),
            distance = new ObservableField<>(""),
            url = new ObservableField<>(""),
            integral = new ObservableField<>("");
    public final ObservableField<RecyclerAdapter> adapter = new ObservableField<>();

    public ADItem(ObservableList<PrizeItem> data) {
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(R.layout.item_prize);
        adapter.set(recyclerAdapter);
        recyclerAdapter.refresh((ArrayList) data);
    }

    public void click() {
        listener.click();
    }

    public interface OnADItemClickListener {
        void click();
    }

    public void setADItemClickListener(OnADItemClickListener listener) {
        this.listener = listener;
    }
}
