package com.thinkser.core.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.thinkser.core.BR;
import com.thinkser.core.R;

import java.util.List;

/**
 * 自定义列表
 */
public class CustomListView extends LinearLayout {

    private int spanCount;
    private int size;
    private int item;

    public CustomListView(Context context) {
        this(context, null);
    }

    public CustomListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取数据
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.MyRecyclerView, defStyleAttr, 0);
        spanCount = typedArray.getInt(R.styleable.CustomListView_count, 1);
        size = typedArray.getInt(R.styleable.CustomListView_size, 0);
        typedArray.recycle();
        setOrientation(LinearLayout.VERTICAL);
    }

    public void setItem(int item) {
        this.item = item;
    }

    public void setData(ObservableList data) {
        data.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList>() {
            @Override
            public void onChanged(ObservableList observableList) {
                show(observableList);
            }

            @Override
            public void onItemRangeChanged(ObservableList observableList, int i, int i1) {

            }

            @Override
            public void onItemRangeInserted(ObservableList observableList, int i, int i1) {

            }

            @Override
            public void onItemRangeMoved(ObservableList observableList, int i, int i1, int i2) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList observableList, int i, int i1) {

            }
        });

    }

    private void show(ObservableList data) {
        removeAllViews();
        LinearLayout line = null;
        if (size == 0) {
            size = data.size();
        }
        for (int i = 0; i < size; i++) {
            //判断是否要另起一行
            if (i % spanCount == 0) {
                line = new LinearLayout(getContext());
                line.setWeightSum(4);
                addView(line, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                    item, null, false);
            if (data != null)
                binding.setVariable(BR.presenter, data.get(i));
            binding.setVariable(BR.position, i);
            line.addView(binding.getRoot(), new LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        }
    }
}

