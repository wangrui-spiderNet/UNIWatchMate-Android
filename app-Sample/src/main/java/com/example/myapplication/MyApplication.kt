package com.example.myapplication

import android.app.Application
import com.base.api.UNIWatchMate
import com.base.sdk.entity.settings.WmSportGoal
import com.sjbt.sdk.SJUniWatchSdk

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        UNIWatchMate.init(this, 10000, arrayOf(SJUniWatchSdk))
        UNIWatchMate.mUNIWatchSdk?.connect("123456")

        UNIWatchMate.mUNIWatchSdk?.abWmSettings?.sportGoalSetting?.set(WmSportGoal(1,2.5,4.5,6))
    }
}