package com.base.sdk.entity;
import java.util.Objects;

public class AlarmBean {
    public int open;
    public int hour;
    public int min;
    public int repeat;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlarmBean)) return false;
        AlarmBean alarmBean = (AlarmBean) o;
        return hour == alarmBean.hour && min == alarmBean.min && repeat == alarmBean.repeat && open == alarmBean.open;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, min, repeat, open);
    }

    @Override
    public String toString() {
        return "AlarmBean{" +
                "open=" + open +
                ", hour=" + hour +
                ", min=" + min +
                ", repeat=" + repeat +
                '}';
    }
}
