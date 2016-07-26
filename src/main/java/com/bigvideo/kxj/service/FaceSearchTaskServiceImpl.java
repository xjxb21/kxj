package com.bigvideo.kxj.service;

import com.bigvideo.kxj.dao.FaceSearchTaskDao;
import com.bigvideo.kxj.utils.RequestCompareFace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by xiao on 2016/7/12.
 *
 */
@Service(value = "faceSearchTaskService")
public class FaceSearchTaskServiceImpl implements IFaceSearchTaskService {

    @Value("${kxj.faceCompareCGI}")
    String faceCompareCGI;

    @Autowired
    FaceSearchTaskDao faceSearchTaskDao;

    /**
     * 对比图片接口
     *
     * @param is       对比的图片输入流
     * @param isLength 流的长度
     * @return 对比接口返回的对比会话ID，会话ID为 FACESEARCHRESULT.SESSIONID, 如果没有匹配 则返回-1
     */
    @Override
    public int compareFace(InputStream is, int isLength) {
        String retSessionId = invokeHttpInterface(addTask(is, isLength));
        return Integer.valueOf(retSessionId);
    }

    /**
     * 添加对比任务
     *
     * @param is
     * @param isLength
     * @return
     */
    private int addTask(InputStream is, int isLength) {
        return faceSearchTaskDao.addTask(is, isLength);
    }

    /**
     * 调用HTTP对比接口，获取接口返回SESSIONID信息
     *
     * @param TaskId 对比ID
     * @return
     */
    private String invokeHttpInterface(int TaskId) {

        return RequestCompareFace.sendGet(this.faceCompareCGI, "TaskID=" + TaskId);
    }

}
