package com.base.sdk.entity

class ScanDeviceInfo(mode: DeviceMode?, address: String?) : DeviceInfo(mode!!, address!!) {
    var manufactureUrl: String? = null
}