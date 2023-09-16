package com.base.sdk.entity.data

interface ICalculateSleepItem {
    fun getCalculateStatus(): Int
    fun getCalculateStartTime(): Long
}

/**
 * Sleep data(睡眠数据)
 */
class WmSleepData(
    val timestamp: Long,
    val items: List<WmSleepItem>
)

class WmSleepItem(
    val status: Int,//
    val startTime: Long
) : ICalculateSleepItem {
    companion object {
        /**
         * Sleep status of deep sleep
         */
        const val STATUS_DEEP = 1 //深睡

        /**
         * Sleep status of light sleep
         */
        const val STATUS_LIGHT = 2 //浅睡

        /**
         * Sleep status of sober sleep
         */
        const val STATUS_SOBER = 3 //清醒
        const val STATUS_REM = 4 //快速眼动

    }

    override fun getCalculateStatus(): Int {
        return status
    }

    override fun getCalculateStartTime(): Long {
        return startTime
    }



}