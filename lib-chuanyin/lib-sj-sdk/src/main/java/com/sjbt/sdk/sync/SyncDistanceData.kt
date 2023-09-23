package com.sjbt.sdk.sync

import com.base.sdk.entity.data.WmDistanceData
import com.base.sdk.`interface`.sync.AbSyncData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter

class SyncDistanceData : AbSyncData<List<WmDistanceData> >() {

    var isSupport: Boolean = true
    var lastSyncTime: Long = 0
    lateinit var activityObserveEmitter: ObservableEmitter<List<WmDistanceData>>
    lateinit var observeChangeEmitter: ObservableEmitter<List<WmDistanceData>>
    override fun isSupport(): Boolean {
        return isSupport
    }

    override fun latestSyncTime(): Long {
        return lastSyncTime
    }

    override fun syncData(startTime: Long): Observable<List<WmDistanceData>> {
        return Observable.create { emitter -> activityObserveEmitter = emitter }
    }

    override var observeSyncData: Observable<List<WmDistanceData>> =
        Observable.create { emitter -> observeChangeEmitter = emitter }


}