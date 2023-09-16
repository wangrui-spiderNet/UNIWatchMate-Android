package com.sjbt.sdk.sync

import android.database.Observable
import com.base.sdk.entity.data.WmAvgHeartRateData
import com.base.sdk.`interface`.sync.AbSyncData

class SyncRateData : AbSyncData<WmAvgHeartRateData>() {

     override fun isSupport(): Boolean {
         TODO("Not yet implemented")
     }

     override fun latestSyncTime(): Long {
         TODO("Not yet implemented")
     }

     override fun syncStepData(startTime: Long): Observable<List<WmAvgHeartRateData>> {
         TODO("Not yet implemented")
     }
 }