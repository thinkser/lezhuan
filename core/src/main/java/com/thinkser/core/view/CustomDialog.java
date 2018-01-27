package com.thinkser.core.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.thinkser.core.R;

/**
 * 自定义dialog
 */

public class CustomDialog extends Dialog implements View.OnClickListener {

    private Window window;
    private int layout;
    private int[] listenedItems;
    private OnDialogClickListener listener;

    public interface OnDialogClickListener {
        void OnDialogClick(CustomDialog dialog, View view);
    }

    public CustomDialog(Context context, int layout, int[] listenedItems) {
        super(context, R.style.dialog_custom);
        this.layout = layout;
        this.listenedItems = listenedItems;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        window = getWindow();
        window.setGravity(Gravity.CENTER);
        setContentView(layout);

        if (listenedItems == null)
            return;
        //遍历控件id,添加点击事件
        for (int id : listenedItems) {
            findViewById(id).setOnClickListener(this);
        }
    }

    public void setCancelable(boolean cancelable) {
        setCanceledOnTouchOutside(cancelable);
    }

    public void setAnimEnable(boolean animEnable) {
        if (animEnable)
            window.setWindowAnimations(R.style.bottom_menu_animation);
    }

    public void setOnDialogClickListener(OnDialogClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        dismiss();
        listener.OnDialogClick(this, view);
    }
}
