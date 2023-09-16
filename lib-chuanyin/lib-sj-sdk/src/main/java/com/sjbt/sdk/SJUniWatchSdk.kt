package com.sjbt.sdk

import android.bluetooth.BluetoothDevice
import android.content.Context
import com.base.sdk.AbUniWatch
import com.base.sdk.entity.DeviceInfo
import com.base.sdk.entity.DeviceMode
import com.base.sdk.entity.ScanDeviceInfo
import com.sjbt.sdk.app.SJAbApps
import com.sjbt.sdk.settings.SJSettings
import com.sjbt.sdk.sync.SJSyncDatas
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
        wmAbApps = SJAbApps()
        wmSync = SJSyncDatas()
        wmConnect = SjConnect()
    }

    override fun scanQr(address: String): ScanDeviceInfo {
        TODO("Not yet implemented")
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

    override fun connect(address: String) {
        wmConnect?.connect(address)
    }

    override fun getDeviceInfo(deviceMode: DeviceMode, address: String?): DeviceInfo? {
        TODO("Not yet implemented")
    }

}