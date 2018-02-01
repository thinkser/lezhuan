package com.thinkser.lezhuan.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.thinkser.core.view.CustomDialog;
import com.thinkser.lezhuan.R;

/**
 * 提示dialog
 */

public class HintDialog {

    private Context context;
    public CustomDialog dialog;

    public HintDialog(Context context,String hint) {
        this.context = context;
        if (dialog == null)
            dialog = new CustomDialog(context, R.layout.dialog_hint,
                    new int[]{R.id.tv_hint_cancel, R.id.tv_hint_ensure});
        dialog.show();
        ((TextView) dialog.findViewById(R.id.tv_hint)).setText(hint);
    }

    public HintDialog setRightButton(String right, int color) {
        TextView textView = dialog.findViewById(R.id.tv_hint_ensure);
        textView.setText(right);
        textView.setTextColor(context.getResources().getColor(color));
        return this;
    }

    public HintDialog setLeftButton(String left, int color) {
        TextView textView = dialog.findViewById(R.id.tv_hint_cancel);
        textView.setText(left);
        textView.setTextColor(context.getResources().getColor(color));
        return this;
    }

    public HintDialog setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
        return this;
    }

    public void setOnHintClickListener(OnClickListener listener) {
        dialog.setOnDialogClickListener(new CustomDialog.OnDialogClickListener() {
            @Override
            public void OnDialogClick(CustomDialog dialog, View view) {
                if (view.getId() == R.id.tv_hint_cancel) {
                    listener.onLeftClick(dialog);
                }
                if (view.getId() == R.id.tv_hint_ensure) {
                    listener.onRightClick(dialog);
                }
            }
        });
    }

    public interface OnClickListener {
        void onLeftClick(CustomDialog dialog);

        void onRightClick(CustomDialog dialog);
    }

}
