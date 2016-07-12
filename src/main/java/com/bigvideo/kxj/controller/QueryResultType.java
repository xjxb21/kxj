package com.bigvideo.kxj.controller;

/**
 * Created by xiao on 2016/7/11.
 * 查询人员当前返回结果的类型
 */
public class QueryResultType {

    int id;
    String name;
    String history;
    String imgSrc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
