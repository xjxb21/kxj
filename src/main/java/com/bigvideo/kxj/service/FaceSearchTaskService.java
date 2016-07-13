package com.bigvideo.kxj.service;

import java.io.InputStream;

/**
 * Created by xiao on 2016/7/12.
 */
public interface FaceSearchTaskService {

    /**
     * 增加人脸对比任务数据，供C++程序调用
     * @param is
     * @param isLength
     */
    public int addTask(InputStream is, int isLength);
}
