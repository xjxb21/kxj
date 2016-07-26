package com.bigvideo.kxj.service;

import com.bigvideo.kxj.dao.IFaceSearchResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Created by xiao on 2016/7/20.
 */
@Service(value = "faceSearchResultSrv")
public class FaceSearchResultSrvImpl implements IFaceSearchResultSrv {

    @Autowired
    IFaceSearchResultDao faceSearchResultDao;

    /**
     * 根据sessionId获取相似的结果集
     * @param sessionId
     * @return
     */
    @Override
    public List<Integer> getPhotoIdsBySessionId(int sessionId) {
        List<Integer> list = faceSearchResultDao.getPhotoIdBySessionId(sessionId);
        return list;
    }

    /**
     * 根据photoId获取人脸图片
     *
     * @param photoId
     * @return
     */
    @Override
    public File getFaceImgByPhotoId(int photoId) {
        return faceSearchResultDao.getFaceImgByPhotoId(photoId);
    }
}
