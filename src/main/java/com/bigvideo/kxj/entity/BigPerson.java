package com.bigvideo.kxj.entity;

/**
 * 科学家
 */
public class BigPerson {

    Integer personId;
    String name;
    String history;

    public BigPerson(){

    }

    public BigPerson(Integer personId, String name, String history) {
        this.personId = personId;
        this.name = name;
        this.history = history;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}
