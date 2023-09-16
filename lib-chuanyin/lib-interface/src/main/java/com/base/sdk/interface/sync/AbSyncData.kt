package com.base.sdk.`interface`.sync

import android.database.Observable
import com.base.sdk.`interface`.IWmSupport

abstract class AbSyncData<T > : IWmSupport {

    abstract fun latestSyncTime(): Long
    abstract fun syncStepData(startTime: Long): Observable<List<T>>
}