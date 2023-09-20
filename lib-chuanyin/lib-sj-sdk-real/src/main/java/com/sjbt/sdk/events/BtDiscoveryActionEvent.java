package com.sjbt.sdk.events;

public class BtDiscoveryActionEvent extends BtMessageEvent {
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public BtDiscoveryActionEvent(int state) {
        this.state = state;
    }
}
