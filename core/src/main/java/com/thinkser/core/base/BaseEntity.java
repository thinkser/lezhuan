package com.thinkser.core.base;

import cn.bmob.v3.BmobObject;

/**
 * 所有实体类继承此类
 */

public class BaseEntity extends BmobObject {

    private Integer code = 0;
    private String error;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
