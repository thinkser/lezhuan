package com.thinkser.lezhuan.api;

import com.thinkser.lezhuan.entity.Customer;
import com.thinkser.lezhuan.entity.Friend;

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
 * 好友相关接口
 */

public interface FriendAPI {

    @GET("classes/{table}")
    Observable<Map<String, List<Customer>>> search(
            @Path("table") String table,
            @Query("where") String where
    );

    @GET("classes/{table}")
    Observable<Map<String, List<Friend>>> getFriend(
            @Path("table") String table,
            @Query("where") String where
    );

    @POST("classes/{table}")
    Observable<Friend> addFriend(
            @Path("table") String table,
            @Body RequestBody body
    );

    @PUT("classes/{table}/{id}")
    Observable<Map<String, String>> agree(
            @Path("table") String table,
            @Path("id") String id,
            @Body RequestBody body
    );
}
