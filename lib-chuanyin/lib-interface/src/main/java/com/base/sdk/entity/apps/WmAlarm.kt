package com.base.sdk.entity.apps

import android.os.Parcel
import android.os.Parcelable
import com.base.sdk.entity.settings.IdBean
import java.util.*

/**
 * Alarm clock(闹钟)
 */
open class WmAlarm(
    /**
     * The alarm clock id. Mark a unique alarm clock.
     * Can only add 5 alarm clocks. So the alarmId must in 0-4.
     * [ALARM_ID_MIN]
     * [ALARM_ID_MAX]
     */
    override var id: Int
) : IdBean, Cloneable, Parcelable {
    /**
     * The alarm clock year.
     */
    var year: Int = 0

    /**
     * The alarm clock month.
     */
    var month: Int = 0

    /**
     * The alarm clock day.
     */
    var day: Int = 0

    /**
     * The alarm clock hour.
     */
    var hour: Int = 0

    /**
     * The alarm clock minute.
     */
    var minute: Int = 0

    /**
     * The alarm clock repeat.
     *
     * Use [FcRepeatFlag.isRepeatEnabled] get repeat days.Use [FcRepeatFlag.setRepeatEnabled] set repeat days.
     * If you changed this repeat value, don't forget save changed with [repeat].
     */
    var repeat: Int = 0

    private var _isEnabled: Boolean = false

    /**
     * The alarm clock enable.
     */
    var isEnabled: Boolean
        get() {
            if (repeat == 0 && _isEnabled) { //不重复，并且是打开的，那么要判断是否过期
                val calendar = Calendar.getInstance()
                val current = calendar.timeInMillis
                calendar.set(year, month, day, hour, minute, 0)
                if (current > calendar.timeInMillis) { //过期
                    _isEnabled = false
                }
            }
            return _isEnabled
        }
        set(value) {
            _isEnabled = value
        }

    /**
     * The alarm clock label. Limit 32 bytes.
     */
    var label: String? = null

    /**
     * Before setting the alarm clock, it's better to call this method once.
     * [FcSettingsFeature.setAlarms] will auto call this method.
     */
    open fun adjust() {
        val calendar = Calendar.getInstance()
        if (repeat == 0 && _isEnabled) { //没有循环，那么只设置了一次
            val minuteToday = hour * 60 + minute
            val currentMinute = calendar[Calendar.HOUR_OF_DAY] * 60 + calendar[Calendar.MINUTE]
            if (minuteToday <= currentMinute) {
                calendar[Calendar.DAY_OF_MONTH] = calendar[Calendar.DAY_OF_MONTH] + 1
            }
        }
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH]
        day = calendar[Calendar.DAY_OF_MONTH]
    }

    public override fun clone(): WmAlarm {
        return super.clone() as WmAlarm
    }

    override fun toString(): String {
        return "FcAlarm{id:$id year:$year month:$month day:$day hour:$hour minute:$minute repeat:$repeat label:$label isEnabled:$isEnabled"
    }

    constructor(parcel: Parcel) : this(parcel.readInt()) {
        year = parcel.readInt()
        month = parcel.readInt()
        day = parcel.readInt()
        hour = parcel.readInt()
        minute = parcel.readInt()
        repeat = parcel.readInt()
        _isEnabled = parcel.readInt() != 0
        label = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(year)
        parcel.writeInt(month)
        parcel.writeInt(day)
        parcel.writeInt(hour)
        parcel.writeInt(minute)
        parcel.writeInt(repeat)
        parcel.writeInt(if (_isEnabled) 1 else 0)
        parcel.writeString(label)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        const val ALARM_ID_MIN = 0
        const val ALARM_ID_MAX = 9

        fun findNewAlarmId(list: List<WmAlarm>?): Int {
            return IdBean.findNewId(list, ALARM_ID_MIN, ALARM_ID_MAX)
        }

        @JvmField
        val CREATOR = object : Parcelable.Creator<WmAlarm> {
            override fun createFromParcel(parcel: Parcel): WmAlarm {
                return WmAlarm(parcel)
            }

            override fun newArray(size: Int): Array<WmAlarm?> {
                return arrayOfNulls(size)
            }
        }
    }

}