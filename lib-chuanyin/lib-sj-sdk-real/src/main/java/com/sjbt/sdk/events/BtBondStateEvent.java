package com.sjbt.sdk.events;

import android.bluetooth.BluetoothDevice;

public class BtBondStateEvent extends BtMessageEvent {

    private BluetoothDevice device;
    private int state;

    public BtBondStateEvent() {
    }

    public BtBondStateEvent(BluetoothDevice device) {
        this.device = device;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
