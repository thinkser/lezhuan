package com.thinkser.lezhuan.model;

import android.app.Activity;

import com.google.gson.Gson;
import com.thinkser.core.base.BaseModel;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.lezhuan.api.PublishAPI;
import com.thinkser.lezhuan.entity.FileEntity;
import com.thinkser.lezhuan.entity.Publish;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * 我的发布model
 */

public class PublishModel extends BaseModel {

    public PublishModel(Activity activity) {
        super(activity);
    }

    public void getStoreList(String userId, BaseObserver<List<Publish>> baseObserver) {
        Map<String, String> where = new HashMap<>();
        where.put("userId", userId);
        netUtil.getInstance(headers, PublishAPI.class)
                .getPublishList(Publish.class.getSimpleName(), new Gson().toJson(where))
                .map(map -> map.get("results"))
                .filter(list -> list != null && list.size() > 0)
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    public void createPublish(Publish publish, BaseObserver<Publish> baseObserver) {
        netUtil.getInstance(headers, PublishAPI.class)
                .createPublish(Publish.class.getSimpleName(), getBody(publish))
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    public void uploadFile(String userId, File file, BaseObserver<FileEntity> baseObserver) {
        String token = "c70bc993f42d87c33d728b50052981de531de4c7:HipDHuSQHhMNklx_v1iT1-NaOKM=:eyJkZWFkbGluZSI6MTUxNzkyMzczOCwiYWN0aW9uIjoiZ2V0IiwidWlkIjoiNjI4NDU3IiwiYWlkIjoiMTQwNzg1OCIsImZyb20iOiJmaWxlIn0=";
        netUtil.getInstance(headers, PublishAPI.class)
                .uploadFiles(getPart("Token", token), getPart(userId + file.getName(), file))
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }
}
