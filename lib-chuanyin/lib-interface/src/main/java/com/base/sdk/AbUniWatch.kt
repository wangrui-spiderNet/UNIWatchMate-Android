package com.base.sdk

import android.bluetooth.BluetoothDevice
import android.content.Context
import com.base.sdk.entity.WmDeviceInfo
import com.base.sdk.entity.WmDeviceMode
import com.base.sdk.entity.WmScanDeviceInfo
import com.base.sdk.`interface`.AbWmConnect
import com.base.sdk.`interface`.WmTransferFile
import com.base.sdk.`interface`.app.AbWmApps
import com.base.sdk.`interface`.setting.AbWmSettings
import com.base.sdk.`interface`.sync.AbWmSyncs
import io.reactivex.rxjava3.core.Observable

abstract class AbUniWatch {

    var abWmSettings : AbWmSettings? = null
    var abWmApps : AbWmApps? = null
    var wmSync : AbWmSyncs? = null
    var wmConnect : AbWmConnect? = null
    var wmTransferFile : WmTransferFile? = null

    abstract fun init(context: Context?, msgTimeOut: Int)
    abstract fun scanQr(address: String): WmScanDeviceInfo
    abstract fun getDeviceInfo(wmDeviceMode: WmDeviceMode, address: String?): WmDeviceInfo?
    abstract fun startDiscovery(): Observable<BluetoothDevice>
    abstract fun stopDiscovery()

    abstract fun connect(address: String)

}