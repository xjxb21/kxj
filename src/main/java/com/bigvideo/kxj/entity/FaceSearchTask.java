package com.bigvideo.kxj.entity;

import java.io.InputStream;

/**
 * Created by xiao on 2016/7/12.
 */
public class FaceSearchTask {

    Integer id;
    InputStream faceImg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InputStream getFaceImg() {
        return faceImg;
    }

    public void setFaceImg(InputStream faceImg) {
        this.faceImg = faceImg;
    }
}
