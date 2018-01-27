package com.thinkser.lezhuan.dialog;

import android.content.Context;
import android.widget.TextView;

import com.thinkser.core.view.CustomDialog;
import com.thinkser.lezhuan.R;

/**
 * 提示dialog
 */

public class HintDialog {

    private Context context;
    public CustomDialog dialog;

    public HintDialog(Context context) {
        this.context = context;
    }

    //显示加载中对话框
    public void showHintDialog(String text, boolean cancelable, CustomDialog.OnDialogClickListener listener) {
        if (dialog == null)
            dialog = new CustomDialog(context, R.layout.dialog_hint,
                    new int[]{R.id.tv_hint_cancel, R.id.tv_hint_ensure});
        dialog.show();
        ((TextView) dialog.findViewById(R.id.tv_hint)).setText(text);
        dialog.setCancelable(cancelable);
        dialog.setOnDialogClickListener(listener);
    }

}
