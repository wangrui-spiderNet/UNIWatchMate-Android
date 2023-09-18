package com.sjbt.sdk.sync

import android.database.Observable
import com.base.sdk.entity.data.WmStepData
import com.base.sdk.`interface`.sync.AbSyncData

class SyncStepData : AbSyncData<WmStepData>() {

    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override fun latestSyncTime(): Long {
        TODO("Not yet implemented")
    }

    override fun syncData(startTime: Long): Observable<List<WmStepData>> {
        TODO("Not yet implemented")
    }
}