package com.base.sdk.`interface`.app

import com.base.sdk.entity.apps.WmAlarm
import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Observable

/**
 * 应用模块-闹钟
 */
abstract class AbAppAlarm :IWmSupport {
    /**
     * syncAlarmList 同步闹钟列表
     */
    abstract fun syncAlarmList(): Observable<List<WmAlarm>>

    /**
     * addAlarm 添加闹钟
     */
    abstract fun addAlarm(alarm: WmAlarm): Observable<WmAlarm>

    /**
     * deleteAlarm 删除闹钟
     */
    abstract fun deleteAlarm(alarm: WmAlarm): Observable<WmAlarm>

    /**
     * updateAlarm 更新闹钟
     */
    abstract fun updateAlarm(alarm: WmAlarm): Observable<WmAlarm>
}