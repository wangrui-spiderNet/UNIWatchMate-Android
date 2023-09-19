package com.sjbt.sdk

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.text.TextUtils
import com.base.sdk.AbUniWatch
import com.base.sdk.entity.WmDevice
import com.base.sdk.entity.WmDeviceModel
import com.base.sdk.entity.WmScanDevice
import com.sjbt.sdk.app.SJAbWmApps
import com.sjbt.sdk.dfu.SJTransferFile
import com.sjbt.sdk.settings.SJSettings
import com.sjbt.sdk.sync.SJSyncData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe

object SJUniWatchSdk : AbUniWatch() {

    private var context: Context? = null
    private var msgTimeOut: Int? = null

    override fun init(context: Context?, msgTimeOut: Int) {
        this.context = context
        this.msgTimeOut = msgTimeOut
        wmSettings = SJSettings()
        wmApps = SJAbWmApps()
        wmSync = SJSyncData()
        wmConnect = SJConnect()
        wmTransferFile = SJTransferFile()
    }

    override fun startDiscovery(): Observable<BluetoothDevice> {
        return Observable.create(object : ObservableOnSubscribe<BluetoothDevice> {
            override fun subscribe(emitter: ObservableEmitter<BluetoothDevice>) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun stopDiscovery() {
        TODO("Not yet implemented")
    }

    override fun getDevice(wmDeviceModel: WmDeviceModel): WmDevice? {
        val wmDevice = WmDevice(WmDeviceModel.SJ_WATCH)
        wmDevice.isRecognized = wmDeviceModel == WmDeviceModel.SJ_WATCH
        return wmDevice
    }

    override fun parseScanQr(qrString: String): WmScanDevice {
        val wmScanDevice = WmScanDevice(WmDeviceModel.SJ_WATCH)
        wmScanDevice.qrUrl = qrString
        wmScanDevice.address = qrString.substring(0, 12)

        wmScanDevice.isRecognized =
            qrString.contains("shenju") && isLegalMacAddress(wmScanDevice.address)

        return wmScanDevice
    }

    private fun isLegalMacAddress(address: String?): Boolean {
        return TextUtils.isEmpty(address)
    }
}