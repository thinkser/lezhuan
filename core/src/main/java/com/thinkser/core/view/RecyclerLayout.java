package com.thinkser.core.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.thinkser.core.R;

/**
 * 不能滑动的列表
 */

public class RecyclerLayout extends RecyclerView {

    private boolean clickable;

    public RecyclerLayout(Context context) {
        this(context, null);
    }

    public RecyclerLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerLayout(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //获取数据
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.MyRecyclerView, defStyle, 0);
        int spanCount = typedArray.getInt(R.styleable.MyRecyclerView_spanCount, 1);
        clickable = typedArray.getBoolean(R.styleable.RecyclerLayout_clickEnable, true);
        typedArray.recycle();
        RecyclerView.LayoutManager manager;
        if (spanCount > 1) {
            manager = new GridLayoutManager(getContext(), spanCount);
        } else {
            manager = new LinearLayoutManager(getContext());
        }
        //设置布局管理器
        setLayoutManager(manager);
        //设置Item增加、移除动画
        setItemAnimator(new DefaultItemAnimator());
    }

    //添加分割线
    public void addDecoration(RecyclerView.ItemDecoration decoration) {
        if (decoration == null) {
            addItemDecoration(new DividerItemDecoration(
                    getContext(), DividerItemDecoration.HORIZONTAL));
        } else {
            addItemDecoration(decoration);
        }
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        Log.e("onInterceptTouchEvent", "true");
        Log.e("onInterceptTouchEvent", String.valueOf(clickable));
        return clickable;
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.e("dispatchTouchEvent", "true");
//        Log.e("dispatchTouchEvent", clickable + "");
//        return clickable;
//    }
}
