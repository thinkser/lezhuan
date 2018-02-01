package com.thinkser.core.adapter;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thinkser.core.BR;
import com.thinkser.core.view.MyRecyclerView;
import com.thinkser.core.view.NoScrollViewPager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义DataBinding
 */

public class DataBindingAdapter {

    //显示drawable里的图片
    @BindingAdapter("src")
    public static void setSrc(ImageView imageView, int src) {
        if (src != 0) {
            Bitmap bitmap = BitmapFactory.decodeResource(imageView.getResources(), src);
            imageView.setImageBitmap(bitmap);
        }
    }

    //显示SD卡里的图片
    @BindingAdapter("src")
    public static void setSrc(ImageView imageView, String src) {
        if (src == null || src.equals(""))
            return;

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(src);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
        imageView.setImageBitmap(bitmap);
    }

    //显示网络图片
    @BindingAdapter({"url", "placeholder"})
    public static void setUrl(ImageView imageView, String url, int placeholder) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholder)
                .error(placeholder)
                .dontAnimate();
        Glide.with(imageView)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    //viewpager显示fragment
    @BindingAdapter({"adapter", "position"})
    public static void setPosition(ViewPager viewPager, FragmentPagerAdapter adapter, int position) {
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position,false);
    }

    //下拉加载列表
    @BindingAdapter({"adapter", "isLoading", "decoration", "listener"})
    public static void setRecycler(MyRecyclerView recycler,
                                   RecyclerAdapter adapter,
                                   boolean isLoading,
                                   RecyclerView.ItemDecoration decoration,
                                   MyRecyclerView.OnRecyclerScrollListener listener) {
        recycler.setLoading(isLoading);
        recycler.setListener(listener);
        recycler.setAdapter(adapter);
        if (decoration != null)
            recycler.addDecoration(decoration);
    }

    @BindingAdapter({"spanCount", "data", "size", "layout"})
    public static void setList(LinearLayout viewGroup, int spanCount, List data, int size, int layout) {
        viewGroup.setOrientation(LinearLayout.VERTICAL);
        List<LinearLayout> layouts = new ArrayList<>();
        LinearLayout item = null;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        if (size == 0) {
            size = data.size();
        }
        for (int i = 0; i < size; i++) {
            if (i % spanCount == 0) {
                item = new LinearLayout(viewGroup.getContext());
                layouts.add(item);
            }
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                    layout, null, false);
            if (data != null)
                binding.setVariable(BR.presenter, data.get(i));
            item.addView(binding.getRoot(), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        for (LinearLayout linearLayout : layouts) {
            viewGroup.addView(linearLayout, params);
        }
    }
}
