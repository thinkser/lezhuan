package com.thinkser.lezhuan.model;

import android.app.Activity;
import android.util.Log;

import com.thinkser.core.base.BaseModel;
import com.thinkser.lezhuan.api.MainAPI;

import io.reactivex.Observer;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 首页、好友、个人中心的model。
 */

public class MainModel extends BaseModel {

    public MainModel(Activity activity) {
        super(activity);
    }

    //获取用户信息
    public void getPerson(Observer<String> observer) {
        netUtil.getInstance(headers, MainAPI.class)
                .getPerson()
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.e("tag", response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }
}
