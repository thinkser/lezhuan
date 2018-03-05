package com.thinkser.lezhuan.api;

import com.thinkser.lezhuan.entity.Customer;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * 支付接口
 */

public interface PayAPI {

    @GET("classes/{table}/{id}")
    Observable<Customer> getMoney(
            @Path("table") String table,
            @Path("id") String id
    );

    @PUT("classes/{table}/{id}")
    Observable<Map<String, String>> resetMoney(
            @Path("table") String table,
            @Path("id") String id,
            @Body RequestBody body
    );
}
