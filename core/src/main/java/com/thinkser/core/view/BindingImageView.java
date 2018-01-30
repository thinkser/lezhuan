package com.thinkser.core.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 兼容低版本tint属性
 */

@BindingMethods({@BindingMethod(type = ImageView.class, attribute = "android:tint",
        method = "setTint"),
        @BindingMethod(type = ImageView.class, attribute = "android:src",
                method = "setSrc")
})
public class BindingImageView extends AppCompatImageView {

    public BindingImageView(Context context) {
        super(context);
    }

    public BindingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BindingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTint(int color) {
        if (color != 0) {
            ColorStateList cl = new ColorStateList(new int[][]{new int[0]}, new int[]{color});
            ImageViewCompat.setImageTintList(this, cl);
        }
    }

    public void setSrc(int drawable){
        Drawable drawable1 = getResources().getDrawable(drawable);
        setImageDrawable(drawable1);
    }

}
