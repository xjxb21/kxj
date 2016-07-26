package com.bigvideo.kxj.dao;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Created by xiao on 2016/7/18.
 */
public interface IBigPersonPhotoDao {
    /**
     * 根据photoId更新图片
     *
     * @param photoId 与BIGPERSON.PERSONID关联
     */
    void updatePersonPic(final int photoId, final InputStream is, final int isLength);

    /***
     * 根据photoId清空Blob字段内容
     *
     * @param photoId
     */
    void emptyPersonPic(final int photoId);

    /**
     * 根据photoId删除对应的图片
     *
     * @param photoId
     */
    void delPersonPic(final int photoId);

    /**
     * 根据personId 删除对应的图片
     * @param personId
     */
    void delPersonPicByPersonID(final int personId);

    /**
     * @param personId  人员ID
     * @param is       图片的输入流
     * @param isLength FILE图片长度
     * @return 返回插入图片ID
     */
    int istPersonPic(final Integer personId, final InputStream is, final int isLength);

    /**
     * 根据photoId查找对应的图片
     *
     * @param photoId
     */
    File getPersonPicByPhotoId(final int photoId);

    /**
     * 根据personId查找对应的图片
     *
     * @param personId
     * @return 返回对应图片的 photoId 集合
     */
    List getPersonPicByPersonId(final int personId);

    /**
     * 根据photoId 查询对应的人员personId
     *
     * @param photoId 图片ID
     * @return
     */
    int getPersonIdByPhotoId(int photoId);

}
