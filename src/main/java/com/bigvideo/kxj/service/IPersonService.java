package com.bigvideo.kxj.service;

import com.bigvideo.kxj.dao.support.PageInfo;
import com.bigvideo.kxj.entity.BigPerson;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Created by xiao on 2016/7/10.
 */
public interface IPersonService {

    /**
     * 添加科学家人员
     *
     * @param person
     * @return 记录主键ID
     */
    int addPerson(BigPerson person);

    /**
     * 删除科学家
     */
    void delPerson(BigPerson person);

    /**
     * 更新科学家
     */
    void updatePerson(BigPerson person);

    /**
     * 更新科学家个人照片
     */
    void updatePersonPic(BigPerson person, InputStream in, int inLength);

    /**
     * 添加科学家照片
     *
     * @param person
     * @param in       图片流
     * @param inLength 文件长度
     * @return id 返回插入的图片ID
     */
    int istPersonPic(BigPerson person, InputStream in, int inLength);

    /**
     * 根据PERSONID, GET科学家的照片列表
     *
     * @param personId
     * @return  photoId列表
     * @see IPersonService#getPersonPicByPersonId(int)
     */
    List getPersonPicByPersonId(int personId);

    /**
     * 根据photoId , GET科学家的照片
     *
     * @param photoId
     * @return
     */
    File getPersonPicByPhotoId(int photoId);

    /**
     * 根据ID查询单个科学家
     *
     * @param personId
     * @return
     */
    BigPerson queryPerson(int personId);

    /**
     * 【支持分页查询】，pageNum 或 pageSize 为空，那么则搜索全部
     *
     * @param querySql 查询的表名
     * @param pageNum  第几页
     * @param pageSize 每页显示多少条
     * @return
     */
    PageInfo queryPerson(String querySql, Integer pageNum, Integer pageSize);

}
