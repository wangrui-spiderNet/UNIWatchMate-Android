package com.sjbt.sdk

import android.bluetooth.BluetoothDevice
import com.base.sdk.entity.WmDevice
import com.base.sdk.entity.WmDeviceModel
import com.base.sdk.entity.apps.WmConnectState
import com.base.sdk.`interface`.AbWmConnect
import com.base.sdk.`interface`.log.WmLog
import com.sjbt.sdk.log.SJLog
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe

class SJConnect : AbWmConnect() {

    var mConnectTryCount = 0

    private var connectEmitter: ObservableEmitter<WmConnectState>? = null
    private val TAG = TAG_SJ + "Connect"

    /**
     * 通过address 连接
     */
    override fun connect(address: String, deviceMode: WmDeviceModel): WmDevice {
        val device = WmDevice(deviceMode)
        device.address = address
        device.mode = deviceMode

        device.isRecognized = deviceMode == WmDeviceModel.SJ_WATCH

        if (device.isRecognized) {
            SJLog.logBt(TAG, " connect:${address}")
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
            WmLog.e(TAG, " connect:${device}")
            connectEmitter?.onNext(WmConnectState.CONNECTING)
            connectEmitter?.onNext(WmConnectState.PRE_CONNECTED)
        } else {
            connectEmitter?.onError(RuntimeException("not recognized device"))
        }

        return device
    }

    fun btStateChange(state: WmConnectState) {
        connectEmitter?.onNext(state)
    }

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

}