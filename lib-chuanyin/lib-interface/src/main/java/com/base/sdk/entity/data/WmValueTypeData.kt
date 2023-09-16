package com.base.sdk.entity.data

class WmValueTypeData(val id: ValueType, val value: Double)

enum class ValueType {
    StartTimestamp,         // 运动开始时间（时间戳）
    EndTimestamp,           // 运动结束时间（时间戳）
    TotalDuration,          // 运动总时长（秒）
    TotalMileage,           // 总里程（米）
    Calories,               // 活动卡路里（卡）
    FastestPace,            // 最快配速（秒/公里）
    SlowestPace,            // 最慢配速（秒/公里）
    FastestSpeed,           // 最快速度（秒/公里）
    TotalSteps,             // 总步数（步）
    MaxStepFrequency,       // 最大步频（步/秒）
    AverageHeartRate,       // 平均心率（次/分）
    MaxHeartRate,           // 最大心率（次/分）
    MinHeartRate,           // 最小心率（次/分）
    LimitHeartRateDuration, // 心率-极限时长（秒）
    AnaerobicEnduranceDuration, // 心率-无氧耐力时长（秒）
    AerobicEnduranceDuration,   // 心率-有氧耐力时长（秒）
    FatBurningDuration,         // 心率-燃脂时长（秒）
    WarmUpDuration              // 心率-热身时长（秒）
}
