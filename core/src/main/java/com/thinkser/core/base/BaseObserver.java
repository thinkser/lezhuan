package com.thinkser.core.base;

import android.app.Dialog;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public abstract class BaseObserver<T> implements Observer<T> {

    private Dialog dialog;
    private static final String TAG = "BaseObserver";

    protected BaseObserver(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        cancelDialog();
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            cancelDialog();
            ResponseBody body = ((HttpException) e).response().errorBody();
            try {
                HashMap map = new Gson().fromJson(body.string(), HashMap.class);
                String msg = String.valueOf(map.get("error"));
                String code = String.valueOf(map.get("code"));
                onFailed(Double.valueOf(code));
                log(msg);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onComplete() {
        cancelDialog();
        log("onComplete");
    }

    protected abstract void onSuccess(T t);

    protected void onFailed(double code) {
        log(String.valueOf(code));
    }

    //取消加载中对话框
    private void cancelDialog() {
        if (dialog != null) {
            dialog.setCancelable(false);
            dialog.cancel();
        }
    }

    private void log(String s) {
        Log.e(TAG, s);
    }

}