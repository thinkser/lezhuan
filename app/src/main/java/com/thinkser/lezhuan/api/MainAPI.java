package com.thinkser.lezhuan.api;

import com.thinkser.lezhuan.entity.Customer;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MainAPI {

    @GET("classes/{table}/{userId}")
    Observable<Customer> getPeron(
            @Path("table") String table,
            @Path("userId") String id
    );
}
