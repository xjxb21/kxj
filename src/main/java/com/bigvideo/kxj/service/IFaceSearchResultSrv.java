package com.bigvideo.kxj.service;

import java.io.File;
import java.util.List;

/**
 * Created by xiao on 2016/7/20.
 */
public interface IFaceSearchResultSrv {

    /**
     * 根据essionId获取对比结果
     * @param sessionId
     * @return
     */
    List getPhotoIdsBySessionId(int sessionId);

    /**
     * 根据photoId获取人脸图片
     * @param photoId
     * @return
     */
    File getFaceImgByPhotoId(int photoId);
}
