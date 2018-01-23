package com.thinkser.lezhuan.api;

import com.thinkser.lezhuan.entity.Customer;

import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 登录、注册、忘记密码网络接口。
 */

public interface BeginAPI {

    @GET("https://api.bmob.cn/1/classes/Customer/ff55c06aa2")
    Observable<String> text();

}
