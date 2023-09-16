package com.base.sdk.entity;

public class SleepRecordBean {

    private int hour;
    private int minute;
    private int state;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "SleepRecordBean{" +
                "hour=" + hour +
                ", minute=" + minute +
                ", state=" + state +
                '}';
    }
}