package com.sjbt.sdk.uparser.model.eq;

import java.util.ArrayList;

public class Eq2Data {
    // eq2 所有数据字节流, 暂时忽略。。。
    //private byte[] dataBuff;
    // eq 配置项的所有内容，第一层是多个配置模板，第二层是具体配置内容
    private ArrayList<ArrayList<Eq2ItemData>> eqItemList;
    // 额外数据
    private byte[] extraData;
    // 额外数据长度， in bytes.
    private int extraDataLength;


    public Eq2Data() {
        this.eqItemList = null;
        this.extraData = null;
        this.extraDataLength = 0;
    }

    public ArrayList<ArrayList<Eq2ItemData>> getEqItemList() {
        return eqItemList;
    }

    public void setEqItemList(ArrayList<ArrayList<Eq2ItemData>> eqItemList) {
        this.eqItemList = eqItemList;
    }

    public byte[] getExtraData() {
        return extraData;
    }

    public void setExtraData(byte[] extraData) {
        this.extraData = extraData;
    }

    public int getExtraDataLength() {
        return extraDataLength;
    }

    public void setExtraDataLength(int extraDataLength) {
        this.extraDataLength = extraDataLength;
    }


}
