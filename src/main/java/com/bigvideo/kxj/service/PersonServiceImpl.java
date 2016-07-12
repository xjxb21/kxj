package com.bigvideo.kxj.service;

import com.bigvideo.kxj.dao.BigPersonDao;
import com.bigvideo.kxj.dao.BigPersonPhotoDao;
import com.bigvideo.kxj.entity.BigPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public void addPerson(BigPerson person) {
        bigPersonDao.istPerson(person);
    }

    /**
     * delete科学家
     */
    @Override
    public void delPerson(BigPerson person) {
        bigPersonDao.delPerson(person);
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
     * query科学家信息
     */
    @Override
    public List<BigPerson> queryPerson() {
        List<Map<String, Object>> maplist = bigPersonDao.queryAllPerson();
        List<BigPerson> bigPersonList = new ArrayList();
        for (int i=0; i<maplist.size(); i++) {
            Map<String, Object> map = maplist.get(i);
            int personId = ((BigDecimal)map.get("PERSONID")).intValue();
            String name = map.get("NAME").toString();
            String history = map.get("HISTORY").toString();

            bigPersonList.add(new BigPerson(personId, name, history));
        }
        return bigPersonList;
    }
}
