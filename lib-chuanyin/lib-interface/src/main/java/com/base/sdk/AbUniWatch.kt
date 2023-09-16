package com.base.sdk

import android.bluetooth.BluetoothDevice
import android.content.Context
import com.base.sdk.entity.DeviceInfo
import com.base.sdk.entity.DeviceMode
import com.base.sdk.entity.ScanDeviceInfo
import com.base.sdk.`interface`.AbWmConnect
import com.base.sdk.`interface`.app.WmAbApps
import com.base.sdk.`interface`.setting.WmSettings
import com.base.sdk.`interface`.sync.AbWmSyncs
import io.reactivex.rxjava3.core.Observable

abstract class AbUniWatch {

    var wmSettings : WmSettings? = null
    var wmAbApps : WmAbApps? = null
    var wmSync : AbWmSyncs? = null
    var wmConnect : AbWmConnect? = null

    abstract fun init(context: Context?, msgTimeOut: Int)
    abstract fun scanQr(address: String): ScanDeviceInfo
    abstract fun getDeviceInfo(deviceMode: DeviceMode, address: String?): DeviceInfo?
    abstract fun startDiscovery(): Observable<BluetoothDevice>
    abstract fun stopDiscovery()

    abstract fun connect(address: String)

}