package com.sjbt.sdk.sync

import android.database.Observable
import com.base.sdk.`interface`.sync.AbSyncData

class SyncCaloriesData : AbSyncData<SyncCaloriesData>(){

    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override fun latestSyncTime(): Long {
        TODO("Not yet implemented")
    }

    override fun syncStepData(startTime: Long): Observable<List<SyncCaloriesData>> {
        TODO("Not yet implemented")
    }
}