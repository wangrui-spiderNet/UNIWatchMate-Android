package com.base.sdk.entity;

public class DialItem {
    String id;
    int using;
    String version;

    public DialItem(String id, int using, String version) {
        this.id = id;
        this.using = using;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUsing() {
        return using;
    }

    public void setUsing(int using) {
        this.using = using;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "DialItem{" +
                "id='" + id + '\'' +
                ", using=" + using +
                ", version='" + version + '\'' +
                '}';
    }
}