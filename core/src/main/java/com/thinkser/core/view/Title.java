package com.thinkser.core.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkser.core.R;

/**
 * 自定义标题栏
 */

public class Title extends LinearLayout {

    private Integer layoutColor, backColor, lineColor, titleColor;
    private String title;

    private LinearLayout layout;
    private ImageView back;
    private View line;
    private TextView titleView;

    public Title(Context context) {
        this(context, null);
    }

    public Title(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Title(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取数据
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.MyRecyclerView, defStyleAttr, 0);
        layoutColor = typedArray.getColor(R.styleable.Title_layoutColor, 0);
        backColor = typedArray.getColor(R.styleable.Title_backColor, 0);
        lineColor = typedArray.getColor(R.styleable.Title_lineColor, 0);
        titleColor = typedArray.getColor(R.styleable.Title_titleColor, 0);
        title = typedArray.getString(R.styleable.Title_title);
        findView();
    }

    private void findView() {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        layout = (LinearLayout) inflater.inflate(R.layout.layout_title, null);
        titleView = layout.findViewById(R.id.title);
        line = layout.findViewById(R.id.line);
        back = layout.findViewById(R.id.back);
        initView();
    }

    private void initView() {
        layout.setBackgroundColor(layoutColor);
        if (backColor == 0) {
            back.setVisibility(GONE);
            line.setVisibility(GONE);
        } else {
            back.setColorFilter(backColor);
            line.setBackgroundColor(lineColor);
        }
        titleView.setTextColor(titleColor);
        titleView.setText(title);
        this.addView(layout);
    }

    public void setOnClick(OnClickListener listener) {
        back.setOnClickListener(listener);
    }
}
