package com.base.sdk.entity

/**
 * 扫描二维码返回设备信息数据结构
 */
class WmScanDevice(mode: WmDeviceMode?, address: String?) : WmDevice(mode!!, address!!) {
    var qrUrl: String? = null
}