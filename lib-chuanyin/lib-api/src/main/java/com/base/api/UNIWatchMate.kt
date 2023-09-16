package com.base.api

import android.content.Context
import com.base.sdk.AbUniWatch

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
}