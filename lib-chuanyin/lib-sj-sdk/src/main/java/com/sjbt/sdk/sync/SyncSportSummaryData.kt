package com.sjbt.sdk.sync

import com.base.sdk.entity.data.WmSportSummaryData
import com.base.sdk.`interface`.sync.AbSyncData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter

class SyncSportSummaryData: AbSyncData<List<WmSportSummaryData>>() {
    var isSupport: Boolean = true
    var lastSyncTime: Long = 0
    lateinit var activityObserveEmitter: ObservableEmitter<List<WmSportSummaryData>>
    lateinit var observeChangeEmitter: ObservableEmitter<List<WmSportSummaryData>>
    override fun isSupport(): Boolean {
        return isSupport
    }

    override fun latestSyncTime(): Long {
        return lastSyncTime
    }

    override fun syncData(startTime: Long): Observable<List<WmSportSummaryData>> {
        return Observable.create { emitter -> activityObserveEmitter = emitter }
    }

    override var observeSyncData: Observable<List<WmSportSummaryData>> =
        Observable.create { emitter -> observeChangeEmitter = emitter }

}