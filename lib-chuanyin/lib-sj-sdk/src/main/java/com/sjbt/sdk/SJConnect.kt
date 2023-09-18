package com.sjbt.sdk

import android.bluetooth.BluetoothDevice
import android.text.TextUtils
import com.base.sdk.entity.WmDevice
import com.base.sdk.entity.WmDeviceMode
import com.base.sdk.entity.WmScanDevice
import com.base.sdk.entity.apps.WmConnectState
import com.base.sdk.`interface`.AbWmConnect
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Single

class SJConnect : AbWmConnect() {

    private var connectEmitter: ObservableEmitter<WmConnectState>? = null

    /**
     * 通过address 连接
     */
    override fun connect(address: String, deviceMode: WmDeviceMode): WmDevice {
        val device = WmDevice(deviceMode, address)
        device.address = address
        device.mode = deviceMode

        device.isRecognized = deviceMode == WmDeviceMode.SJ_WATCH

        if (device.isRecognized) {
            //TODO 执行连接

        } else {
            connectEmitter?.onError(RuntimeException("not recognized device"))
        }

        return device
    }

    /**
     * 通过BluetoothDevice 连接
     */
    override fun connect(address: BluetoothDevice, deviceMode: WmDeviceMode): WmDevice {
        val device = WmDevice(deviceMode, address.address)
        device.isRecognized = deviceMode == WmDeviceMode.SJ_WATCH

        if (device.isRecognized) {
            //TODO 执行连接

        } else {
            connectEmitter?.onError(RuntimeException("not recognized device"))
        }

        return device
    }

    /**
     * 扫码连接 需要sdk自己实现解析二维码识别自家设备的逻辑
     */
    override fun scanQr(qrString: String): WmScanDevice {
        val address = parseAddress(qrString)
        val scanDeviceInfo = WmScanDevice(WmDeviceMode.SJ_WATCH, address)
        scanDeviceInfo.qrUrl = qrString
        if (qrString.contains("shenjun")) {
            scanDeviceInfo.isRecognized = true;
        }

        return scanDeviceInfo
    }

    override fun disconnect() {
        connectEmitter?.onNext(WmConnectState.DISCONNECTED)
    }

    override fun reset() {
        connectEmitter?.onNext(WmConnectState.DISCONNECTED)
    }

    override fun observeConnectState(): Observable<WmConnectState> {
        return Observable.create<WmConnectState>(object : ObservableOnSubscribe<WmConnectState> {
            @Throws(Throwable::class)
            override fun subscribe(emitter: ObservableEmitter<WmConnectState>) {
                connectEmitter = emitter
            }
        })
    }

    /**
     * 通过扫描到的二维码，解析mac地址
     */
    private fun parseAddress(qrString: String): String {
        val address = qrString.substring(0, 12)
        return address
    }
}