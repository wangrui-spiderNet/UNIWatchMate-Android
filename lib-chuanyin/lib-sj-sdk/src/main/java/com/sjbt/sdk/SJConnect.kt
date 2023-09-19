package com.sjbt.sdk

import android.bluetooth.BluetoothDevice
import com.base.sdk.entity.WmDevice
import com.base.sdk.entity.WmDeviceModel
import com.base.sdk.entity.WmScanDevice
import com.base.sdk.entity.apps.WmConnectState
import com.base.sdk.`interface`.AbWmConnect
import com.base.sdk.`interface`.log.WmLog
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe

class SJConnect : AbWmConnect() {

    private var connectEmitter: ObservableEmitter<WmConnectState>? = null

    val TAG = "SJConnect"

    /**
     * 通过address 连接
     */
    override fun connect(address: String, deviceMode: WmDeviceModel): WmDevice {
        val device = WmDevice(deviceMode)
        device.address = address
        device.mode = deviceMode

        device.isRecognized = deviceMode == WmDeviceModel.SJ_WATCH

        if (device.isRecognized) {
            WmLog.e(TAG, " connect:${address}")
            connectEmitter?.onNext(WmConnectState.CONNECTING)
            connectEmitter?.onNext(WmConnectState.PRE_CONNECTED)
        } else {
            connectEmitter?.onError(RuntimeException("not recognized device"))
        }

        return device
    }

    /**
     * 通过BluetoothDevice 连接
     */
    override fun connect(device: BluetoothDevice, deviceMode: WmDeviceModel): WmDevice {
        val device = WmDevice(deviceMode)
        device.address = device.address
        device.isRecognized = deviceMode == WmDeviceModel.SJ_WATCH

        if (device.isRecognized) {
            //TODO 执行连接
            WmLog.e(TAG, " connect:${device}")
            connectEmitter?.onNext(WmConnectState.CONNECTING)
            connectEmitter?.onNext(WmConnectState.PRE_CONNECTED)
        } else {
            connectEmitter?.onError(RuntimeException("not recognized device"))
        }

        return device
    }

    /**
     * 扫码连接 需要sdk自己实现解析二维码识别自家设备的逻辑
     */
//    override fun scanQr(qrString: String): WmScanDevice {
//        val address = parseAddress(qrString)
//        val scanDevice = WmScanDevice(WmDeviceModel.SJ_WATCH)
//        scanDevice.address = address
//        scanDevice.qrUrl = qrString
//        if (qrString.contains("shenju")) {
//            scanDevice.isRecognized = true
//        }
//
//        if (scanDevice.isRecognized) {
//            //TODO 执行连接
//            WmLog.e(TAG, "scan connect:${address}")
//            connectEmitter?.onNext(WmConnectState.CONNECTING)
//            connectEmitter?.onNext(WmConnectState.PRE_CONNECTED)
//        } else {
//            connectEmitter?.onError(RuntimeException("not recognized device"))
//        }
//
//        return scanDevice
//    }

    override fun disconnect() {
        connectEmitter?.onNext(WmConnectState.DISCONNECTED)
    }

    override fun reset() {
        connectEmitter?.onNext(WmConnectState.DISCONNECTED)
    }

    override var observeConnectState: Observable<WmConnectState>
        get() = Observable.create(object : ObservableOnSubscribe<WmConnectState> {
            @Throws(Throwable::class)
            override fun subscribe(emitter: ObservableEmitter<WmConnectState>) {
                connectEmitter = emitter
            }
        })
        set(value) {}

    /**
     * 通过扫描到的二维码，解析mac地址
     */
    private fun parseAddress(qrString: String): String {
        val address = qrString.substring(0, 12)
        return address
    }
}