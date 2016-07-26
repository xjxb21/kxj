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
import java.util.List;

/**
 * Created by xiao on 2016/7/7.
 * 科学家图片更新
 */
@Repository(value = "bigPersonPhotoDao")
public class BigPersonPhotoDao implements IBigPersonPhotoDao{

    @Value("${kxj.blankJpg}")
    String blankJpg;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final LobHandler lobHandler = new DefaultLobHandler();

    /**
     * 根据photoId更新图片
     *
     * @param photoId 与BIGPERSON.PERSONID关联
     */
    @Override
    public void updatePersonPic(final int photoId, final InputStream is, final int isLength) {

        String sql = "UPDATE BIGPERSONPHOTO SET PERSONPHOTO = ? WHERE PHOTOID= ?";
        jdbcTemplate.execute(sql, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
            @Override
            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                lobCreator.setBlobAsBinaryStream(ps, 1, is, isLength);
                ps.setInt(2, photoId);
            }
        });
    }

    /***
     * 根据photoId清空Blob字段内容
     *
     * @param photoId
     */
    @Override
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
     * 根据photoId删除对应的图片
     *
     * @param photoId
     */
    @Override
    public void delPersonPic(final int photoId) {
        String sql = "DELETE FROM BIGPERSONPHOTO WHERE PHOTOID=?";
        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, photoId);
            }
        });
    }

    /**
     * 根据personId 删除对应的图片
     * @param personId
     */
    @Override
    public void delPersonPicByPersonID(final int personId) {
        String sql = "DELETE FROM BIGPERSONPHOTO WHERE PERSONID=?";
        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, personId);
            }
        });
    }

    /**
     * @param personId  人员ID
     * @param is       图片的输入流
     * @param isLength FILE图片长度
     * @return 如果personId为null，返回生成的personId；否则返回图片的photoId
     */
    @Override
    public int istPersonPic(final Integer personId, final InputStream is, final int isLength) {

        String sql;
        int retId;
        final int personIdVal;

        sql = "SELECT PERSONPHOTO_AUTOID.NEXTVAL FROM DUAL";
        final Integer photoId = jdbcTemplate.queryForObject(sql, Integer.class);
        retId = photoId;

        if (null == personId) {
            //如果PERSONID 为空 则生成一个PERSONID出来，retId 为 persondId
            sql = "SELECT PERSONID.NEXTVAL FROM DUAL";
            personIdVal = jdbcTemplate.queryForObject(sql, Integer.class);
            retId = personIdVal;
        } else {
            personIdVal = personId.intValue();
        }

        sql = "INSERT INTO BIGPERSONPHOTO (PHOTOID, PERSONID, PERSONPHOTO) VALUES (?,?,?)";
        //LobHandler lobHandler = new OracleLobHandler();   //如果为ORACLE 10G 请注意使用不同的版本驱动包
        jdbcTemplate.execute(sql, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
            @Override
            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                ps.setInt(1, photoId);
                ps.setInt(2, personIdVal);
                lobCreator.setBlobAsBinaryStream(ps, 3, is, isLength);
            }
        });

        return retId;
    }

    /**
     * 查找对应的科学家照片
     *
     * @param photoId
     */
    @Override
    public synchronized File getPersonPicByPhotoId(final int photoId) {
        String sql = "SELECT PERSONPHOTO FROM BIGPERSONPHOTO WHERE PHOTOID= ?";

        final File tmpFile = new File(blankJpg);

        jdbcTemplate.query(sql, new Object[]{photoId}, new AbstractLobStreamingResultSetExtractor() {
            @Override
            protected void streamData(ResultSet rs) throws SQLException, IOException, DataAccessException {
                //异步方法，需要同步代码块
                FileCopyUtils.copy(lobHandler.getBlobAsBytes(rs, 1), tmpFile);
            }
        });
        return tmpFile;
    }

    /**
     * 根据personId查找对应的图片photoId 集合
     *
     * @param personId
     * 返回对应图片的 photoId 集合
     */
    @Override
    public List getPersonPicByPersonId(int personId) {
        String sql = "SELECT PHOTOID FROM BIGPERSONPHOTO WHERE PERSONID= ?";
        List<Integer> photoIdList = jdbcTemplate.queryForList(sql, Integer.class, new Object[]{new Integer(personId)});
        return photoIdList;
    }

    /**
     * 根据photoId 查询对应的人员personId
     *
     * @param photoId 图片ID
     * @return
     */
    @Override
    public int getPersonIdByPhotoId(int photoId) {
        String sql = "SELECT PERSONID FROM BIGPERSONPHOTO WHERE PHOTOID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{photoId}, Integer.class);
    }

}
