package com.base.sdk.entity

open class WmDevice(var mode: WmDeviceMode, var address: String) {
    var name: String? = null
    var isRecognized = false
    override fun toString(): String {
        return "WmDevice(mode=$mode, address='$address', name=$name, isRecognized=$isRecognized)"
    }

}