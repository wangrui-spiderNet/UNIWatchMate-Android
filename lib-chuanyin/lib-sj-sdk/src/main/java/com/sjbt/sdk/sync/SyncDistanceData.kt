package com.sjbt.sdk.sync

import android.database.Observable
import com.base.sdk.entity.data.WmDistanceData
import com.base.sdk.`interface`.sync.AbSyncData

class SyncDistanceData :  AbSyncData<WmDistanceData>() {

    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override fun latestSyncTime(): Long {
        TODO("Not yet implemented")
    }

    override fun syncData(startTime: Long): Observable<List<WmDistanceData>> {
        TODO("Not yet implemented")
    }

    override var observeSyncDataList: Observable<List<WmDistanceData>>
        get() = TODO("Not yet implemented")
        set(value) {}
}