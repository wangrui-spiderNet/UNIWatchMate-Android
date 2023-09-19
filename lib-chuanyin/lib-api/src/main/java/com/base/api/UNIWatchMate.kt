package com.base.api

import android.bluetooth.BluetoothDevice
import android.content.Context
import com.base.sdk.AbUniWatch
import com.base.sdk.entity.WmDeviceMode
import com.base.sdk.`interface`.AbWmConnect
import com.base.sdk.`interface`.WmTransferFile
import com.base.sdk.`interface`.app.AbWmApps
import com.base.sdk.`interface`.setting.AbWmSettings
import com.base.sdk.`interface`.sync.AbWmSyncs

object UNIWatchMate {
    private lateinit var mContext: Context
    private val mBaseUNIWatches: MutableList<AbUniWatch> = ArrayList()
    private lateinit var mUNIWatchSdk: AbUniWatch
    private var mMsgTimeOut = 10000

    var wmConnect: AbWmConnect? = null
    var wmApps: AbWmApps? = null
    var wmSettings: AbWmSettings? = null
    var wmSyncs: AbWmSyncs? = null
    var wmTransferFile: WmTransferFile? = null

    fun init(context: Context, msgTimeOut: Int, supportSdks: Array<AbUniWatch>) {
        mBaseUNIWatches.clear()
        mContext = context
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
                mUNIWatchSdk = it

                wmApps = it.wmApps
                wmConnect = it.wmConnect
                wmSettings = it.wmSettings
                wmSyncs = it.wmSync
                wmTransferFile = it.wmTransferFile
            }
        }
    }

    fun connect(address: BluetoothDevice, deviceMode: WmDeviceMode) {
        mBaseUNIWatches.forEach {
            if (it.wmConnect?.connect(address, deviceMode)?.isRecognized == true) {
                mUNIWatchSdk = it

                wmApps = it.wmApps
                wmConnect = it.wmConnect
                wmSettings = it.wmSettings
                wmSyncs = it.wmSync
                wmTransferFile = it.wmTransferFile
            }
        }
    }

    fun scanQr(scanQr: String) {
        mBaseUNIWatches.forEach {
            if (it.wmConnect?.scanQr(scanQr)?.isRecognized == true) {
                mUNIWatchSdk = it

                wmApps = it.wmApps
                wmConnect = it.wmConnect
                wmSettings = it.wmSettings
                wmSyncs = it.wmSync
                wmTransferFile = it.wmTransferFile
            }
        }
    }
}