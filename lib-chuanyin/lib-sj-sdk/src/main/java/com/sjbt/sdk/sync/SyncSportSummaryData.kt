package com.sjbt.sdk.sync

import com.base.sdk.entity.data.WmSportSummaryData
import com.base.sdk.`interface`.sync.AbSyncData
import io.reactivex.rxjava3.core.Observable

class SyncSportSummaryData: AbSyncData<List<WmSportSummaryData>>() {

    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override fun latestSyncTime(): Long {
        TODO("Not yet implemented")
    }

    override fun syncData(startTime: Long): Observable<List<WmSportSummaryData>> {
        TODO("Not yet implemented")
    }

    override var observeSyncData: Observable<List<WmSportSummaryData>>
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun syncTimeOut(obj: String) {
        TODO("Not yet implemented")
    }
}