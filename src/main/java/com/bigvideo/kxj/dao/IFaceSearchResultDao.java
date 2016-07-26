package com.bigvideo.kxj.dao;

import java.io.File;
import java.util.List;

/**
 * Created by xiao on 2016/7/21.
 */
public interface IFaceSearchResultDao {

    /**
     * 根据sessionId获取photoId列表
     * @param sessionId
     * @return
     */
    List getPhotoIdBySessionId(int sessionId);

    /**
     * 根据photoId获取的图片
     *
     * @param photoId
     * @return
     */
    File getFaceImgByPhotoId(int photoId);
}
