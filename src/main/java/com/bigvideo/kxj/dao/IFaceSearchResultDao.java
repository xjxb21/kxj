package com.bigvideo.kxj.dao;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by xiao on 2016/7/21.
 */
public interface IFaceSearchResultDao {

    /**
     * 根据sessionId获取photoId列表[包含比分值]
     * @param sessionId
     * @return
     */
    List<Map> getPhotoIdBySessionId(int sessionId);

    /**
     * 根据photoId获取的图片
     *
     * @param photoId
     * @return
     */
    File getFaceImgByPhotoId(int photoId);
}
