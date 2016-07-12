package com.bigvideo.kxj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xiao on 2016/7/7.
 * 科学家图片更新
 */
@Repository(value = "bigPersonPhotoDao")
public class BigPersonPhotoDao {

    @Value("${kxj.blankJpg}")
    String blankJpg;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private LobHandler lobHandler = new DefaultLobHandler();

    /**
     * 更新科学家图片
     *
     * @param photoId 与BIGPERSON.PERSONID关联
     */
    public void updatePersonPic(final int photoId, final InputStream is, final int isLength) {

        String sql = "UPDATE BIGPERSONPHOTO SET PERSONPHOTO = ? WHERE PHOTOID= ?";
        //LobHandler lobHandler = new DefaultLobHandler();
        jdbcTemplate.execute(sql, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
            @Override
            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                lobCreator.setBlobAsBinaryStream(ps, 1, is, isLength);
                ps.setInt(2, photoId);
            }
        });
    }

    /***
     * 重置 清空科学家图片Blob字段内容
     *
     * @param photoId
     */
    public void emptyPersonPic(final int photoId) {
        String sql = "UPDATE BIGPERSONPHOTO SET PERSONPHOTO = NULL WHERE PHOTOID=?";
        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, photoId);
            }
        });
    }

    /**
     * 删除对应的科学家图片记录
     *
     * @param photoId
     */
    public void delPersonPic(final int photoId) {
        String sql = "DELETE * FROM BIGPERSONPHOTO WHERE PHOTOID=?";
        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, photoId);
            }
        });
    }

    /**
     * @param photoId  插入图片的ID
     * @param is       图片的输入流
     * @param isLength FILE图片长度
     */
    public void istPersonPic(final int photoId, final InputStream is, final int isLength) {
        String sql = "INSERT INTO BIGPERSONPHOTO (PHOTOID, PERSONPHOTO) VALUES (?,?)";
        //LobHandler lobHandler = new OracleLobHandler();   //如果为ORACLE 10G 请注意使用不同的版本驱动包
        //LobHandler lobHandler = new DefaultLobHandler();
        jdbcTemplate.execute(sql, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
            @Override
            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                ps.setInt(1, photoId);
                lobCreator.setBlobAsBinaryStream(ps, 2, is, isLength);
            }
        });
    }

    /**
     * 查找对应的科学家照片
     *
     * @param photoId
     */
    public File queryPersonPic(final int photoId) throws FileNotFoundException {
        String sql = "SELECT PERSONPHOTO FROM BIGPERSONPHOTO WHERE PHOTOID= ?";
        final LobHandler lobHandler = new DefaultLobHandler();

        //读取Blob字段
        final File blackImg = new File(blankJpg);
        jdbcTemplate.query(sql, new Object[]{photoId}, new AbstractLobStreamingResultSetExtractor() {
            @Override
            protected void streamData(ResultSet rs) throws SQLException, IOException, DataAccessException {
                FileCopyUtils.copy(lobHandler.getBlobAsBytes(rs, 1), blackImg);
            }
        });

        return blackImg;
    }
}
