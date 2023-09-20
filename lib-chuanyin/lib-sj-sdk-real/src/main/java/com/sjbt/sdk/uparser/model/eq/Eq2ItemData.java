package com.sjbt.sdk.uparser.model.eq;

public class Eq2ItemData {
    private int pageId;
    private int type;
    private int itemIndex;
    private int extend;
    private float data;

    public Eq2ItemData(){
        this.pageId = 0;
        this.itemIndex = 0;
        this.type = 0;
        this.extend = 0;
        this.data = 0;
    }

    public Eq2ItemData(int pageId, int itemIndex){
        this.pageId = pageId;
        this.itemIndex = itemIndex;
        this.type = 0;
        this.extend = 0;
        this.data = 0;
    }

    public Eq2ItemData(int pageId, int itemIndex, float data){
        this.pageId = pageId;
        this.itemIndex = itemIndex;
        this.type = 0;
        this.extend = 0;
        this.data = data;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    public int getExtend() {
        return extend;
    }

    public void setExtend(int extend) {
        this.extend = extend;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }
}
