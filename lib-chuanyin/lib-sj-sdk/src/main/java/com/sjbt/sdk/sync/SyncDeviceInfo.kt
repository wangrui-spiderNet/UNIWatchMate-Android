package com.sjbt.sdk.sync

import com.base.sdk.entity.settings.WmDeviceInfo
import com.base.sdk.`interface`.sync.AbSyncData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe

class SyncDeviceInfo : AbSyncData<WmDeviceInfo>() {

    var deviceEmitter: ObservableEmitter<WmDeviceInfo>? = null
    var observeDeviceEmitter: ObservableEmitter<WmDeviceInfo>? = null
    var lastSyncTime: Long = 0

    override fun isSupport(): Boolean {
        return true
    }

    override fun latestSyncTime(): Long {
        return lastSyncTime
    }

    override fun syncData(startTime: Long): Observable<WmDeviceInfo> {
        return Observable.create(object : ObservableOnSubscribe<WmDeviceInfo> {
            override fun subscribe(emitter: ObservableEmitter<WmDeviceInfo>) {
                deviceEmitter = emitter
            }
        })
    }

    override var observeSyncData: Observable<WmDeviceInfo> =
        Observable.create(object : ObservableOnSubscribe<WmDeviceInfo> {
            override fun subscribe(emitter: ObservableEmitter<WmDeviceInfo>) {
                observeDeviceEmitter = emitter
            }
        })


}