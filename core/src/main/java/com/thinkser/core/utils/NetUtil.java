package com.thinkser.core.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.thinkser.core.data.StaticData.BASE_URL;
import static com.thinkser.core.data.StaticData.TIME_OUT;

public class NetUtil {

    private Activity activity;

    public NetUtil(Activity activity) {
        this.activity = activity;
    }

    //获取API的实例
    public <T> T getInstance(List<Map<String, String>> headers, Class<T> clazz) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                // 添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                // 添加Retrofit到RxJava的转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //添加字符串转换器
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(getClient(headers))
                .build()
                .create(clazz);
    }

    // 创建一个OkHttpClient进行一些配置
    private OkHttpClient getClient(final List<Map<String, String>> headers) {
        return new OkHttpClient.Builder()
                // 添加通用的Header
                .addInterceptor(chain -> {
                    Request.Builder builder = chain.request().newBuilder();
                    for (Map<String, String> header : headers) {
                        builder.addHeader(header.get("name"), header.get("value"));
                    }
                    return chain.proceed(builder.build());
                })
                //添加拦截器方便调试接口
                .addInterceptor(new HttpLoggingInterceptor(message ->
                        Log.d("HTTPIntercept", message))
                        .setLevel(HttpLoggingInterceptor.Level.BASIC))
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    private Gson buildGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                // 此处可以添加Gson 自定义TypeAdapter
//                //.registerTypeAdapter(UserInfo.class, new UserInfoTypeAdapter())
                .create();
    }

    public <T> ObservableTransformer<T, T> compose() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (!isNetAccept()) {
                        Toast.makeText(activity, "网络连接异常", Toast.LENGTH_SHORT).show();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    //判断当前网络是否可用
    private boolean isNetAccept() {
        ConnectivityManager connectivity = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}