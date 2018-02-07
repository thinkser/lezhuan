package com.thinkser.lezhuan.api;

import com.thinkser.lezhuan.entity.Customer;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 登录、注册、忘记密码网络接口。
 */

public interface BeginAPI {

    @GET("classes/{table}")
    Observable<Map<String, List<Customer>>> login(
            @Path("table") String table,
            @Query("where") String where
    );

    @POST("classes/{table}")
    Observable<Customer> register(
            @Path("table") String table,
            @Body RequestBody body
    );

    @PUT("classes/{table}/{id}")
    Observable<Map<String, String>> forget(
            @Path("table") String table,
            @Path("id") String id,
            @Body RequestBody body
    );

}
