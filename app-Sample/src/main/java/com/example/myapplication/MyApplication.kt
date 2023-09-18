package com.example.myapplication

import android.app.Application
import com.base.api.UNIWatchMate
import com.base.sdk.entity.WmDeviceMode
import com.base.sdk.entity.apps.WmConnectState
import com.base.sdk.entity.settings.WmSportGoal
import com.base.sdk.`interface`.AbWmConnect
import com.sjbt.sdk.SJUniWatchSdk
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //第一步：初始化，需要传入支持的sdk实例
        UNIWatchMate.init(this, 10000, arrayOf(SJUniWatchSdk))

        //第二步：通过scanQR连接，或者通过connect直接连接
        UNIWatchMate.mUNIWatchSdk.wmConnect?.scanQr("www.shenju.watch?mac=00:00:56:78:9A:BC?name=SJ 8020N")
        //通过MAC地址回连
        UNIWatchMate.mUNIWatchSdk?.wmConnect?.connect("00:00:56:78:9A:BC", WmDeviceMode.SJ_WATCH)
//        通过发现设备连接
//        UNIWatchMate.mUNIWatchSdk?.wmConnect?.connect(device, WmDeviceMode.SJ_WATCH)

//        获取连接实例
        val connecter = UNIWatchMate.mUNIWatchSdk?.wmConnect

        //监听连接状态
        connecter?.observeConnectState()?.subscribe(object : Observer<WmConnectState> {
            override fun onSubscribe(d: Disposable) {}
            override fun onNext(connectState: WmConnectState) {
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

        //获取设置实例
        val settings = UNIWatchMate.mUNIWatchSdk?.wmSettings

        //设置运动目标 示例：其他与此类似，都是先拿到模块实例，然后通过模块实例调用对应的接口方法
        val sportGoal = WmSportGoal(10000,200.0,10000.0,1000)
        val settingSingle = settings?.sportGoalSetting?.set(sportGoal)
        settingSingle?.subscribe(object : SingleObserver<WmSportGoal> {
            override fun onSubscribe(d: Disposable) {}
            override fun onSuccess(basicInfo: WmSportGoal) {

            }

            override fun onError(e: Throwable) {

            }
        })

    }
}