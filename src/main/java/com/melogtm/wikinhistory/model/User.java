package com.melogtm.wikinhistory.model;

public class User {
    private Integer day;
    private Integer month;
    private String eventType;

    public User() {}

    public User(Integer day, Integer month, String eventType) {
        this.day = day;
        this.month = month;
        this.eventType = eventType;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "User{" +
                "day=" + day +
                ", month=" + month +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
