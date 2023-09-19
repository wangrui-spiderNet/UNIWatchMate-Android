package com.base.sdk.`interface`.sync

import android.database.Observable
import com.base.sdk.`interface`.IWmSupport

/**
 * 同步数据抽象父类
 * 所有同步数据模块共同继承
 */
abstract class AbSyncData<T> : IWmSupport {

    /**
     * 最近更新时间
     */
    abstract fun latestSyncTime(): Long

    /**
     * 同步数据（最近7日）
     */
    abstract fun syncData(startTime: Long): Observable<List<T>>

    abstract var observeSyncDataList: Observable<List<T>>
}