package com.sjbt.sdk.sync

import com.base.sdk.entity.data.WmHeartRateData
import com.base.sdk.`interface`.sync.AbSyncData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter

class SyncHeartRateData: AbSyncData<List<WmHeartRateData>>() {
    var isSupport: Boolean = true
    var lastSyncTime: Long = 0
    lateinit var activityObserveEmitter: ObservableEmitter<List<WmHeartRateData>>
    lateinit var observeChangeEmitter: ObservableEmitter<List<WmHeartRateData>>
    override fun isSupport(): Boolean {
        return isSupport
    }

    override fun latestSyncTime(): Long {
        return lastSyncTime
    }

    override fun syncData(startTime: Long): Observable<List<WmHeartRateData>> {
        return Observable.create { emitter -> activityObserveEmitter = emitter }
    }

    override var observeSyncData: Observable<List<WmHeartRateData>> =
        Observable.create { emitter -> observeChangeEmitter = emitter }


}