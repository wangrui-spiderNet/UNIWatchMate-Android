package com.sjbt.sdk.uparser.model;


public class UpPartitionInfo {
    public String tag;
    public int partitionLength;
    public int offsetInFlash;
    public byte[]  partitionData;

    public UpPartitionInfo() {
        tag = "";
        partitionLength = 0;
        offsetInFlash = 0;
        partitionData = null;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getPartitionLength() {
        return partitionLength;
    }

    public void setPartitionLength(int partitionLength) {
        this.partitionLength = partitionLength;
    }

    public int getOffsetInFlash() {
        return offsetInFlash;
    }

    public void setOffsetInFlash(int offsetInFlash) {
        this.offsetInFlash = offsetInFlash;
    }

    public byte[] getPartitionData() {
        return partitionData;
    }

    public void setPartitionData(byte[] partitionData) {
        this.partitionData = partitionData;
    }
}
