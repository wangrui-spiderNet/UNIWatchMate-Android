package com.base.api

import android.app.Application
import com.base.sdk.AbUniWatch
import com.base.sdk.`interface`.AbWmConnect
import com.base.sdk.`interface`.WmTransferFile
import com.base.sdk.`interface`.app.AbWmApps
import com.base.sdk.`interface`.setting.AbWmSettings
import com.base.sdk.`interface`.sync.AbWmSyncs
import com.base.sdk.entity.WmDeviceModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

object UNIWatchMate {
    private lateinit var application: Application
    private val mBaseUNIWatches: MutableList<AbUniWatch> = ArrayList()
    var uniWatchSdk: Observable<AbUniWatch>? = null
    var mInstance: AbUniWatch? = null
    private var mMsgTimeOut = 10000

    var mWmConnect: AbWmConnect? = null
    var mWmSettings: AbWmSettings? = null
    var mWmTransferFile: WmTransferFile? = null
    var mWmApps: AbWmApps? = null
    var mWmSyncs: AbWmSyncs? = null

    private val watchSubject = BehaviorSubject.create<AbUniWatch>()
    private val watchObservable = BehaviorObservable<AbUniWatch>(watchSubject)

//    val wmConnect: AbWmConnect = AbWmConnectDelegate(watchSubject)
//    var wmSettings: AbWmSettings = AbWmSettingsDelegate(watchObservable)

    fun init(application: Application, msgTimeOut: Int, supportSdks: Array<AbUniWatch>) {
        if (this::application.isInitialized) {
            return
        }
        this.application = application
        mBaseUNIWatches.clear()

        uniWatchSdk = watchSubject
        mMsgTimeOut = if (mMsgTimeOut < 5) {
            5
        } else {
            msgTimeOut
        }

        for (i in supportSdks.indices) {
            supportSdks[i].init(application, mMsgTimeOut)
            mBaseUNIWatches.add(supportSdks[i])
        }

        if (uniWatchSdk == null && mBaseUNIWatches.isEmpty()) {
            throw RuntimeException("No Sdk Register Exception!")
        }
    }

    fun setDeviceModel(deviceMode: WmDeviceModel) {
        mBaseUNIWatches.forEach {
            if (it.getDevice(deviceMode)?.isRecognized == true) {
                mWmConnect = it.wmConnect
                mWmSettings = it.wmSettings
                mWmApps = it.wmApps
                mWmSyncs = it.wmSync
                mWmTransferFile = it.wmTransferFile

                watchSubject.onNext(it)
                mInstance = it
            }
        }
    }

    fun scanQr(qrString: String) {
        mBaseUNIWatches.forEach {
            val scanDevice = it.parseScanQr(qrString)
            scanDevice?.let { device ->
                if (device.isRecognized) {
                    mWmApps = it.wmApps
                    mWmSyncs = it.wmSync
                    mWmTransferFile = it.wmTransferFile

                    watchSubject.onNext(it)
                    mInstance = it

                    mWmConnect?.connect(device.address!!, device.mode)
                }
            }
        }
    }

//    fun connect(address: String, deviceMode: WmDeviceModel) {
//        mBaseUNIWatches.forEach {
//            if (it.wmConnect?.connect(address, deviceMode)?.isRecognized == true) {
//                sdkSubject.onNext(it)
//            }
//        }
//    }
//
//    fun connect(address: BluetoothDevice, deviceMode: WmDeviceModel) {
//        mBaseUNIWatches.forEach {
//            if (it.wmConnect?.connect(address, deviceMode)?.isRecognized == true) {
//                sdkSubject.onNext(it)
//            }
//        }
//    }
//
//    fun scanQr(scanQr: String) {
//        mBaseUNIWatches.forEach {
//            if (it.wmConnect?.scanQr(scanQr)?.isRecognized == true) {
//                sdkSubject.onNext(it)
//            }
//        }
//    }

}