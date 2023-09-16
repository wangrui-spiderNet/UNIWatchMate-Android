package com.base.sdk.entity.settings

/**
 * Device information(设备信息)
 */
data class WmDeviceInfo(
    /**
     * device model(设备型号)
     */
    val model: String,
    /**
     * device mac address(设备mac地址)
     */
    val macAddress: String,
    /**
     * device version(设备版本)
     */
    val version: String,
    /**
     * device battery percentage(设备电量百分比)
     */
    val batteryPercentage: Int,
    /**
     * device id(设备id)
     */
    val deviceId: String,
    /**
     * bluetooth name(蓝牙名称)
     */
    val bluetoothName: String,
    /**
     * device name(设备名称)
     */
    val deviceName: String
) {
    override fun toString(): String {
        return "WmDeviceInfo(model='$model', macAddress='$macAddress', version='$version', batteryPercentage=$batteryPercentage, deviceId='$deviceId', bluetoothName='$bluetoothName', deviceName='$deviceName')"
    }
}