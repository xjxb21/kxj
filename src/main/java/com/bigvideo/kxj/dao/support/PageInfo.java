package com.bigvideo.kxj.dao.support;

import java.util.List;
import java.util.Map;

/**
 * Created by xiao on 2016/7/18.
 * 用于返回分页相关信息
 */
public class PageInfo {

    //信息总数字
    Integer totalRows;

    //信息List
    List<Map<String, Object>> listInfo;

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public List<Map<String, Object>> getListInfo() {
        return listInfo;
    }

    public void setListInfo(List<Map<String, Object>> listInfo) {
        this.listInfo = listInfo;
    }
}
