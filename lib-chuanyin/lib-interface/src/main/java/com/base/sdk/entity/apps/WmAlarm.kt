package com.base.sdk.entity.apps

/**
 * AlarmData structure 闹钟数据结构
 */
class WmAlarm(
    var alarmId: Int,//闹钟Id
    var alarmName: String,//闹钟名称
    var alarmTime: Long,//闹钟时间
    var repeatOptions: Set<AlarmRepeatOption>//重复模式
) {
    var isOn: Boolean = false

    fun setAlarm(alarmId: Int, name: String, time: Long, options: Set<AlarmRepeatOption>) {
        this.alarmName = name
        this.alarmTime = time
        this.repeatOptions = options
    }

    fun turnOn() {
        this.isOn = true
        // 在这里添加真正的闹钟逻辑
    }

    fun turnOff() {
        this.isOn = false
        // 在这里添加停止闹钟的逻辑
    }

    override fun toString(): String {
        return "WmAlarm(alarmId=$alarmId, alarmName='$alarmName', alarmTime=$alarmTime, repeatOptions=$repeatOptions, isOn=$isOn)"
    }


}

/**
 * 重复模式
 */
enum class AlarmRepeatOption(val value: Int) {
    NONE(0),
    SUNDAY(1 shl 0),
    MONDAY(1 shl 1),
    TUESDAY(1 shl 2),
    WEDNESDAY(1 shl 3),
    THURSDAY(1 shl 4),
    FRIDAY(1 shl 5),
    SATURDAY(1 shl 6);

    companion object {
        fun fromValue(value: Int): Set<AlarmRepeatOption> {
            return AlarmRepeatOption.values().filter { option -> option.value and value != 0 }
                .toSet()
        }
    }


}
