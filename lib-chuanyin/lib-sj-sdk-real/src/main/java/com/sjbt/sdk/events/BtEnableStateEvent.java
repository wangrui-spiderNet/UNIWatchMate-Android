package com.sjbt.sdk.events;


public class BtEnableStateEvent extends BtMessageEvent {
    private boolean enable;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
