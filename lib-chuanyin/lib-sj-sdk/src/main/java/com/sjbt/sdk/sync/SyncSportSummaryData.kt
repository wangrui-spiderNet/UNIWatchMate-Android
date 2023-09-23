package com.sjbt.sdk.sync

import com.base.sdk.entity.data.WmSportSummaryData
import com.base.sdk.`interface`.sync.AbSyncData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter

class SyncSportSummaryData : AbSyncData<List<WmSportSummaryData>>() {
    var is_support: Boolean = true
    var lastSyncTime: Long = 0
    lateinit var activityObserveEmitter: SingleEmitter<List<WmSportSummaryData>>
    lateinit var observeChangeEmitter: ObservableEmitter<List<WmSportSummaryData>>
    override fun isSupport(): Boolean {
        return is_support
    }

    override fun latestSyncTime(): Long {
        return lastSyncTime
    }

    override fun syncData(startTime: Long): Single<List<WmSportSummaryData>> {
        return Single.create { emitter -> activityObserveEmitter = emitter }
    }

    override var observeSyncData: Observable<List<WmSportSummaryData>> =
        Observable.create { emitter -> observeChangeEmitter = emitter }

}