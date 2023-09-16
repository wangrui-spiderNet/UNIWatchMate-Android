package com.base.sdk.`interface`.app

import com.base.sdk.entity.apps.WmAlarm
import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Observable

abstract class AbAppAlarm :IWmSupport {

    abstract fun syncAlarmList(): Observable<List<WmAlarm>>
    abstract fun addAlarm(alarm: WmAlarm): Observable<WmAlarm>
    abstract fun deleteAlarm(alarm: WmAlarm): Observable<WmAlarm>
    abstract fun updateAlarm(alarm: WmAlarm): Observable<WmAlarm>
}