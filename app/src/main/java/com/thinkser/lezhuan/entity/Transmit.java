package com.thinkser.lezhuan.entity;

import com.thinkser.core.base.BaseEntity;

/**
 * 转发表
 */

public class Transmit extends BaseEntity {

    private String transmitId;
    private String receiverId;
    private String publishId;

    public String getTransmitId() {
        return transmitId;
    }

    public void setTransmitId(String transmitId) {
        this.transmitId = transmitId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getPublishId() {
        return publishId;
    }

    public void setPublishId(String publishId) {
        this.publishId = publishId;
    }
}
