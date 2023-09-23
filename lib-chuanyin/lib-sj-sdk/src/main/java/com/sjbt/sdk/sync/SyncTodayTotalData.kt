package com.sjbt.sdk.sync

import com.base.sdk.entity.data.WmTodayTotalData
import com.base.sdk.`interface`.sync.AbSyncData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter

class SyncTodayTotalData : AbSyncData<WmTodayTotalData>() {

    var isSupport: Boolean = true
    var lastSyncTime: Long = 0
    lateinit var activityObserveEmitter: ObservableEmitter<WmTodayTotalData>
    lateinit var observeChangeEmitter: ObservableEmitter<WmTodayTotalData>
    override fun isSupport(): Boolean {
        return isSupport
    }

    override fun latestSyncTime(): Long {
        return lastSyncTime
    }

    override fun syncData(startTime: Long): Observable<WmTodayTotalData> {
        return Observable.create { emitter -> activityObserveEmitter = emitter }
    }

    override var observeSyncData: Observable<WmTodayTotalData> =
        Observable.create { emitter -> observeChangeEmitter = emitter }

}