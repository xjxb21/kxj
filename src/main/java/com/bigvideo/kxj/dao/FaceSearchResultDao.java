package com.bigvideo.kxj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by xiao on 2016/7/21.
 */
@Repository(value = "faceSearchResultDao")
public class FaceSearchResultDao implements IFaceSearchResultDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Value("${kxj.blankJpg}")
    String blankJpg;

    private final LobHandler lobHandler = new DefaultLobHandler();

    /**
     * 根据sessionId获取photoId列表
     *
     * @param sessionId
     * @return
     */
    @Override
    public List<Integer> getPhotoIdBySessionId(int sessionId) {

        String sql = "SELECT PHOTOID FROM FACESEARCHRESULT WHERE SESSIONID=?";
        List photoIdList = jdbcTemplate.queryForList(sql, Integer.class, new Object[]{new Integer(sessionId)});
        return photoIdList;
    }

    /**
     * 根据photoId获取的图片
     * @param photoId
     * @return
     */
    @Override
    public File getFaceImgByPhotoId(int photoId) {

        String sql = "SELECT PERSONPHOTO FROM BIGPERSONPHOTO WHERE PHOTOID= ?";
        final File tmpFile = new File(blankJpg);

        jdbcTemplate.query(sql, new Object[]{photoId}, new AbstractLobStreamingResultSetExtractor() {
            @Override
            protected void streamData(ResultSet rs) throws SQLException, IOException, DataAccessException {
                FileCopyUtils.copy(lobHandler.getBlobAsBytes(rs, 1), tmpFile);
            }
        });

        return tmpFile;
    }
}
