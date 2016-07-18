package com.bigvideo.kxj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by xiao on 2016/7/12.
 */
@Repository(value = "faceSearchTaskDao")
public class FaceSearchTaskDao implements IFaceSearchTaskDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private LobHandler lobHandler = new DefaultLobHandler();

    /**
     * 增加人脸对比任务
     * @param is
     * @param isLength
     * @return  -1代表插入失败， 成功则返回主键ID
     */
    @Override
    public int addTask(final InputStream is, final int isLength) {

        String sql = "SELECT FACECHECK_AUTOID.NEXTVAL FROM DUAL";

        final Integer keyId = jdbcTemplate.queryForObject(sql, Integer.class);

        sql = "INSERT INTO FACESEARCHTASK (ID, FACEIMG) VALUES (?, ?)";
        int ret = jdbcTemplate.execute(sql, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
            @Override
            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                ps.setInt(1, keyId.intValue());

                lobCreator.setBlobAsBinaryStream(ps, 2, is, isLength);
            }
        });

        if (ret < 1) {
            return -1;
        } else {
            return keyId.intValue();
        }
    }
}
