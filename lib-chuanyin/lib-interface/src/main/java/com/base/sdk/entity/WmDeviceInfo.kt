package com.base.sdk.entity

open class WmDeviceInfo(var mode: WmDeviceMode, var address: String) {
    var name: String? = null
    var isRecognized = false

    override fun toString(): String {
        return "WmDeviceInfo{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", mode=" + mode +
                '}'
    }
}