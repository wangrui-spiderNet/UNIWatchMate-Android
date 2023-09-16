package com.base.sdk.entity

open class DeviceInfo(var mode: DeviceMode, var address: String) {
    var name: String? = null
    var isRecognized = false

    override fun toString(): String {
        return "DeviceInfo{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", mode=" + mode +
                '}'
    }
}