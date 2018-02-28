package com.thinkser.lezhuan.api;

import com.thinkser.lezhuan.entity.FileEntity;
import com.thinkser.lezhuan.entity.Publish;
import com.thinkser.lezhuan.entity.Store;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * 广告相关API
 */

public interface PublishAPI {

    @GET("classes/{table}")
    Observable<Map<String, List<Publish>>> getPublishList(
            @Path("table") String table,
            @Query("where") String where);

    @POST("classes/{table}")
    Observable<Publish> createPublish(
            @Path("table") String table,
            @Body RequestBody body);

    @PUT("classes/{table}/{id}")
    Observable<Map<String, String>> changePublish(
            @Path("table") String table,
            @Path("id") String id,
            @Body RequestBody body
    );

    @Multipart
    @POST("http://up.imgapi.com/")
    Observable<FileEntity> uploadFiles(
            @Part MultipartBody.Part token,
            @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("http://www.tietuku.com/?m=Home&c=Index&a=delpic")
    Observable<Map<String, String>> deleteFiles(
            @Field("pid") Integer pid
    );

    @GET("http://www.tietuku.com/{findUrl}.html")
    Observable<ResponseBody> getPid(
            @Path("findUrl") String findUrl
    );

    @GET
    Observable<ResponseBody> getFiles(
            @Url String url
    );

    @GET("classes/{table}/{id}")
    Observable<Store> getStore(
            @Path("table") String table,
            @Path("id") String id
    );
}
