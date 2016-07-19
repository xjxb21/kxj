package com.bigvideo.kxj.dao;

import com.bigvideo.kxj.dao.support.PageInfo;
import com.bigvideo.kxj.dao.support.Pagination;
import com.bigvideo.kxj.entity.BigPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.security.Key;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by xiao on 2016/7/7.
 */
@Repository(value = "bigPersonDao")
public class BigPersonDao implements IBigPersonDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 增加科学家信息,返回插入主键
     *
     * @param person
     * @return 成功返回主键
     */
    @Override
    public int istPerson(final BigPerson person) {
        String sql = "SELECT PERSONID.NEXTVAL FROM DUAL";
        final Integer keyId = jdbcTemplate.queryForObject(sql, Integer.class);

        String istSql = "INSERT INTO BIGPERSON (PERSONID, NAME, HISTORY) VALUES (?,?,?)";
        jdbcTemplate.update(istSql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, keyId.intValue());
                preparedStatement.setString(2, person.getName());
                preparedStatement.setString(3, person.getHistory());
            }
        });
        return keyId;
    }

    /**
     * 更新科学家信息
     *
     * @param person
     */
    @Override
    public void updatePerson(final BigPerson person) {
        String sql = "UPDATE BIGPERSON SET NAME=?, HISTORY=? WHERE PERSONID=?";
        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, person.getName());
                ps.setString(2, person.getHistory());
                ps.setInt(3, person.getPersonId());
            }
        });
    }

    /**
     * 删除科学家信息
     *
     * @param person
     */
    @Override
    public void delPerson(BigPerson person) {
        //String sql = "DELETE P1.*, P2.* FROM BIGPERSON P1, BIGPERSONPHOTO P2 WHERE P1.PERSONID=P2.PHOTOID AND P1.PERSONID=?";
        String sql = "DELETE FROM BIGPERSON P1 WHERE P1.PERSONID=?";
        jdbcTemplate.update(sql, new Object[]{person.getPersonId()});
    }


    /**
     * 获取所有的科学家信息【支持分页】
     *
     * @param curPage
     * @param pageSize
     * @return
     */
    /*public List<Map<String, Object>> queryAllPerson(Integer curPage, Integer pageSize) {

        String sql = "SELECT PERSONID, NAME, HISTORY FROM BIGPERSON";
        if (curPage == null || pageSize == null) {
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
            return mapList;
        } else {
            Pagination pagination = new Pagination(curPage, pageSize, sql, jdbcTemplate);
            return pagination.getResultList();
        }
    }*/
    @Override
    public PageInfo queryAllPerson(Integer curPage, Integer pageSize) {

        PageInfo pageInfo = new PageInfo();
        String sql = "SELECT PERSONID, NAME, HISTORY FROM BIGPERSON";
        if (curPage == null || pageSize == null) {
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
            pageInfo.setListInfo(mapList);
        } else {
            Pagination pagination = new Pagination(curPage, pageSize, sql, jdbcTemplate);
            pageInfo.setTotalRows(pagination.getTotalRows());
            pageInfo.setListInfo(pagination.getResultList());
        }
        return pageInfo;
    }

    /**
     * 根据ID 获取科学家信息
     *
     * @param personId 科学家ID
     */
    @Override
    public BigPerson queryPerson(int personId) {

        String sql = "SELECT PERSONID, NAME, HISTORY FROM BIGPERSON WHERE PERSONID=?";
        try {
            BigPerson person = jdbcTemplate.queryForObject(sql, new Object[]{new Integer(personId)}, new RowMapper<BigPerson>() {
                @Override
                public BigPerson mapRow(ResultSet rs, int rowNum) throws SQLException {
                    BigPerson ret = new BigPerson();
                    ret.setPersonId(rs.getInt("PERSONID"));
                    ret.setName(rs.getString("NAME"));
                    ret.setHistory(rs.getString("HISTORY"));
                    return ret;
                }
            });
            return person;
        } catch (Exception e) {
            //结果集小于0 或 大于1 会Spring会抛出异常
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 根据关键字搜索
     *
     * @param field 字段名
     * @param key   关键字
     * @return
     */
    @Override
    public PageInfo searchByKey(String field, String key) {
        /*PageInfo pageInfo = new PageInfo();
        String sql = "SELECT * FROM BIGPERSON WHERE " + field + " LIKE %"+ key+"%";

        Pagination pagination = new Pagination(curPage, pageSize, sql, jdbcTemplate);
        pageInfo.setTotalRows(pagination.getTotalRows());
        pageInfo.setListInfo(pagination.getResultList());*/
        return null;
    }
}
