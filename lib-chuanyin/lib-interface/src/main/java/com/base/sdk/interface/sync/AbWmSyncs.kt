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
    abstract var syncStepData: AbSyncData<List<WmStepData>>?

    /**
     * sync oxygen(同步血氧)
     */
    abstract var syncOxygenData: AbSyncData<List<WmOxygenData>>?

    /**
     * syncCalories(同步卡路里)
     */
    abstract var syncCaloriesData: AbSyncData<List<WmCaloriesData>>?

    /**
     * syncSleep(同步睡眠)
     */
    abstract var syncSleepData: AbSyncData<List<WmSleepData>>?

    /**
     * syncRealtimeRate(同步实时心率)
     */
    abstract var syncRealtimeRateData: AbSyncData<List<WmRealtimeRateData>>?

    /**
     * syncAvgHeartRate(同步平均心率)
     */
    abstract var syncHeartRateData: AbSyncData<List<WmHeartRateData>>?

    /**
     * syncDistance(同步距离)
     */
    abstract var syncDistanceData: AbSyncData<List<WmDistanceData>>?

    /**
     * syncActivity(同步日常活动)
     */
    abstract var syncActivityData: AbSyncData<List<WmActivityData>>?

    /**
     * syncSportSummary(同步运动小结)
     */
    abstract var syncSportSummaryData: AbSyncData<List<WmSportSummaryData>>?

    /**
     * syncDeviceInfo(同步设备信息)
     */
    abstract var syncDeviceInfoData: AbSyncData<WmDeviceInfo>?

    /**
     * syncTodayInfo(同步当日数据)
     */
    abstract var syncTodayInfoData: AbSyncData<WmTodayTotalData>?
}