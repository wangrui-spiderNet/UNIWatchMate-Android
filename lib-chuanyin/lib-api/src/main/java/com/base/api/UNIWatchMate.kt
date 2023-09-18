package com.base.api

import android.bluetooth.BluetoothDevice
import android.content.Context
import com.base.sdk.AbUniWatch
import com.base.sdk.entity.WmDeviceMode

object UNIWatchMate {
    private lateinit var mContext: Context
    private val mBaseUNIWatches: MutableList<AbUniWatch> = ArrayList()
    lateinit var mUNIWatchSdk: AbUniWatch
    private var mMsgTimeOut = 10000

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
            }
        }
    }

    fun connect(address: BluetoothDevice, deviceMode: WmDeviceMode) {
        mBaseUNIWatches.forEach {
            if (it.wmConnect?.connect(address, deviceMode)?.isRecognized == true) {
                mUNIWatchSdk = it
            }
        }
    }

    fun scanQr(scanQr: String) {
        mBaseUNIWatches.forEach {
            if (it.wmConnect?.scanQr(scanQr)?.isRecognized == true) {
                mUNIWatchSdk = it
            }
        }
    }
}