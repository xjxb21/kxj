package com.bigvideo.kxj.controller;

/**
 * Created by xiao on 2016/7/6.
 */

public class Msg {

    private int status;
    private String msgbody;

    public Msg(int status, String msgbody) {
        this.status = status;
        this.msgbody = msgbody;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsgbody() {
        return msgbody;
    }

    public void setMsgbody(String msgbody) {
        this.msgbody = msgbody;
    }
}
