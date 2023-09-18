package com.base.sdk.`interface`.sync

import com.base.sdk.entity.data.*
import com.base.sdk.entity.settings.WmDeviceInfo

/**
 * 同步数据
 */
abstract class AbWmSyncs {
    /**
     * sync step(同步步数)
     */
    abstract var syncStepData: AbSyncData<WmStepData>?

    /**
     * sync oxygen(同步血氧)
     */
    abstract var syncOxygenData: AbSyncData<WmOxygenData>?

    /**
     * syncCalories(同步卡路里)
     */
    abstract var syncCaloriesData: AbSyncData<WmCaloriesData>?

    /**
     * syncSleep(同步睡眠)
     */
    abstract var syncSleepData: AbSyncData<WmSleepData>?

    /**
     * syncRealtimeRate(同步实时心率)
     */
    abstract var syncRealtimeRateData: AbSyncData<WmRealtimeRateData>?

    /**
     * syncAvgHeartRate(同步平均心率)
     */
    abstract var syncAvgHeartRateData: AbSyncData<WmHeartRateData>?

    /**
     * syncDistance(同步距离)
     */
    abstract var syncDistanceData: AbSyncData<WmDistanceData>?

    /**
     * syncActivity(同步日常活动)
     */
    abstract var syncActivityData: AbSyncData<WmActivityData>?

    /**
     * syncSportSummary(同步运动小结)
     */
    abstract var syncSportSummaryData: AbSyncData<WmSportSummaryData>?

    /**
     * syncDeviceInfo(同步设备信息)
     */
    abstract var syncDeviceInfoData: AbSyncData<WmDeviceInfo>?

    /**
     * syncTodayInfo(同步当日数据)
     */
    abstract var syncTodayInfoData: AbSyncData<WmTodayTotalData>?
}