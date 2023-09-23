package com.sjbt.sdk.sync

import com.base.sdk.entity.data.WmStepData
import com.base.sdk.`interface`.sync.AbSyncData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter

class SyncStepData : AbSyncData<List<WmStepData>>() {

    var is_support: Boolean = true
    var lastSyncTime: Long = 0
    lateinit var activityObserveEmitter: SingleEmitter<List<WmStepData>>
    lateinit var observeChangeEmitter: ObservableEmitter<List<WmStepData>>
    override fun isSupport(): Boolean {
        return is_support
    }

    override fun latestSyncTime(): Long {
        return lastSyncTime
    }

    override fun syncData(startTime: Long): Single<List<WmStepData>> {
        return Single.create { emitter -> activityObserveEmitter = emitter }
    }

    override var observeSyncData: Observable<List<WmStepData>> =
        Observable.create { emitter -> observeChangeEmitter = emitter }


}