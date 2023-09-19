package com.example.myapplication.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.base.api.UNIWatchMate
import com.base.sdk.entity.WmDeviceModel
import com.base.sdk.entity.settings.WmSportGoal
import com.example.myapplication.R
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_actiivty)

        val btnConnect = findViewById<Button>(R.id.btn_connect)
        val btnExchange = findViewById<Button>(R.id.btn_exchange)

        btnConnect.setOnClickListener {
            UNIWatchMate.scanQr("www.shenju.watch?mac=00:00:56:78:9A:BC?name=SJ 8020N")
        }

        btnExchange.setOnClickListener {
            connectSample()
        }
    }

    private fun connectSample() {
        UNIWatchMate.mWmConnect?.connect("12:34:56:78:9A:BC", WmDeviceModel.SJ_WATCH)
    }

    fun settingsSample() {
        //设置运动目标 示例：其他与此类似，都是通过模块实例调用对应的接口方法
        val sportGoal = WmSportGoal(10000, 200.0, 10000.0, 1000)
        val settingSingle = UNIWatchMate.mWmSettings?.sportGoalSetting?.set(sportGoal)
        settingSingle?.subscribe(object : SingleObserver<WmSportGoal> {
            override fun onSubscribe(d: Disposable) {}
            override fun onSuccess(basicInfo: WmSportGoal) {

            }

            override fun onError(e: Throwable) {

            }
        })
    }
}