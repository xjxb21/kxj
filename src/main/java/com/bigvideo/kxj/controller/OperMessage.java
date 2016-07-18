package com.bigvideo.kxj.controller;

/**
 * Created by xiao on 2016/7/17.
 */
public class OperMessage {

    String status;
    String message;
    Integer pid;

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public OperMessage() {

    }

    public OperMessage(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public OperMessage(String status, String message, Integer pid) {
        this.status = status;
        this.message = message;
        this.pid = pid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getPid() {
        return pid;
    }
}
