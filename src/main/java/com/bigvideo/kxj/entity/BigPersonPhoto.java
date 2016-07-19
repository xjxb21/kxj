package com.bigvideo.kxj.entity;

import java.io.InputStream;

/**
 * 科学家照片
 */
public class BigPersonPhoto {

    // 科学家图片ID
    Integer photoId;

    // 与Bigperson.personid关联
    Integer personId;

    //科学家图片
    InputStream is;

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
}
