package com.base.sdk.`interface`.sync

import com.base.sdk.entity.data.*
import com.base.sdk.entity.settings.WmDeviceInfo

abstract class AbWmSyncs {

    abstract var syncStepData: AbSyncData<WmStepData>?
    abstract var syncOxygenData: AbSyncData<WmOxygenData>?
    abstract var syncCaloriesData: AbSyncData<WmCaloriesData>?
    abstract var syncSleepData: AbSyncData<WmSleepData>?
    abstract var syncRealtimeRateData: AbSyncData<WmRealtimeRateData>?
    abstract var syncAvgHeartRateData: AbSyncData<WmAvgHeartRateData>?
    abstract var syncDistanceData: AbSyncData<WmDistanceData>?
    abstract var syncActivityData: AbSyncData<WmActivityData>?
    abstract var syncSportSummaryData: AbSyncData<WmSportSummaryData>?
    abstract var syncDeviceInfoData: AbSyncData<WmDeviceInfo>?

}