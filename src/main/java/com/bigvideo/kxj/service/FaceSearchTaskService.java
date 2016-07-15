package com.bigvideo.kxj.service;

import java.io.InputStream;

/**
 * Created by xiao on 2016/7/12.
 */
public interface FaceSearchTaskService {

    /**
     * 增加人脸对比任务数据，调用C++程序接口
     * @param is    对比的图片输入流
     * @param isLength  流的长度
     * @return  对比接口返回的对比会话ID，会话ID为 FACESEARCHRESULT.SESSIONID
     */
    public String compareFace(InputStream is, int isLength);
}
