package com.bigvideo.kxj.service;

import com.bigvideo.kxj.entity.BigPerson;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Created by xiao on 2016/7/10.
 */
public interface PersonService {

    /**
     * 添加科学家人员
     * @param person
     */
    public void addPerson(BigPerson person);

    /**
     * 删除科学家
     */
    public void delPerson(BigPerson person);

    /**
     * 更新科学家
     */
    public void updatePerson(BigPerson person);

    /**
     * 更新科学家个人照片
     */
    public void updatePersonPic(BigPerson person, InputStream in, int inLength) ;

    /**
     * 添加科学家照片
     *
     * @param person
     * @param in       图片流
     * @param inLength 文件长度
     */
    public void istPersonPic(BigPerson person, InputStream in, int inLength) ;


    /**
     * 获取科学家的照片
     *
     * @param person
     * @return
     * @see PersonService#getPersonPic(int)
     */
    public File getPersonPic(BigPerson person);

    /**
     * 获取科学家的照片
     *
     * @param photoId
     * @return
     * @see PersonService#getPersonPic(BigPerson)
     */
    public File getPersonPic(int photoId);

    /**
     * 根据ID查询单个科学家
     * @param personId
     * @return
     */
    public BigPerson queryPerson(int personId);

    /**
     * 查询所有科学家信息
     */
    public List<BigPerson> queryPerson();


    /**
     * 分页查询
     * @param pageNum   第几页
     * @param pageSize  每页显示多少条
     * @return
     */
    public List<BigPerson> queryPerson(int pageNum, int pageSize);

}
