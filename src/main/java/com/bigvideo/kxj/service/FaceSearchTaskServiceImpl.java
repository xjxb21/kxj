package com.bigvideo.kxj.service;

import com.bigvideo.kxj.dao.FaceSearchTaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by xiao on 2016/7/12.
 */
@Service(value = "faceSearchTaskService")
public class FaceSearchTaskServiceImpl implements FaceSearchTaskService {

    @Autowired
    FaceSearchTaskDao faceSearchTaskDao;

    @Override
    public int addTask(InputStream is, int isLength) {
        return faceSearchTaskDao.addTask(is, isLength);
    }
}
