package com.bigvideo.kxj.dao;

import com.bigvideo.kxj.entity.BigPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by xiao on 2016/7/7.
 */
@Repository(value = "bigPersonDao")
public class BigPersonDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 增加科学家信息
     */
    public void istPerson(final BigPerson person) {
        String istSql = "INSERT INTO BIGPERSON (PERSONID, NAME, HISTORY) VALUES (?,?,?)";
        jdbcTemplate.update(istSql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, person.getPersonId());
                preparedStatement.setString(2, person.getName());
                preparedStatement.setString(3, person.getHistory());
            }
        });
    }

    /**
     * 更新科学家信息
     *
     * @param person
     */
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
     * 删除科学家信息,同时删除BIGPERSONPHOTO表对应的数据
     *
     * @param person
     */
    public void delPerson(BigPerson person) {
        String sql = "DELETE P1.*, P2.* FROM BIGPERSON P1, BIGPERSONPHOTO P2 WHERE P1.PERSONID=P2.PHOTOID AND P1.PERSONID=?";
        jdbcTemplate.update(sql, new Object[]{person.getPersonId()});
    }


    /**
     * 获取所有的科学家信息
     *
     * @return
     */
    public List<Map<String, Object>> queryAllPerson() {
        String sql = "SELECT PERSONID, NAME, HISTORY FROM BIGPERSON";
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        return mapList;
    }

    /**
     * 根据ID 获取科学家信息
     */
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
}
