package com.sjbt.sdk.app

import com.base.sdk.entity.apps.WmAlarm
import com.base.sdk.`interface`.app.AbAppAlarm
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe

class AppAlarm : AbAppAlarm() {

    var isSupport: Boolean = false
    lateinit var alarmListEmitter: ObservableEmitter<List<WmAlarm>>

    override fun isSupport(): Boolean {
        return isSupport
    }

    override var syncAlarmList: Observable<List<WmAlarm>> = Observable.create {
        alarmListEmitter = it
    }

    override fun addAlarm(alarm: WmAlarm): Observable<WmAlarm> {
        TODO("Not yet implemented")
    }

    override fun deleteAlarm(alarm: WmAlarm): Observable<WmAlarm> {
        TODO("Not yet implemented")
    }

    override fun updateAlarm(alarm: WmAlarm): Observable<WmAlarm> {
        TODO("Not yet implemented")
    }
}