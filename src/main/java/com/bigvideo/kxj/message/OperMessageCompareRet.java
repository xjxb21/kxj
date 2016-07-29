package com.bigvideo.kxj.message;

import com.bigvideo.kxj.entity.BigPerson;

/**
 * Created by xiao on 2016/7/21.
 */
public class OperMessageCompareRet extends OperMessage {

    private BigPerson person;
    private double MaxScore;   //对比最高比分值

    public OperMessageCompareRet(String status, String message, BigPerson person) {
        super(status, message);
        this.person = person;
    }

    public BigPerson getPerson() {
        return person;
    }

    public void setPerson(BigPerson person) {
        this.person = person;
    }

    public double getMaxScore() {
        return MaxScore;
    }

    public void setMaxScore(double maxScore) {
        MaxScore = maxScore;
    }
}
