package com.thinkser.core.adapter;

import android.databinding.BindingAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thinkser.core.view.MyRecyclerView;

import java.io.File;

/**
 * 自定义DataBinding
 */

public class DataBindingAdapter {

    //显示drawable里的图片
    @BindingAdapter("src")
    public static void setSrc(ImageView imageView, int src) {
        if (src != 0) {
            Glide.with(imageView.getContext()).load(src).into(imageView);
        }
    }

    //显示file类型图片
    @BindingAdapter({"file", "placeholder"})
    public static void setFile(ImageView imageView, File file, int placeholder) {
        Glide.with(imageView)
                .asBitmap()
                .load(file)
                .apply(getOptions()
                        .placeholder(placeholder)
                        .error(placeholder))
                .into(imageView);
    }

    //显示网络图片
    @BindingAdapter({"url", "placeholder"})
    public static void setUrl(ImageView imageView, String url, int placeholder) {
        Glide.with(imageView)
                .asBitmap()
                .load(url)
                .apply(getOptions()
                        .placeholder(placeholder)
                        .error(placeholder))
                .into(imageView);
    }

    private static RequestOptions getOptions() {
        return new RequestOptions()
                .centerCrop()
                .dontAnimate();
    }

    //viewpager显示fragment
    @BindingAdapter({"adapter", "position"})
    public static void setPosition(ViewPager viewPager, FragmentPagerAdapter adapter, int position) {
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position, false);
    }

    //下拉刷新
    @BindingAdapter({"refreshColor", "refreshListener"})
    public static void setRefresh(SwipeRefreshLayout refresh, int refreshColor,
                                  SwipeRefreshLayout.OnRefreshListener refreshListener) {
        refresh.setColorSchemeColors(refreshColor);
        refresh.setOnRefreshListener(refreshListener);
    }

    @BindingAdapter("isRefresh")
    public static void setRefreshing(SwipeRefreshLayout refresh, boolean isRefresh) {
        refresh.setRefreshing(isRefresh);
    }

    //上拉加载列表
    @BindingAdapter({"adapter", "decoration", "listener"})
    public static void setRecycler(MyRecyclerView recycler,
                                   RecyclerAdapter adapter,
                                   RecyclerView.ItemDecoration decoration,
                                   MyRecyclerView.OnRecyclerScrollListener listener) {
        recycler.setListener(listener);
        recycler.setAdapter(adapter);
        if (decoration != null)
            recycler.addDecoration(decoration);
    }

    @BindingAdapter("isLoading")
    public static void setLoading(MyRecyclerView recycler, boolean isLoading) {
        recycler.setLoading(isLoading);
    }

}
