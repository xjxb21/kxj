package com.bigvideo.kxj.service;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by xiao on 2016/7/12.
 */
public interface IFaceSearchTaskService {

    /**
     * 增加人脸对比任务数据，调用C++程序接口
     * @param is    对比的图片输入流
     * @param isLength  流的长度
     * @return  对比接口返回的对比会话ID，会话ID为 FACESEARCHRESULT.SESSIONID
     *            任务ID facesearchtask.taskId
     */
    int compareFace(InputStream is, int isLength);
}
