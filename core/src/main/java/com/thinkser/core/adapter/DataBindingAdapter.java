package com.thinkser.core.adapter;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thinkser.core.view.MyRecyclerView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    //设置矢量图和颜色
    @BindingAdapter({"src", "tint"})
    public static void setTint(ImageView imageView, int src, int color) {
        imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(src));
        imageView.setColorFilter(color);
    }

    //设置背景颜色
    @BindingAdapter("bgColor")
    public static void bgColor(View view, int color) {
        if (color != 0)
            view.setBackgroundColor(color);
    }

    //viewpager显示fragment
    @BindingAdapter({"manager", "fragments", "position"})
    public static void setPosition(ViewPager viewPager, FragmentManager manager,
                                   List<Fragment> fragments, int position) {
        viewPager.setAdapter(new FragmentPagerAdapter(manager, fragments));
        viewPager.setCurrentItem(position);
    }

    //下拉加载列表
    @BindingAdapter({"isLoading", "decoration", "listener"})
    public static void setRecycler(MyRecyclerView recycler, boolean isLoading,
                                   RecyclerView.ItemDecoration decoration,
                                   MyRecyclerView.OnRecyclerScrollListener listener) {
        recycler.setLoading(isLoading);
        recycler.setListener(listener);
        if (decoration != null)
            recycler.addDecoration(decoration);
    }

}
