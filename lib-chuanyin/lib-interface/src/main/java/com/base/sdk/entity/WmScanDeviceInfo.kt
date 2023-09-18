package com.base.sdk.entity

class WmScanDeviceInfo(mode: WmDeviceMode?, address: String?) : WmDeviceInfo(mode!!, address!!) {
    var manufactureUrl: String? = null
}