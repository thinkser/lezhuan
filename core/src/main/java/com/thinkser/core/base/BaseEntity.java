package com.thinkser.core.base;

import java.io.Serializable;

/**
 * 所有实体类继承此类
 */

public class BaseEntity implements Serializable {

    private String createdAt;
    private String objectId;
    private String updateAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public void clearSystemData() {
        objectId = null;
        updateAt = null;
        createdAt = null;
    }
}
