package com.thinkser.lezhuan.dialog;

import android.content.Context;
import android.widget.TextView;

import com.thinkser.core.view.CustomDialog;
import com.thinkser.lezhuan.R;

/**
 * 加载中dialog
 */

public class ProgressDialog {

    private Context context;
    public CustomDialog dialog;

    public ProgressDialog(Context context) {
        this.context = context;
    }

    //显示加载中对话框
    public void showProgressDialog(String text, boolean cancelable) {
        if (dialog == null)
            dialog = new CustomDialog(context, R.layout.dialog_progress, null);
        dialog.show();
        ((TextView) dialog.findViewById(R.id.tv_progress)).setText(text);
        dialog.setCancelable(cancelable);
    }

    //取消加载中对话框
    public void cancelProgressDialog() {
        if (dialog != null) {
            dialog.setCancelable(false);
            dialog.cancel();
        }
    }
}
