package com.sjbt.sdk.sync

import com.base.sdk.entity.data.WmStepData
import com.base.sdk.`interface`.sync.AbSyncData
import io.reactivex.rxjava3.core.Observable

class SyncStepData : AbSyncData<List<WmStepData>>() {

    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override fun latestSyncTime(): Long {
        TODO("Not yet implemented")
    }

    override fun syncData(startTime: Long): Observable<List<WmStepData>> {
        TODO("Not yet implemented")
    }

    override var observeSyncData: Observable<List<WmStepData>>
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun syncTimeOut(obj: String) {
        TODO("Not yet implemented")
    }
}