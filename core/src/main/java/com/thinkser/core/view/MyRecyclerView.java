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

import com.thinkser.core.R;

/**
 * 自定义下拉加载列表
 */

public class MyRecyclerView extends RecyclerView {

    private boolean isLoading;//是否正在加载
    private int spanCount;
    private boolean scrollEnable;

    public interface OnRecyclerScrollListener {
        void loadMore(MyRecyclerView view);
    }

    public MyRecyclerView(Context context) {
        this(context, null);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //获取数据
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.MyRecyclerView, defStyle, 0);
        spanCount = typedArray.getInt(R.styleable.MyRecyclerView_spanCount, 1);
        scrollEnable = typedArray.getBoolean(R.styleable.MyRecyclerView_scrollEnable, true);
        typedArray.recycle();
        RecyclerView.LayoutManager manager;
        if (spanCount > 1) {
            manager = new GridLayoutManager(getContext(), spanCount) {
                @Override
                public boolean canScrollVertically() {
                    return scrollEnable && super.canScrollVertically();
                }
            };
        } else {
            manager = new LinearLayoutManager(getContext()) {
                @Override
                public boolean canScrollVertically() {
                    return scrollEnable && super.canScrollVertically();
                }
            };
        }
        //设置布局管理器
        setLayoutManager(manager);
        //设置Item增加、移除动画
        setItemAnimator(new DefaultItemAnimator());
    }

    //线性布局时下拉加载方法
    private void linearLoadMore(OnRecyclerScrollListener listener, int dy) {
        if (listener == null) return;
        int lastVisibleItem = ((LinearLayoutManager) getLayoutManager())
                .findLastVisibleItemPosition();
        int totalItemCount = getLayoutManager().getItemCount();
        //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载
        // dy>0 表示向下滑动
        if (lastVisibleItem >= totalItemCount - 4 && dy > 0) {
            if (!isLoading()) {
                setLoading(true);
                listener.loadMore(this);
            }
        }
    }

    //表格布局时下拉加载方法
    private void gridLoadMore(OnRecyclerScrollListener listener, int dy) {
        if (listener == null) return;
        GridLayoutManager manager = (GridLayoutManager) getLayoutManager();
        int spanCount = manager.getSpanCount();
        int visibleItem = manager.findLastVisibleItemPosition();
        int totalItemCount = manager.getItemCount();
        //visibleItem >= totalItemCount - 4 * spanCount 表示剩下4行自动加载
        // dy>0 表示向下滑动
        if (visibleItem >= totalItemCount - 4 * spanCount && dy > 0) {
            if (!isLoading()) {
                setLoading(true);
                listener.loadMore(this);
            }
        }
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    //设置滑动监听
    public void setListener(OnRecyclerScrollListener listener) {
        if (listener == null)
            return;
        setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (spanCount > 1) {
                    gridLoadMore(listener, dy);
                } else {
                    linearLoadMore(listener, dy);
                }
            }
        });
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
}
