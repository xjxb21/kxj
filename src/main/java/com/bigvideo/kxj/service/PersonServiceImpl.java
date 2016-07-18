package com.bigvideo.kxj.service;

import com.bigvideo.kxj.dao.BigPersonDao;
import com.bigvideo.kxj.dao.BigPersonPhotoDao;
import com.bigvideo.kxj.dao.support.PageInfo;
import com.bigvideo.kxj.entity.BigPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 科学家信息维护
 */
@Service(value = "personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    BigPersonDao bigPersonDao;

    @Autowired
    BigPersonPhotoDao bigPersonPhotoDao;

    /**
     * add科学家人员信息
     */
    @Override
    public int addPerson(BigPerson person) {
        int key = bigPersonDao.istPerson(person);
        return key;
    }

    /**
     * delete科学家
     */
    @Override
    @Transactional
    public void delPerson(BigPerson person) {
        bigPersonDao.delPerson(person);
        bigPersonPhotoDao.delPersonPic(person.getPersonId());
    }

    /**
     * update科学家信息
     */
    @Override
    public void updatePerson(BigPerson person) {
        bigPersonDao.updatePerson(person);
    }

    /**
     * update科学家个人照片
     */
    @Override
    public void updatePersonPic(BigPerson person, InputStream in, int inLength) {
        bigPersonPhotoDao.updatePersonPic(person.getPersonId(), in, inLength);
    }

    /**
     * add科学家照片
     *
     * @param person
     * @param in       图片流
     * @param inLength 文件长度
     */
    @Override
    public void istPersonPic(BigPerson person, InputStream in, int inLength) {
        bigPersonPhotoDao.istPersonPic(person.getPersonId(), in, inLength);
    }

    /**
     * GET科学家的照片
     *
     * @param person
     * @return
     * @see PersonService#getPersonPic(int)
     */
    @Override
    public File getPersonPic(BigPerson person) {
        return getPersonPic(person.getPersonId());
    }

    /**
     * GET科学家的照片
     *
     * @param photoId
     * @return
     * @see PersonService#getPersonPic(BigPerson)
     */
    @Override
    public File getPersonPic(int photoId) {
        try {
            return bigPersonPhotoDao.queryPersonPic(photoId);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据ID查询单个科学家
     *
     * @param personId
     * @return
     */
    @Override
    public BigPerson queryPerson(int personId) {
        return bigPersonDao.queryPerson(personId);
    }

    /**
     * 分页查询 查询所有科学家信息
     *
     * @param pageNum  第几页
     * @param pageSize 每页显示多少条
     * @return
     */
    @Override
    public PageInfo queryPerson(Integer pageNum, Integer pageSize) {
        PageInfo pageInfo = bigPersonDao.queryAllPerson(pageNum, pageSize);
        return pageInfo;
    }
}
