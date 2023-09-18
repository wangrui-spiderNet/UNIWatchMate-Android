package com.base.sdk

import android.bluetooth.BluetoothDevice
import android.content.Context
import com.base.sdk.entity.WmDeviceInfo
import com.base.sdk.entity.WmDeviceMode
import com.base.sdk.entity.WmScanDeviceInfo
import com.base.sdk.`interface`.AbWmConnect
import com.base.sdk.`interface`.WmTransferFile
import com.base.sdk.`interface`.app.AbWmApps
import com.base.sdk.`interface`.setting.AbWmSettings
import com.base.sdk.`interface`.sync.AbWmSyncs
import io.reactivex.rxjava3.core.Observable

/**
 * sdk接口抽象类
 * 封装了几大功能模块
 * sdk需要实现此接口，并实现每一个功能模块下的功能
 * 拿到此接口实例即可操作sdk实现的所有功能
 *
 * sdk interface abstract class
 * Encapsulates several major functional modules
 * The sdk implementation class needs to implement this interface and implement the functions under each functional module
 * App can operate all functions implemented by sdk after getting an instance of this interface implementation class.
 */
abstract class AbUniWatch {

    /**
     * 设置模块
     */
    var abWmSettings : AbWmSettings? = null

    /**
     * A应用模块
     */
    var abWmApps : AbWmApps? = null

    /**
     * 同步模块
     */
    var wmSync : AbWmSyncs? = null

    /**
     * 连接模块
     */
    var wmConnect : AbWmConnect? = null

    /**
     * 文件传输
     */
    var wmTransferFile : WmTransferFile? = null

    /**
     * 初始化方法，需要在所有方法执行前调用,建议在application执行
     */
    abstract fun init(context: Context?, msgTimeOut: Int)

    /**
     * 扫描二维码连接
     */
    abstract fun scanQr(qrString: String): WmScanDeviceInfo

    /**
     * 根据设备类型返回设备信息
     */
    abstract fun getDeviceInfo(wmDeviceMode: WmDeviceMode, address: String?): WmDeviceInfo?

    /**
     * 开始扫描设备
     */
    abstract fun startDiscovery(): Observable<BluetoothDevice>

    /**
     * 停止扫描设备
     */
    abstract fun stopDiscovery()

    /**
     * 连接方法
     */
    abstract fun connect(address: String)

}