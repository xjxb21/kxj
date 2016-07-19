package com.bigvideo.kxj.dao;

import com.bigvideo.kxj.dao.support.PageInfo;
import com.bigvideo.kxj.entity.BigPerson;

/**
 * Created by xiao on 2016/7/18.
 */
public interface IBigPersonDao {

    /**
     * 增加科学家信息,返回插入主键
     *
     * @param person
     * @return 成功返回主键
     */
    int istPerson(final BigPerson person);

    /**
     * 更新科学家信息
     *
     * @param person
     */
    void updatePerson(final BigPerson person);

    /**
     * 删除科学家信息
     *
     * @param person
     */
    void delPerson(BigPerson person);

    /**
     * 获取所有的科学家信息【支持分页】
     *
     * @param curPage
     * @param pageSize
     * @return
     */
    PageInfo queryAllPerson(Integer curPage, Integer pageSize);

    /**
     * 根据ID 获取科学家信息
     *
     * @param personId 科学家ID
     */
    BigPerson queryPerson(int personId);

    /**
     * 根据关键字搜索
     * @param field 字段名
     * @param key  关键字
     * @return
     */
    PageInfo searchByKey(String field, String key);
}
