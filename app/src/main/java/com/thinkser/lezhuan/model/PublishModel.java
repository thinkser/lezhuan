package com.thinkser.lezhuan.model;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.thinkser.core.base.BaseModel;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.lezhuan.api.PublishAPI;
import com.thinkser.lezhuan.entity.FileEntity;
import com.thinkser.lezhuan.entity.Publish;
import com.thinkser.lezhuan.entity.Store;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    public void createPublish(Publish publish, BaseObserver<Publish> baseObserver) {
        netUtil.getInstance(headers, PublishAPI.class)
                .createPublish(Publish.class.getSimpleName(), getBody(publish))
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    public void changePublish(String publishId, Publish publish, BaseObserver<Map<String, String>> baseObserver) {
        netUtil.getInstance(headers, PublishAPI.class)
                .changePublish(Publish.class.getSimpleName(), publishId, getBody(publish))
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

    public void deleteFile(String findUrl, BaseObserver<Map<String, String>> baseObserver) {
        netUtil.getInstance(headers, PublishAPI.class)
                .getPid(findUrl)
                .map(responseBody -> {
                    String[] html = responseBody.string().split("pid", 2);
                    String[] pid = html[1].split("\'", 3);
                    return pid[1];
                })
                .flatMap(s -> netUtil.getInstance(headers, PublishAPI.class)
                        .deleteFiles(Integer.valueOf(s)))
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    public void getFile(Context context, String url, BaseObserver<File> baseObserver) {
        netUtil.getInstance(headers, PublishAPI.class)
                .getFiles(url)
                .map(responseBody -> {
                    File file = new File(context.getCacheDir().getAbsolutePath(), "" + System.currentTimeMillis());
                    FileOutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(responseBody.bytes());
                    outputStream.close();
                    return file;
                })
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    public void getStore(String storeId, BaseObserver<Store> baseObserver) {
        netUtil.getInstance(headers, PublishAPI.class)
                .getStore(Store.class.getSimpleName(), storeId)
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

}
