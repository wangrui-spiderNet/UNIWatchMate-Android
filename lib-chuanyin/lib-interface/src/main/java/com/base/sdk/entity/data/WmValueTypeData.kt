package com.base.sdk.entity.data

class WmValueTypeData(val id: ValueType, val value: Double)

enum class ValueType {
    START_TIME_STAMP,         // 运动开始时间（时间戳）
    END_TIME_STAMP,           // 运动结束时间（时间戳）
    TOTAL_DURATION,          // 运动总时长（秒）
    TOTAL_MILEAGE,           // 总里程（米）
    CALORIES,               // 活动卡路里（卡）
    FAST_EST_PACE,            // 最快配速（秒/公里）
    SLOW_EST_PACE,            // 最慢配速（秒/公里）
    FAST_EST_SPEED,           // 最快速度（秒/公里）
    TOTAL_STEPS,             // 总步数（步）
    MAX_STEP_FREQUENCY,       // 最大步频（步/秒）
    AVERAGE_HEART_RATE,       // 平均心率（次/分）
    MAX_HEART_RATE,           // 最大心率（次/分）
    MIN_HEART_RATE,           // 最小心率（次/分）
    LIMIT_HEART_RATE_DURATION, // 心率-极限时长（秒）
    ANAEROBIC_ENDURANCE_DURATION, // 心率-无氧耐力时长（秒）
    AEROBIC_ENDURANCE_DURATION,   // 心率-有氧耐力时长（秒）
    FAT_BURNING_DURATION,         // 心率-燃脂时长（秒）
    WARM_UP_DURATION              // 心率-热身时长（秒）
}
