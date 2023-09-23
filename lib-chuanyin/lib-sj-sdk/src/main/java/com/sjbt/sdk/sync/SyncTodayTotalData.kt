package com.sjbt.sdk.sync

import com.base.sdk.entity.data.WmTodayTotalData
import com.base.sdk.`interface`.sync.AbSyncData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter

class SyncTodayTotalData : AbSyncData<WmTodayTotalData>() {

    var is_support: Boolean = true
    var lastSyncTime: Long = 0
    lateinit var activityObserveEmitter: SingleEmitter<WmTodayTotalData>
    lateinit var observeChangeEmitter: ObservableEmitter<WmTodayTotalData>
    override fun isSupport(): Boolean {
        return is_support
    }

    override fun latestSyncTime(): Long {
        return lastSyncTime
    }

    override fun syncData(startTime: Long): Single<WmTodayTotalData> {
        return Single.create { emitter -> activityObserveEmitter = emitter }
    }

    override var observeSyncData: Observable<WmTodayTotalData> =
        Observable.create { emitter -> observeChangeEmitter = emitter }

}