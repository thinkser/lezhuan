package com.thinkser.core.base;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.thinkser.core.utils.MarkedUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<T> {

    private static final String TAG = "BaseObserver";
    private Context context;
    private Dialog dialog;

    protected BaseObserver(Context context, Dialog dialog) {
        this.context = context.getApplicationContext();
        this.dialog = dialog;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        cancelDialog();
        Log.e(TAG, e.getMessage());
        onFailed();
    }

    @Override
    public void onComplete() {
        cancelDialog();
        Log.e(TAG, "onComplete");
    }

    protected abstract void onSuccess(T t);

    protected void onFailed() {
    }

    //取消加载中对话框
    private void cancelDialog() {
        if (dialog != null) {
            dialog.setCancelable(false);
            dialog.cancel();
        }
    }

}