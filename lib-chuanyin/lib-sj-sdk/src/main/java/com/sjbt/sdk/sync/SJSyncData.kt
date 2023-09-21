package com.sjbt.sdk.sync

import com.base.sdk.entity.data.*
import com.base.sdk.entity.settings.WmDeviceInfo
import com.base.sdk.`interface`.sync.AbSyncData
import com.base.sdk.`interface`.sync.AbWmSyncs

class SJSyncData : AbWmSyncs() {

    override var syncStepData: AbSyncData<List<WmStepData>>?
        get() = SyncStepData()
        set(value) {}
    override var syncOxygenData: AbSyncData<List<WmOxygenData>>?
        get() = SyncOxygenData()
        set(value) {}
    override var syncCaloriesData: AbSyncData<List<WmCaloriesData>>?
        get() = SyncCaloriesData()
        set(value) {}
    override var syncSleepData: AbSyncData<List<WmSleepData>>?
        get() = SyncSleepData()
        set(value) {}
    override var syncRealtimeRateData: AbSyncData<List<WmRealtimeRateData>>?
        get() = SyncRealtimeRateData()
        set(value) {}
    override var syncHeartRateData: AbSyncData<List<WmHeartRateData>>?
        get() = SyncHeartRateData()
        set(value) {}
    override var syncDistanceData: AbSyncData<List<WmDistanceData>>?
        get() = SyncDistanceData()
        set(value) {}
    override var syncActivityData: AbSyncData<List<WmActivityData>>?
        get() = SyncActivityData()
        set(value) {}
    override var syncSportSummaryData: AbSyncData<List<WmSportSummaryData>>?
        get() = SyncSportSummaryData()
        set(value) {}
    override var syncDeviceInfoData: AbSyncData<WmDeviceInfo>?
        get() = SyncDeviceInfo()
        set(value) {}
    override var syncTodayInfoData: AbSyncData<WmTodayTotalData>?
        get() = SyncTodayTotalData()
        set(value) {}

}