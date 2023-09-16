package com.sjbt.sdk.sync

import android.database.Observable
import com.base.sdk.entity.data.WmSleepData
import com.base.sdk.`interface`.sync.AbSyncData

class SyncSleepData : AbSyncData<WmSleepData>() {

    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override fun latestSyncTime(): Long {
        TODO("Not yet implemented")
    }

    override fun syncStepData(startTime: Long): Observable<List<WmSleepData>> {
        TODO("Not yet implemented")
    }
}