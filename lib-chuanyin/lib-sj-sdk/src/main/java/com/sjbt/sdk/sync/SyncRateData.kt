package com.sjbt.sdk.sync

import android.database.Observable
import com.base.sdk.entity.data.WmHeartRateData
import com.base.sdk.`interface`.sync.AbSyncData

class SyncRateData : AbSyncData<WmHeartRateData>() {

     override fun isSupport(): Boolean {
         TODO("Not yet implemented")
     }

     override fun latestSyncTime(): Long {
         TODO("Not yet implemented")
     }

     override fun syncData(startTime: Long): Observable<List<WmHeartRateData>> {
         TODO("Not yet implemented")
     }
 }