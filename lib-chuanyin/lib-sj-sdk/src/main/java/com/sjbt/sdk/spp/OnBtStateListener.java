package com.sjbt.sdk.spp;

import android.bluetooth.BluetoothDevice;

public interface OnBtStateListener {
    void onClassicBtDisConnect(BluetoothDevice device);

    void onClassicBtConnect(BluetoothDevice device);

    void onClassicBtDisabled();

    void onClassicBtOpen();

    void onBindState(BluetoothDevice device, int bondState);

    void onStartDiscovery();
    void onStopDiscovery();
    void onDiscoveryDevice(BluetoothDevice device);
}
