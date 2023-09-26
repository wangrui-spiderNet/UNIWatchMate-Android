package com.sjbt.sdk

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import com.base.sdk.entity.WmDevice
import com.base.sdk.entity.WmDeviceModel
import com.base.sdk.entity.apps.WmConnectState
import com.base.sdk.`interface`.AbWmConnect
import com.base.sdk.`interface`.log.WmLog
import com.sjbt.sdk.log.SJLog
import com.sjbt.sdk.spp.bt.BtEngine
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter

class SJConnect(btEngine: BtEngine, mBtAdapter: BluetoothAdapter) : AbWmConnect() {

    var mConnectTryCount = 0
    private var connectEmitter: ObservableEmitter<WmConnectState>? = null
    private val TAG = TAG_SJ + "Connect"
    private var btEngine = btEngine
    private var mBtAdapter = mBtAdapter
    var mBindInfo: BindInfo? = null

    /**
     * 通过address 连接
     */
    override fun connect(address: String, bindInfo: BindInfo, deviceMode: WmDeviceModel): WmDevice {
        val device = WmDevice(deviceMode)
        device.address = address
        device.mode = deviceMode
        mBindInfo = bindInfo
        device.isRecognized = deviceMode == WmDeviceModel.SJ_WATCH

        if (device.isRecognized) {
            SJLog.logBt(TAG, " connect:${address}")
            connectEmitter?.onNext(WmConnectState.CONNECTING)

            try {
                val bluetoothDevice: BluetoothDevice = mBtAdapter.getRemoteDevice(address)
                btEngine.connect(bluetoothDevice)
            } catch (e: Exception) {
                e.printStackTrace()
                connectEmitter?.onNext(WmConnectState.DISCONNECTED)
            }
        } else {
            connectEmitter?.onNext(WmConnectState.DISCONNECTED)
        }

        return device
    }

    /**
     * 通过BluetoothDevice 连接
     */
    override fun connect(
        bluetoothDevice: BluetoothDevice,
        bindInfo: BindInfo,
        deviceMode: WmDeviceModel
    ): WmDevice {
        val wmDevice = WmDevice(deviceMode)
        wmDevice.address = bluetoothDevice.address
        wmDevice.isRecognized = deviceMode == WmDeviceModel.SJ_WATCH

        if (wmDevice.isRecognized) {
            WmLog.e(TAG, " connect:${wmDevice}")
            connectEmitter?.onNext(WmConnectState.CONNECTING)
            btEngine.connect(bluetoothDevice)
        } else {
            connectEmitter?.onError(RuntimeException("not recognized device"))
        }

        return wmDevice
    }

    /**
     * 重连
     */
    fun reConnect(device: BluetoothDevice){
        mBindInfo?.let {
            connect(device, it, WmDeviceModel.SJ_WATCH)
        }
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

    override val observeConnectState: Observable<WmConnectState> = Observable.create {
        connectEmitter = it
    }

    override fun getConnectState(): WmConnectState {
        TODO("Not yet implemented")
    }

}