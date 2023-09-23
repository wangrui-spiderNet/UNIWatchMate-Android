package com.sjbt.sdk.sync

import com.base.sdk.entity.data.WmOxygenData
import com.base.sdk.`interface`.sync.AbSyncData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter

class SyncOxygenData : AbSyncData<List<WmOxygenData>>() {
    var isSupport: Boolean = true
    var lastSyncTime: Long = 0
    lateinit var activityObserveEmitter: ObservableEmitter<List<WmOxygenData>>
    lateinit var observeChangeEmitter: ObservableEmitter<List<WmOxygenData>>
    override fun isSupport(): Boolean {
        return isSupport
    }

    override fun latestSyncTime(): Long {
        return lastSyncTime
    }

    override fun syncData(startTime: Long): Observable<List<WmOxygenData>> {
        return Observable.create { emitter -> activityObserveEmitter = emitter }
    }

    override var observeSyncData: Observable<List<WmOxygenData>> =
        Observable.create { emitter -> observeChangeEmitter = emitter }


}