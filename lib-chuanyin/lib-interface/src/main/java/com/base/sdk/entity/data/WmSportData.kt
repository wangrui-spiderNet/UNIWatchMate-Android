package com.base.sdk.entity.data

class WmSportData(
    timestamp: Long,
    intervalTime: Long,

    /**
     * Duration(用时, 分钟)
     */
    val duration: Int,
    /**
     * Distance, in meter.(距离, 米)
     */
    val distance: Int,
    /**
     * Calorie, in calorie (消耗, 卡)
     */
    val calories: Int,
    /**
     * Step(步数)
     */
    val steps: Int,
    /**
     * Average pace(平均配速)
     */
    val avgPace: Int,
    /**
     * Max pace(最大配速)
     */
    val maxPace: Int,
    /**
     * Min pace(最低配速)
     */
    val minPace: Int,
    /**
     * Average speed(平均速度)
     */
    val avgSpeed: Int,
    /**
     * Max speed(最大速度)
     */
    val maxSpeed: Int,
    /**
     * Min speed(最低速度)
     */
    val minSpeed: Int,
    /**
     * Average step frequency(平均步频)
     */
    val avgStepFreq: Int,
    /**
     * Max step frequency(最大步频)
     */
    val maxStepFreq: Int,
    /**
     * Min step frequency(最低步频)
     */
    val minStepFreq: Int,
    /**
     * Average heart rate(平均心率)
     */
    val avgHR: Int,
    /**
     * Max heart rate(最大心率)
     */
    val maxHR: Int,
    /**
     * Min heart rate(最低心率)
     */
    val minHR: Int,
    /**
     * Skip count(跳绳计数)
     */
    val skipCount: Int,
    /**
     * Heart rate distribution, the duration of each heart rate range, in seconds.
     * (心率分布, 每个心率区间的时长, 单位秒)
     */
    val hrDistribution: List<Int>,


) : WmBaseSyncData(timestamp,intervalTime)
