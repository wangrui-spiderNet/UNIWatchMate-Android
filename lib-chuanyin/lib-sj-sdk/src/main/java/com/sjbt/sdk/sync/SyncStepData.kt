package com.sjbt.sdk.sync

import com.base.sdk.entity.data.WmStepData
import com.base.sdk.`interface`.sync.AbSyncData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter

class SyncStepData : AbSyncData<List<WmStepData>>() {

    var isSupport: Boolean = true
    var lastSyncTime: Long = 0
    lateinit var activityObserveEmitter: ObservableEmitter<List<WmStepData>>
    lateinit var observeChangeEmitter: ObservableEmitter<List<WmStepData>>
    override fun isSupport(): Boolean {
        return isSupport
    }

    override fun latestSyncTime(): Long {
        return lastSyncTime
    }

    override fun syncData(startTime: Long): Observable<List<WmStepData>> {
        return Observable.create { emitter -> activityObserveEmitter = emitter }
    }

    override var observeSyncData: Observable<List<WmStepData>> =
        Observable.create { emitter -> observeChangeEmitter = emitter }


}