package com.thinkser.lezhuan.api;

import com.thinkser.lezhuan.entity.Customer;
import com.thinkser.lezhuan.entity.Publish;
import com.thinkser.lezhuan.entity.Store;
import com.thinkser.lezhuan.entity.Transmit;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MainAPI {

    @GET("classes/{table}")
    Observable<Map<String, List<Transmit>>> getTransmit(
            @Path("table") String table,
            @Query("where") String where
    );

    @GET("classes/{table}/{id}")
    Observable<Publish> getTransmitInfo(
            @Path("table") String table,
            @Path("id") String id
    );

    @GET("classes/{table}")
    Observable<Map<String, List<Publish>>> search(
            @Path("table") String table,
            @Query("where") String where
    );


    @POST("classes/{table}")
    Observable<Transmit> transmit(
            @Path("table") String table,
            @Body RequestBody body
    );
}
