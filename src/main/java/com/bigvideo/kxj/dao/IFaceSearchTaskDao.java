package com.bigvideo.kxj.dao;

import java.io.InputStream;

/**
 * Created by xiao on 2016/7/18.
 */
public interface IFaceSearchTaskDao {
    /**
     * 增加人脸对比任务
     * @param is
     * @param isLength
     * @return  -1代表插入失败， 成功则返回主键ID
     */
    int addTask(final InputStream is, final int isLength);
}
