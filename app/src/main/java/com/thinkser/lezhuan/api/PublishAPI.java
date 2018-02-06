package com.thinkser.lezhuan.api;

import com.thinkser.lezhuan.entity.FileEntity;
import com.thinkser.lezhuan.entity.Publish;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    void changePublish();

    @Multipart
    @POST("http://up.imgapi.com/")
    Observable<FileEntity> uploadFiles(
            @Part MultipartBody.Part token,
            @Part MultipartBody.Part file);
}
