package com.sjbt.sdk.sync

import com.base.sdk.entity.data.WmRealtimeRateData
import com.base.sdk.`interface`.sync.AbSyncData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter

class SyncRealtimeRateData: AbSyncData<List<WmRealtimeRateData>>(){

    var isSupport: Boolean = true
    var lastSyncTime: Long = 0
    lateinit var activityObserveEmitter: ObservableEmitter<List<WmRealtimeRateData>>
    lateinit var observeChangeEmitter: ObservableEmitter<List<WmRealtimeRateData>>
    override fun isSupport(): Boolean {
        return isSupport
    }

    override fun latestSyncTime(): Long {
        return lastSyncTime
    }

    override fun syncData(startTime: Long): Observable<List<WmRealtimeRateData>> {
        return Observable.create { emitter -> activityObserveEmitter = emitter }
    }

    override var observeSyncData: Observable<List<WmRealtimeRateData>> =
        Observable.create { emitter -> observeChangeEmitter = emitter }


}