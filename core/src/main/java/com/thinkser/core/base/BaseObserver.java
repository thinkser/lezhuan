package com.thinkser.core.base;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.thinkser.core.utils.MarkedUtil;

import io.reactivex.Observer;

public abstract class BaseObserver<T extends BaseEntity> implements Observer<T> {

    private static final String TAG = "BaseObserver";
    private Context context;
    private Dialog dialog;

    protected BaseObserver(Context context, Dialog dialog) {
        this.context = context.getApplicationContext();
        this.dialog = dialog;
    }

    @Override
    public void onNext(T t) {
        int code = t.getCode();
        if (code == 0) {
            onSuccess(t);
        } else {
            toast(code);
            cancelDialog();
        }
    }

    @Override
    public void onError(Throwable e) {
        toast(e.getMessage());
        cancelDialog();
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
        cancelDialog();
    }

    protected void toast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    protected void toast(int code) {
        Toast.makeText(context, MarkedUtil.getMessage(code), Toast.LENGTH_SHORT).show();
    }

    //取消加载中对话框
    public void cancelDialog() {
        if (dialog != null) {
            dialog.setCancelable(false);
            dialog.cancel();
        }
    }

    protected abstract void onSuccess(T t);
}