package com.bigvideo.kxj.dao.support;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;
import java.util.Map;

/**
 * ORACLE分页处理类
 * Created by xiao on 2016/7/15.
 */
public class Pagination extends JdbcDaoSupport {

    // 一页显示的记录数
    private int pageSize;
    // 记录总数
    private int totalRows;
    // 总页数
    private int totalPages;
    // 当前页码
    private int currentPage;
    // 起始行数
    private int startIndex;
    // 结束行数
    private int lastIndex;
    // 结果集存放List
    private List<Map<String, Object>> resultList;

    private JdbcTemplate jtemplate;

    /**
     * 分页构造函数
     *
     * @param sql         根据传入的sql语句得到一些基本分页信息
     * @param currentPage 当前页
     * @param pageSize  每页记录数
     * @param jTemplate   JdbcTemplate实例
     */
    public Pagination(int currentPage, int pageSize, String sql, JdbcTemplate jTemplate) {

        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.jtemplate = jTemplate;

        if (jtemplate == null) {
            throw new IllegalArgumentException(
                    "com.bigvideo.kxj.Pagination.jtemplate is null,please initial it first. ");
        } else if (sql.equals("")) {
            throw new IllegalArgumentException(
                    "com.bigvideo.kxj.Pagination.sql is empty,please initial it first. ");
        }
        // 设置每页显示记录数
        setPageSize(pageSize);
        // 设置要显示的页数
        setCurrentPage(currentPage);
        System.out.println("Pagination currentPage=" + currentPage);
        // 计算总记录数
        StringBuffer totalSQL = new StringBuffer(" SELECT count(1) FROM ( ");
        totalSQL.append(sql);
        totalSQL.append(" ) totalTable ");
        // 给JdbcTemplate赋值
        setJdbcTemplate(jTemplate);
        // 总记录数
        //setTotalRows(getJdbcTemplate().queryForInt(totalSQL.toString()));
        setTotalRows(getJdbcTemplate().queryForObject(totalSQL.toString(), Integer.class));
        // 计算总页数
        setTotalPages();
        // 计算起始行数
        setStartIndex();
        // 计算结束行数
        setLastIndex();
        System.out.println("lastIndex=" + lastIndex);// ////////////////
        // 构造oracle数据库的分页语句
        StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
        paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
        paginationSQL.append(sql);
        paginationSQL.append("　) temp where ROWNUM <= " + lastIndex);
        paginationSQL.append(" ) WHERE　num > " + startIndex);
        System.out.println("sql:" + paginationSQL.toString());
        // 装入结果集
        //setResultList(getJdbcTemplate().query(paginationSQL.toString(), rowMapper));
        System.out.println(paginationSQL.toString());
        setResultList(getJdbcTemplate().queryForList(paginationSQL.toString()));
    }

    public void setStartIndex() {
        this.startIndex = (currentPage - 1) * pageSize;
    }

    // 计算结束时候的索引
    public void setLastIndex() {
        System.out.println("totalRows=" + totalRows);// /////////
        System.out.println("pageSize=" + pageSize);// /////////
        if (totalRows < pageSize) {
            this.lastIndex = totalRows;
        } else if ((totalRows % pageSize == 0)
                || (totalRows % pageSize != 0 && currentPage < totalPages)) {
            this.lastIndex = currentPage * pageSize;
        } else if (totalRows % pageSize != 0 && currentPage == totalPages) {//最后一页
            this.lastIndex = totalRows;
        }
    }

    public int getTotalPages() {
        return totalPages;
    }

    // 计算总页数
    public void setTotalPages() {
        if (totalRows % pageSize == 0) {
            this.totalPages = totalRows / pageSize;
        } else {
            this.totalPages = (totalRows / pageSize) + 1;
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public List<Map<String, Object>> getResultList() {
        return resultList;
    }

    public void setResultList(List<Map<String, Object>> resultList) {
        this.resultList = resultList;
    }
}
