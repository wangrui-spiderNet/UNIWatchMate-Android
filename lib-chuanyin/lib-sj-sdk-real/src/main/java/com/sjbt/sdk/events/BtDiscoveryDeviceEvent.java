package com.sjbt.sdk.events;

import android.bluetooth.BluetoothDevice;

public class BtDiscoveryDeviceEvent extends BtMessageEvent {
    private BluetoothDevice bluetoothDevice;

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }
}
