package com.thinkser.lezhuan.entity;

import com.thinkser.core.base.BaseEntity;

import java.util.ArrayList;

/**
 * 广告实体类
 */

public class Publish extends BaseEntity {

    private String userId, storeId, title, content, classify;
    private Integer prizeCount = 0, integral = 0;
    private ArrayList<String> photos, findUrls;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public Integer getPrizeCount() {
        return prizeCount;
    }

    public void setPrizeCount(Integer prizeCount) {
        this.prizeCount = prizeCount;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public ArrayList<String> getFindUrls() {
        return findUrls;
    }

    public void setFindUrls(ArrayList<String> findUrls) {
        this.findUrls = findUrls;
    }
}
