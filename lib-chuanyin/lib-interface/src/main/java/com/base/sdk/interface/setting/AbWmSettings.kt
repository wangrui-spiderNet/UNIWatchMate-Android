package com.base.sdk.`interface`.setting

import com.base.sdk.entity.settings.*

abstract class AbWmSettings {
    /**
     * sportGoalSetting 运动目标设置
     */
    abstract var sportGoalSetting: AbWmSetting<WmSportGoal>?

    /**
     * dateTimeSetting 日期时间设置
     */
    abstract var dateTimeSetting: AbWmSetting<WmDateTime>?

    /**
     * personalInfoSetting 个人信息设置
     */
    abstract var personalInfoSetting: AbWmSetting<WmPersonalInfo>?

    /**
     * Sedentary reminder(久坐提醒)
     */
    abstract var sedentaryReminderSetting: AbWmSetting<WmSedentaryReminder>?

    /**
     * soundAndHapticSetting(声音和触感设置)
     */
    abstract var soundAndHapticSetting: AbWmSetting<WmSoundAndHaptic>?

    /**
     * unitInfoSetting(单位设置)
     */
    abstract var unitInfoSetting: AbWmSetting<WmUnitInfo>?

    /**
     * wistRaiseSetting 抬腕设置
     */
    abstract var wistRaiseSetting: AbWmSetting<WmWistRaise>?

    /**
     * appViewSetting 应用视图设置
     */
    abstract var appViewSetting: AbWmSetting<WmAppView>?

    /**
     * drinkWaterSetting 喝水提醒设置
     */
    abstract var drinkWaterSetting: AbWmSetting<WmSedentaryReminder>?

    /**
     * rateSetting 心率提醒设置
     */
    abstract var rateSetting: AbWmSetting<WmHeartRateAlerts>?
}