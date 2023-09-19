package com.base.sdk.entity

open class WmDevice(var mode: WmDeviceModel) {
    var name: String? = null
    var address: String? = null
    var isRecognized = false
    override fun toString(): String {
        return "WmDevice(mode=$mode, address='$address', name=$name, isRecognized=$isRecognized)"
    }

}