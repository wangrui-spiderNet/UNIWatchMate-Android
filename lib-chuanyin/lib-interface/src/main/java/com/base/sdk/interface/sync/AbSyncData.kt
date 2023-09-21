package com.base.sdk.`interface`.sync

import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Observable

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
    abstract fun syncData(startTime: Long): Observable<T>

    /**
     * 观察数据监听
     */
    abstract var observeSyncData: Observable<T>

    /**
     * 同步消息超时
     */
    abstract fun syncTimeOut(obj: String)
}