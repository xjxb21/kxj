package com.bigvideo.kxj.dao;

import java.io.File;
import java.io.InputStream;

/**
 * Created by xiao on 2016/7/18.
 */
public interface IBigPersonPhotoDao {
    /**
     * 更新科学家图片
     *
     * @param photoId 与BIGPERSON.PERSONID关联
     */
    void updatePersonPic(final int photoId, final InputStream is, final int isLength);

    /***
     * 重置 清空科学家图片Blob字段内容
     *
     * @param photoId
     */
    void emptyPersonPic(final int photoId);

    /**
     * 删除对应的科学家图片记录
     *
     * @param photoId
     */
    void delPersonPic(final int photoId);

    /**
     * @param photoId  插入图片的ID
     * @param is       图片的输入流
     * @param isLength FILE图片长度
     */
    void istPersonPic(final int photoId, final InputStream is, final int isLength);

    /**
     * 查找对应的科学家照片
     *
     * @param photoId
     */
    File queryPersonPic(final int photoId);
}
