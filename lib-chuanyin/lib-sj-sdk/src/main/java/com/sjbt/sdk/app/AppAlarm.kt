package com.sjbt.sdk.app

import com.base.sdk.entity.apps.WmAlarm
import com.base.sdk.`interface`.app.AbAppAlarm
import io.reactivex.rxjava3.core.Observable

class AppAlarm : AbAppAlarm() {
    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override fun syncAlarmList(): Observable<List<WmAlarm>> {
        TODO("Not yet implemented")
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