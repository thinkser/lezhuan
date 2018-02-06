package com.thinkser.lezhuan.entity;

/**
 * 上传图片返回的json
 */

public class FileEntity {

    private String width;//图片的宽
    private String height;// 图片的高
    private String type;
    private String size;//图片大小(单位:字节)
    private String ubburl;//图片UBB引用代码
    private String linkurl;//图片原图地址
    private String htmlurl;//图片HTML引用代码
    private String s_url;//图片展示图地址
    private String t_url;//图片缩略图地址

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUbburl() {
        return ubburl;
    }

    public void setUbburl(String ubburl) {
        this.ubburl = ubburl;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public String getHtmlurl() {
        return htmlurl;
    }

    public void setHtmlurl(String htmlurl) {
        this.htmlurl = htmlurl;
    }

    public String getS_url() {
        return s_url;
    }

    public void setS_url(String s_url) {
        this.s_url = s_url;
    }

    public String getT_url() {
        return t_url;
    }

    public void setT_url(String t_url) {
        this.t_url = t_url;
    }
}
