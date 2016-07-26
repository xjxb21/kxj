package com.bigvideo.kxj.message;

/**
 * Created by xiao on 2016/7/21.
 */
public class OperMessage {

    String status;
    String message;

    public OperMessage() {
    }

    public OperMessage(String status, String message) {
        this.status = status;
        this.message = message;
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
}
