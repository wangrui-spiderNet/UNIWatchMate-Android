package com.base.sdk

import android.app.Application
import android.bluetooth.BluetoothDevice
import com.base.sdk.`interface`.AbWmConnect
import com.base.sdk.`interface`.WmTransferFile
import com.base.sdk.`interface`.app.AbWmApps
import com.base.sdk.`interface`.log.WmLog
import com.base.sdk.`interface`.setting.AbWmSettings
import com.base.sdk.`interface`.sync.AbWmSyncs
import com.base.sdk.entity.WmDevice
import com.base.sdk.entity.WmDeviceModel
import com.base.sdk.entity.WmScanDevice
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
   abstract val wmSettings: AbWmSettings

    /**
     * A应用模块
     */
    abstract val wmApps: AbWmApps

    /**
     * 同步模块
     */
    abstract val wmSync: AbWmSyncs

    /**
     * 连接模块
     */
    abstract val wmConnect: AbWmConnect

    /**
     * 文件传输
     */
    abstract val wmTransferFile: WmTransferFile

    /**
     * 初始化方法，需要在所有方法执行前调用,建议在application执行
     */
    abstract fun init(application: Application, msgTimeOut: Int)

    /**
     * 根据设备类型返回设备信息
     */
    abstract fun getDevice(wmDeviceModel: WmDeviceModel): WmDevice?

    /**
     * 开始扫描设备
     */
    abstract fun startDiscovery(): Observable<BluetoothDevice>

    /**
     * 停止扫描设备
     */
    abstract fun stopDiscovery()

    fun setLogEnable(logEnable: Boolean) {
        WmLog.logEnable = logEnable
    }

    abstract fun parseScanQr(qrString: String): WmScanDevice

}