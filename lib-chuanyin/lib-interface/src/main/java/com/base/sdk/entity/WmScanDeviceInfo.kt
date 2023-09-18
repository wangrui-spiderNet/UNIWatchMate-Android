package com.base.sdk.entity

/**
 * 扫描二维码返回设备信息数据结构
 */
class WmScanDeviceInfo(mode: WmDeviceMode?, address: String?) : WmDeviceInfo(mode!!, address!!) {
    var manufactureUrl: String? = null
}