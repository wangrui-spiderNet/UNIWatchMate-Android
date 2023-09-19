package com.base.api

import android.content.Context
import com.base.sdk.*
import com.base.sdk.entity.WmDeviceModel
import com.base.sdk.entity.WmScanDevice
import com.base.sdk.`interface`.AbWmConnect
import com.base.sdk.`interface`.WmTransferFile
import com.base.sdk.`interface`.app.AbWmApps
import com.base.sdk.`interface`.setting.AbWmSettings
import com.base.sdk.`interface`.sync.AbWmSyncs
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

object UNIWatchMate {
    private lateinit var mContext: Context
    private val mBaseUNIWatches: MutableList<AbUniWatch> = ArrayList()
    var uniWatchSdk: Observable<AbUniWatch>? = null
    private var mMsgTimeOut = 10000

    var mWmConnect: AbWmConnect? = null
    var mWmSettings: AbWmSettings? = null
    var mWmTransferFile: WmTransferFile? = null
    var mWmApps: AbWmApps? = null
    var mWmSyncs: AbWmSyncs? = null

    private var sdkSubject = PublishSubject.create<AbUniWatch>()

    fun init(context: Context, msgTimeOut: Int, supportSdks: Array<AbUniWatch>) {
        mBaseUNIWatches.clear()
        mContext = context

        uniWatchSdk = sdkSubject
        mMsgTimeOut = if (mMsgTimeOut < 5) {
            5
        } else {
            msgTimeOut
        }

        for (i in supportSdks.indices) {
            supportSdks[i].init(mContext, mMsgTimeOut)
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
                sdkSubject.onNext(it)

            }
        }
    }

    fun scanQr(qrString: String) {
        mBaseUNIWatches.forEach {
            val scanDevice = it.parseScanQr(qrString)
            scanDevice?.let { device ->
                if (device.isRecognized) {

                    mWmConnect = it.wmConnect
                    mWmSettings = it.wmSettings
                    mWmApps = it.wmApps
                    mWmSyncs = it.wmSync
                    mWmTransferFile = it.wmTransferFile

                    sdkSubject.onNext(it)
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