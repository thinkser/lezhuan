package com.thinkser.lezhuan.api;

import com.thinkser.lezhuan.entity.Store;

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
 * 我的店铺API
 */

public interface StoreAPI {

    @POST("classes/{table}")
    Observable<Store> createStore(
            @Path("table") String table,
            @Body RequestBody body);

    @PUT("classes/{table}/{id}")
    Observable<Map<String, String>> changeStore(
            @Path("table") String table,
            @Path("id") String id,
            @Body RequestBody body
    );

    @GET("classes/{table}")
    Observable<Map<String, List<Store>>> getStoreList(
            @Path("table") String table,
            @Query("where") String where);

    @GET("classes/{table}/{id}")
    Observable<Store> getStoreInfo(
            @Path("table") String table,
            @Path("id") String id
    );
}
