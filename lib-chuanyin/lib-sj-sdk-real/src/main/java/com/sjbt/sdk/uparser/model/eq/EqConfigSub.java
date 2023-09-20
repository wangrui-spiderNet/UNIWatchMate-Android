package com.sjbt.sdk.uparser.model.eq;

import java.util.Map;
import java.util.TreeMap;

public class EqConfigSub {
    private int pageTypeId;
    private float compensationGain;//补偿增益
    private Map<Integer,EqConfigPoint> pointSettingMap = new TreeMap<>();//14点的数组或者List

    public float getCompensationGain() {
        return compensationGain;
    }

    public void setCompensationGain(float compensationGain) {
        this.compensationGain = compensationGain;
    }


    public int getPageTypeId() {
        return pageTypeId;
    }

    public void setPageTypeId(int pageTypeId) {
        this.pageTypeId = pageTypeId;
    }

    public Map<Integer, EqConfigPoint> getPointSettingMap() {
        return pointSettingMap;
    }

    public void setPointSettingMap(Map<Integer, EqConfigPoint> pointSettingMap) {
        this.pointSettingMap = pointSettingMap;
    }

    @Override
    public String toString() {
        return "EqConfigSub{" +
                "pageTypeId=" + pageTypeId +
                ", compensationGain=" + compensationGain +
                ", pointSettingMap=" + pointSettingMap +
                '}';
    }
}
