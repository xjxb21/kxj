package com.bigvideo.kxj.service;

import com.bigvideo.kxj.dao.FaceSearchTaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by xiao on 2016/7/12.
 */
@Service
public class FaceSearchTaskImpl implements FaceSearchTask {

    @Autowired
    FaceSearchTaskDao faceSearchTaskDao;

    @Override
    public Boolean addTask(InputStream is, int isLength) {
        faceSearchTaskDao.addTask(is, isLength);
        return null;
    }
}
