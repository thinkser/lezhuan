package com.thinkser.lezhuan.entity;

/**
 * 上传图片返回的json
 */

public class FileEntity {

    private double width;//图片宽度
    private double height;//图片高度
    private String type;//图片类型
    private double size;//图片大小（字节）
    private String ubburl;
    private String linkurl;//原图链接
    private String htmlurl;
    private String markdown;
    private String s_url;
    private String t_url;
    private String findurl;//获取pid的url

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
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

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
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

    public String getFindurl() {
        return findurl;
    }

    public void setFindurl(String findurl) {
        this.findurl = findurl;
    }
}
