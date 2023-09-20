package com.sjbt.sdk.spp;

import static android.bluetooth.BluetoothDevice.BOND_BONDED;
import static android.bluetooth.BluetoothDevice.BOND_BONDING;
import static android.bluetooth.BluetoothDevice.BOND_NONE;
import static com.sjbt.sdk.BTConfig.CONNECT_STATE_CONNECTED;
import static com.sjbt.sdk.BTConfig.CONNECT_STATE_DISCONNECTED;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.base.sdk.action.OnBtStateChangeListener;
import com.sjbt.sdk.events.BtBondStateEvent;
import com.sjbt.sdk.events.BtConnectStateEvent;
import com.sjbt.sdk.events.BtDiscoveryActionEvent;
import com.sjbt.sdk.events.BtDiscoveryDeviceEvent;
import com.sjbt.sdk.events.BtEnableStateEvent;
import com.sjbt.sdk.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * 监听蓝牙广播-各种状态
 */
@SuppressLint("MissingPermission")
public class BtStateReceiver extends BroadcastReceiver {

    private OnBtStateChangeListener mOnBtStateChangeListener;
    private String mCurrAddress;

    private BluetoothSocket mSocket;

    public BtStateReceiver(Context cxt, OnBtStateChangeListener onBtStateChangeListener) {
        this.mOnBtStateChangeListener = onBtStateChangeListener;
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);//蓝牙开关状态
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);//蓝牙开始搜索
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);//蓝牙搜索结束

        filter.addAction(BluetoothDevice.ACTION_FOUND);//蓝牙发现新设备(未配对的设备)
        filter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);//在系统弹出配对框之前(确认/输入配对码)
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);//设备配对状态改变

        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);//最底层连接建立
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);//最底层连接断开

        filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED); //BluetoothAdapter连接状态
        filter.addAction(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED); //BluetoothHeadset连接状态
        filter.addAction(BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED); //BluetoothA2dp连接状态

        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);

        cxt.registerReceiver(this, filter);
    }

    public void setmCurrDevice(String address) {
        mCurrAddress = address;
    }

    public void setmSocket(BluetoothSocket mSocket) {
        this.mSocket = mSocket;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null)
            return;

        switch (action) {
            case BluetoothAdapter.ACTION_STATE_CHANGED:

                int status = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);

                BtEnableStateEvent enableStateEvent = new BtEnableStateEvent();

                if (status == BluetoothAdapter.STATE_ON) {
                    enableStateEvent.setEnable(true);
                    LogUtils.logBlueTooth("系统蓝牙已开启:" + status);
                    if (mOnBtStateChangeListener != null) {
                        mOnBtStateChangeListener.onClassicBtOpen();
                    }

                } else if (status == BluetoothAdapter.STATE_OFF) {
                    LogUtils.logBlueTooth("系统蓝牙已关闭:" + status);
                    enableStateEvent.setEnable(false);
                    if (mOnBtStateChangeListener != null) {
                        mOnBtStateChangeListener.onClassicBtDisabled();
                    }
                }

                EventBus.getDefault().post(enableStateEvent);

                break;
            case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                LogUtils.logBlueTooth("开始扫描");

                BtDiscoveryActionEvent discoveryEventStart = new BtDiscoveryActionEvent(1);
                EventBus.getDefault().post(discoveryEventStart);

                break;
            case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                LogUtils.logBlueTooth("结束扫描");
                BtDiscoveryActionEvent discoveryEventEnd = new BtDiscoveryActionEvent(2);
                EventBus.getDefault().post(discoveryEventEnd);

                break;

            case BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED:
            case BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED:
            case BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED:
                LogUtils.logBlueTooth("ACTION_CONNECTION_STATE_CHANGED");

                break;

            case BluetoothDevice.ACTION_FOUND:
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (!TextUtils.isEmpty(device.getName())) {

                    if (mOnBtStateChangeListener != null) {
                        mOnBtStateChangeListener.onDiscoveryDevice(device);
                    }

                    BtDiscoveryDeviceEvent discoveryEvent = new BtDiscoveryDeviceEvent();
                    discoveryEvent.setBluetoothDevice(device);
                    EventBus.getDefault().post(discoveryEvent);
                }

                break;
            case BluetoothDevice.ACTION_PAIRING_REQUEST:
                BluetoothDevice pairing_device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                LogUtils.logBlueTooth("配请求对中...：" + pairing_device.getName());

                break;

            case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
                BtBondStateEvent bondStateEvent = new BtBondStateEvent();
                BluetoothDevice bond_device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                bondStateEvent.setDevice(bond_device);
                switch (bond_device.getBondState()) {
                    case BOND_NONE:
                        LogUtils.logBlueTooth("配对取消：" + bond_device.getName());
                        bondStateEvent.setState(BOND_NONE);
                        break;

                    case BOND_BONDED:
                        LogUtils.logBlueTooth("配对成功：" + bond_device.getName());
                        bondStateEvent.setState(BOND_BONDED);
                        break;

                    case BOND_BONDING:
                        LogUtils.logBlueTooth("配对中：" + bond_device.getName());
                        bondStateEvent.setState(BOND_BONDING);
                        break;
                }

                EventBus.getDefault().post(bondStateEvent);

                if (mOnBtStateChangeListener != null) {
                    mOnBtStateChangeListener.onBindState(bond_device, bondStateEvent.getState());
                }

                break;

            case BluetoothDevice.ACTION_ACL_CONNECTED:
                LogUtils.logBlueTooth("ACTION_ACL_CONNECTED");

                BluetoothDevice connect_device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (connect_device.getType() == BluetoothDevice.DEVICE_TYPE_LE) {
                    return;
                }

                BtConnectStateEvent connectStateEvent = new BtConnectStateEvent();
                connectStateEvent.setDevice(connect_device);
                connectStateEvent.setState(CONNECT_STATE_CONNECTED);
                EventBus.getDefault().post(connectStateEvent);

                if (mOnBtStateChangeListener != null) {
                    mOnBtStateChangeListener.onClassicBtConnect(connect_device);
                }

                break;
            case BluetoothDevice.ACTION_ACL_DISCONNECTED:

                BluetoothDevice dis_connect_device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                LogUtils.logBlueTooth("ACTION_ACL_DISCONNECTED 当前的设备 address:" + mCurrAddress);
                LogUtils.logBlueTooth("ACTION_ACL_DISCONNECTED name:" + dis_connect_device.getName() + " address:" + dis_connect_device.getAddress());

                if (dis_connect_device.getType() == BluetoothDevice.DEVICE_TYPE_LE) return;

                if (!TextUtils.isEmpty(mCurrAddress) && dis_connect_device.getAddress().equals(mCurrAddress)) {
                    try {
                        if (mSocket != null) {
                            byte[] b = new byte[1];
                            mSocket.getOutputStream().write(b);
                            mSocket.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    BtConnectStateEvent disConnectStateEvent = new BtConnectStateEvent();
                    disConnectStateEvent.setDevice(dis_connect_device);
                    disConnectStateEvent.setState(CONNECT_STATE_DISCONNECTED);
                    EventBus.getDefault().post(disConnectStateEvent);

                    if (mOnBtStateChangeListener != null) {
                        mOnBtStateChangeListener.onClassicBtDisConnect(dis_connect_device);
                    }
                }

                break;

        }
    }

}