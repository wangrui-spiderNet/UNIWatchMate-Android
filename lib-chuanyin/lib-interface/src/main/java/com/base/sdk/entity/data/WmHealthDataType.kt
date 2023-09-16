package com.base.sdk.entity.data

import androidx.annotation.IntDef

@IntDef(
    value = [
        WmHealthDataType.TAKE_TIME,
        WmHealthDataType.DISTANCE,
        WmHealthDataType.CALORIE,
        WmHealthDataType.STEP,
        WmHealthDataType.AVG_PACE,
        WmHealthDataType.MAX_PACE,
        WmHealthDataType.MIN_PACE,
        WmHealthDataType.AVG_SPEED,
        WmHealthDataType.MAX_SPEED,
        WmHealthDataType.MIN_SPEED,
        WmHealthDataType.AVG_STEP_FREQUENCY,
        WmHealthDataType.MAX_STEP_FREQUENCY,
        WmHealthDataType.MIN_STEP_FREQUENCY,
        WmHealthDataType.AVG_HEART_RATE,
        WmHealthDataType.MAX_HEART_RATE,
        WmHealthDataType.MIN_HEART_RATE,
        WmHealthDataType.SKIP_COUNT,
        WmHealthDataType.HEART_RATE_DISTRIBUTION
    ],
    flag = true
)
@Retention(AnnotationRetention.BINARY)
annotation class WmHealthDataType {
    companion object {
        /**
         * Take time(用时)
         */
        const val TAKE_TIME = 0x01
        /**
         * Distance(距离)
         */
        const val DISTANCE = 0x01 shl 1
        /**
         * Calorie(消耗)
         */
        const val CALORIE = 0x01 shl 2
        /**
         * Step(步数)
         */
        const val STEP = 0x01 shl 3
        /**
         * Average pace(平均配速)
         */
        const val AVG_PACE = 0x01 shl 4
        /**
         * Max pace(最大配速)
         */
        const val MAX_PACE = 0x01 shl 5
        /**
         * Min pace(最低配速)
         */
        const val MIN_PACE = 0x01 shl 6
        /**
         * Average speed(平均速度)
         */
        const val AVG_SPEED = 0x01 shl 7
        /**
         * Max speed(最大速度)
         */
        const val MAX_SPEED = 0x01 shl 8
        /**
         * Min speed(最低速度)
         */
        const val MIN_SPEED = 0x01 shl 9
        /**
         * Average step frequency(平均步频)
         */
        const val AVG_STEP_FREQUENCY = 0x01 shl 10
        /**
         * Max step frequency(最大步频)
         */
        const val MAX_STEP_FREQUENCY = 0x01 shl 11
        /**
         * Min step frequency(最低步频)
         */
        const val MIN_STEP_FREQUENCY = 0x01 shl 12
        /**
         * Average heart rate(平均心率)
         */
        const val AVG_HEART_RATE = 0x01 shl 13
        /**
         * Max heart rate(最大心率)
         */
        const val MAX_HEART_RATE = 0x01 shl 14
        /**
         * Min heart rate(最低心率)
         */
        const val MIN_HEART_RATE = 0x01 shl 15
        /**
         * Skip count(跳绳计数)
         */
        const val SKIP_COUNT = 0x01 shl 16
        /**
         * Heart rate distribution(心率分布)
         */
        const val HEART_RATE_DISTRIBUTION = 0x01 shl 17
    }
}