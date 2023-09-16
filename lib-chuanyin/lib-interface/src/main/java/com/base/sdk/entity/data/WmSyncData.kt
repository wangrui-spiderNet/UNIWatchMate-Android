package com.base.sdk.entity.data

import com.base.sdk.entity.common.Sport

/**
 * Sync data(同步数据)
 */
data class WmSyncData(
    /**
     * Data type(数据类型)
     */
    @WmSyncDataType val type: Int,
    /**
     * Data(数据)
     */
    val data: WmBaseSyncData,
)

abstract class WmBaseSyncData(
    /**
     * Timestamp of this data
     */
    val timestamp: Long,
    val intervalTime: Long
)

class WmHeartRateData(
    timestamp: Long,
    intervalTime: Long,
    /**
     * heart rate value (beats per minute)
     */
    val rateRangeId: Int,
    val timeDuration:Int,
    val type: WmSportData

) : WmBaseSyncData(timestamp,intervalTime)

class WmAvgHeartRateData(
    timestamp: Long,
    intervalTime: Long,
    /**
     * heart rate value (beats per minute)
     */
    val minHeartRate: Int,
    val maxHeartRate: Int,
    val avgHeartRate: Int,

    /**
     * activity duration, in seconds
     */
    val duration: Int

) : WmBaseSyncData(timestamp,intervalTime)

class WmOxygenData(
    timestamp: Long,
    intervalTime:Long,
    /**
     * Oxygen value (SpO2)，0~100
     */
    val oxygen: Int
) : WmBaseSyncData(timestamp, intervalTime)

class WmStepData(
    timestamp: Long,
    intervalTime:Long,
    /**
     * Step value
     */
    val step: Int,

) : WmBaseSyncData(timestamp, intervalTime)

class WmDistanceData(
    timestamp: Long,
    intervalTime:Long,
    /**
     * Step value
     */
    val distance: Int,

) : WmBaseSyncData(timestamp, intervalTime)

class WmCaloriesData(
    timestamp: Long,
    intervalTime:Long,
    /**
     * Step value
     */
    val distance: Int,

    ) : WmBaseSyncData(timestamp, intervalTime)



class WmBloodPressureData(
    timestamp: Long,
    intervalTime:Long,
    /**
     * systolic blood pressure (unit mmHg)
     */
    val sbp: Int, //收缩压值

    /**
     * diastolic blood pressure (unit mmHg)
     */
    val dbp: Int //舒张压值
) : WmBaseSyncData(timestamp, intervalTime)

class WmBloodPressureMeasureData(
    timestamp: Long,
    intervalTime:Long,
    /**
     * systolic blood pressure (unit mmHg)
     */
    val sbp: Int, //收缩压值

    /**
     * diastolic blood pressure (unit mmHg)
     */
    val dbp: Int, //舒张压值

    /**
     * Additional heart rate values.
     * This value exists only if [WmDeviceInfo.Feature.BLOOD_PRESSURE_AIR_PUMP] is support
     */
    val heartRate: Int
) : WmBaseSyncData(timestamp, intervalTime)


class WmRealtimeRateData(
    timestamp: Long,
    intervalTime:Long,
    /**
     * Respiratory rate value (breaths per minute)
     */
    val rate: Int
) : WmBaseSyncData(timestamp, intervalTime)

class WmPressureData(
    timestamp: Long,
    intervalTime:Long,
    /**
     * Pressure value. Limit(0,256)
     */
    val pressure: Int
) : WmBaseSyncData(timestamp, intervalTime)


class WmTemperatureData(
    timestamp: Long,
    intervalTime:Long,
    /**
     * Temperature of your body(unit ℃)。
     * This value is generally in the normal body temperature range[36℃-42℃].
     */
    val body: Float,
    /**
     * Temperature of your wrist(unit ℃)。
     * The range of this value is wider, because it is related to the ambient temperature, in extreme cases it may be below 0℃.
     */
    val wrist: Float
) : WmBaseSyncData(timestamp, intervalTime)


class WmGameData(
    /**
     * Game start time
     */
    timestamp: Long,
    intervalTime:Long,
    /**
     * Game Type
     */
    val type: Int,

    /**
     * Game duration in seconds
     */
    val duration: Int,
    val score: Int,
    val level: Int
) : WmBaseSyncData(timestamp, intervalTime)

/**
 * The ecg data.
 *
 * If [WmDeviceInfo.Feature.TI_ECG] is supported, you can adjust the speed and amplitude of ECG data.
 */
class WmEcgData(
    timestamp: Long,
    intervalTime:Long,
    /**
     * Sampling rate (number of ECG values per second)
     *
     * Such as 100Hz represents 10ms of a data point
     */
    val samplingRate: Int,
    /**
     * Ecg values
     */
    val items: List<Int>,
) : WmBaseSyncData(timestamp, intervalTime) {
    companion object {
        const val DEFAULT_SAMPLING_RATE = 100
    }
}

