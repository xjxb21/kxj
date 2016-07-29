package com.bigvideo.kxj.service;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by xiao on 2016/7/20.
 */
public interface IFaceSearchResultSrv {

    /**
     * 根据essionId获取对比结果【包含比分值】
     * @param sessionId
     * @return
     */
    List<Map> getPhotoIdsBySessionId(int sessionId);

    /**
     * 根据photoId获取人脸图片
     * @param photoId
     * @return
     */
    File getFaceImgByPhotoId(int photoId);
}
