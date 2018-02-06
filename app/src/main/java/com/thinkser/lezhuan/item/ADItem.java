package com.thinkser.lezhuan.item;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.thinkser.core.adapter.RecyclerAdapter;
import com.thinkser.lezhuan.R;

import java.util.List;

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

    public ADItem(List<PrizeItem> data){
        adapter.set(new RecyclerAdapter(R.layout.item_prize));
        adapter.get().addNew(data);
    }

    public void click() {
        listener.click();
    }

    public interface OnADItemClickListener{
        void click();
    }

    public void setADItemClickListener(OnADItemClickListener listener){
        this.listener = listener;
    }
}
