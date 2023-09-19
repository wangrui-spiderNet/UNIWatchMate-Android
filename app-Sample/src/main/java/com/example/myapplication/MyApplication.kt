package com.example.myapplication

import android.app.Application
import android.util.Log
import com.base.api.UNIWatchMate
import com.base.sdk.entity.WmDeviceMode
import com.base.sdk.entity.apps.WmConnectState
import com.base.sdk.entity.settings.WmSportGoal
import com.base.sdk.`interface`.AbWmConnect
import com.base.sdk.`interface`.WmTransferFile
import com.base.sdk.`interface`.app.AbWmApps
import com.base.sdk.`interface`.log.WmLog
import com.base.sdk.`interface`.setting.AbWmSettings
import com.base.sdk.`interface`.sync.AbWmSyncs
import com.sjbt.sdk.SJUniWatchSdk
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable

class MyApplication : Application() {

    var mWmConnect: AbWmConnect? = null
    var mWmSettings: AbWmSettings? = null
    var mWmTransferFile: WmTransferFile? = null
    var mWmApps: AbWmApps? = null
    var mWmSyncs: AbWmSyncs? = null

    val TAG: String = "MyApplication"

    companion object {
        @JvmStatic
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        //第一步：初始化，需要传入支持的sdk实例
        UNIWatchMate.init(this, 10000, arrayOf(SJUniWatchSdk))

        UNIWatchMate.mUNIWatchSdk?.subscribe {
            WmLog.e(TAG, "SDK changed")
            it.setLogEnable(true)
            mWmConnect = it.wmConnect
            mWmSettings = it.wmSettings
            mWmApps = it.wmApps
            mWmSyncs = it.wmSync
            mWmTransferFile = it.wmTransferFile

        }

        //第二步：通过scanQR连接，或者通过connect直接连接
//        mWmConnect?.scanQr("www.shenju.watch?mac=00:00:56:78:9A:BC?name=SJ 8020N")
//
//        //通过MAC地址回连
//        mWmConnect?.connect("00:00:56:78:9A:BC", WmDeviceMode.SJ_WATCH)

        //通过发现设备连接
        //UNIWatchMate.wmConnect?.connect(device, WmDeviceMode.SJ_WATCH)

//        settingsSample()
//
        observeConnectState()
    }

    fun settingsSample() {

        //设置运动目标 示例：其他与此类似，都是通过模块实例调用对应的接口方法

        val sportGoal = WmSportGoal(10000, 200.0, 10000.0, 1000)
        val settingSingle = mWmSettings?.sportGoalSetting?.set(sportGoal)
        settingSingle?.subscribe(object : SingleObserver<WmSportGoal> {
            override fun onSubscribe(d: Disposable) {}
            override fun onSuccess(basicInfo: WmSportGoal) {

            }

            override fun onError(e: Throwable) {

            }
        })
    }

    /**
     * 全局监听
     */
    private fun observeConnectState() {
        //监听连接状态
        UNIWatchMate.mUNIWatchSdk?.flatMap {
            it.wmConnect!!.observeConnectState
        }?.subscribe(object : Observer<WmConnectState> {
            override fun onSubscribe(d: Disposable) {}
            override fun onNext(connectState: WmConnectState) {

                WmLog.e(TAG, "connect state: $connectState")

                when (connectState) {
                    WmConnectState.BT_DISABLE -> {

                    }

                    WmConnectState.DISCONNECTED -> {

                    }

                    WmConnectState.CONNECTING -> {

                    }

                    WmConnectState.PRE_CONNECTED -> {

                    }

                    WmConnectState.CONNECTED -> {

                    }

                    WmConnectState.VERIFIED -> {

                    }

                    WmConnectState.CONNECT_FAIL -> {

                    }
                }
            }

            override fun onError(e: Throwable) {}
            override fun onComplete() {}
        })
    }


}