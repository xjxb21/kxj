package com.bigvideo.kxj.service;

import com.bigvideo.kxj.dao.support.PageInfo;
import com.bigvideo.kxj.entity.BigPerson;

import java.io.File;
import java.io.InputStream;

/**
 * Created by xiao on 2016/7/10.
 */
public interface IPersonService {

    /**
     * 添加科学家人员
     * @param person
     * @return 记录主键ID
     */
    //public int addPerson(BigPerson person, InputStream inPic, int inPicLength);
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
    void updatePersonPic(BigPerson person, InputStream in, int inLength) ;

    /**
     * 添加科学家照片
     *
     * @param person
     * @param in       图片流
     * @param inLength 文件长度
     */
    void istPersonPic(BigPerson person, InputStream in, int inLength) ;


    /**
     * 获取科学家的照片
     *
     * @param person
     * @return
     * @see IPersonService#getPersonPic(int)
     */
    File getPersonPic(BigPerson person);

    /**
     * 获取科学家的照片
     *
     * @param photoId
     * @return
     * @see IPersonService#getPersonPic(BigPerson)
     */
    File getPersonPic(int photoId);

    /**
     * 根据ID查询单个科学家
     * @param personId
     * @return
     */
    BigPerson queryPerson(int personId);

    /**
     * 【支持分页查询】，pageNum 或 pageSize 为空，那么则搜索全部
     * @param pageNum   第几页
     * @param pageSize  每页显示多少条
     * @return
     */
    PageInfo queryPerson(Integer pageNum, Integer pageSize);

}
