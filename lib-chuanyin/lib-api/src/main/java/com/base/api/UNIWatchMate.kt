package com.base.api

import android.bluetooth.BluetoothDevice
import android.content.Context
import com.base.sdk.*
import com.base.sdk.entity.WmDeviceMode
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
    var mUNIWatchSdk: Observable<AbUniWatch>? = null
    private var mMsgTimeOut = 10000

//    var wmConnect: AbWmConnect? = null
//    var wmApps: AbWmApps? = null
//    var wmSettings: AbWmSettings? = null
//    var wmSyncs: AbWmSyncs? = null
//    var wmTransferFile: WmTransferFile? = null

    var subject = PublishSubject.create<AbUniWatch>()

//    lateinit var sdkEmitter: ObservableEmitter<AbUniWatch>
//
//    /**
//     * 监听SDK变化
//     */
//    fun observeSdkChange(): Observable<AbUniWatch> {
//        return Observable.create(object : ObservableOnSubscribe<AbUniWatch> {
//            override fun subscribe(emitter: ObservableEmitter<AbUniWatch>) {
//                sdkEmitter = emitter
//            }
//        })
//    }

    fun init(context: Context, msgTimeOut: Int, supportSdks: Array<AbUniWatch>) {
        mBaseUNIWatches.clear()
        mContext = context

        mUNIWatchSdk = subject

        mMsgTimeOut = if (mMsgTimeOut < 5) {
            5
        } else {
            msgTimeOut
        }

        for (i in supportSdks.indices) {
            supportSdks[i].init(mContext, mMsgTimeOut)
            mBaseUNIWatches.add(supportSdks[i])
        }

        if (mUNIWatchSdk == null && mBaseUNIWatches.isEmpty()) {
            throw RuntimeException("No Sdk Register Exception!")
        }
    }

    fun connect(address: String, deviceMode: WmDeviceMode) {
        mBaseUNIWatches.forEach {
            if (it.wmConnect?.connect(address, deviceMode)?.isRecognized == true) {
                subject.onNext(it)

//                wmApps = it.wmApps
//                wmConnect = it.wmConnect
//                wmSettings = it.wmSettings
//                wmSyncs = it.wmSync
//                wmTransferFile = it.wmTransferFile
            }
        }
    }

    fun connect(address: BluetoothDevice, deviceMode: WmDeviceMode) {
        mBaseUNIWatches.forEach {
            if (it.wmConnect?.connect(address, deviceMode)?.isRecognized == true) {
                subject.onNext(it)

//                wmApps = it.wmApps
//                wmConnect = it.wmConnect
//                wmSettings = it.wmSettings
//                wmSyncs = it.wmSync
//                wmTransferFile = it.wmTransferFile
            }
        }
    }

    fun scanQr(scanQr: String) {
        mBaseUNIWatches.forEach {
            if (it.wmConnect?.scanQr(scanQr)?.isRecognized == true) {
                subject.onNext(it)

//                wmApps = it.wmApps
//                wmConnect = it.wmConnect
//                wmSettings = it.wmSettings
//                wmSyncs = it.wmSync
//                wmTransferFile = it.wmTransferFile
            }
        }
    }

}