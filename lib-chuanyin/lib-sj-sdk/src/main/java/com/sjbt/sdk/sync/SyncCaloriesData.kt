package com.sjbt.sdk.sync

import com.base.sdk.entity.data.WmCaloriesData
import com.base.sdk.`interface`.sync.AbSyncData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter

class SyncCaloriesData : AbSyncData<List<WmCaloriesData>>() {
    var isSupport: Boolean = true
    var lastSyncTime: Long = 0
    lateinit var activityObserveEmitter: ObservableEmitter<List<WmCaloriesData>>
    lateinit var observeChangeEmitter: ObservableEmitter<List<WmCaloriesData>>
    override fun isSupport(): Boolean {
        return isSupport
    }

    override fun latestSyncTime(): Long {
        return lastSyncTime
    }

    override fun syncData(startTime: Long): Observable<List<WmCaloriesData>> {
        return Observable.create { emitter -> activityObserveEmitter = emitter }
    }

    override var observeSyncData: Observable<List<WmCaloriesData>> =
        Observable.create { emitter -> observeChangeEmitter = emitter }

}