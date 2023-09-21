package com.base.sdk.`interface`.setting

import com.base.sdk.entity.settings.*

abstract class AbWmSettings {
    /**
     * sportGoalSetting 运动目标设置
     */
    abstract var settingSportGoal: AbWmSetting<WmSportGoal>?

    /**
     * dateTimeSetting 日期时间设置
     */
    abstract var settingDateTime: AbWmSetting<WmDateTime>?

    /**
     * personalInfoSetting 个人信息设置
     */
    abstract var settingPersonalInfo: AbWmSetting<WmPersonalInfo>?

    /**
     * Sedentary reminder(久坐提醒)
     */
    abstract var settingSedentaryReminder: AbWmSetting<WmSedentaryReminder>?

    /**
     * soundAndHapticSetting(声音和触感设置)
     */
    abstract var settingSoundAndHaptic: AbWmSetting<WmSoundAndHaptic>?

    /**
     * unitInfoSetting(单位设置)
     */
    abstract var settingUnitInfo: AbWmSetting<WmUnitInfo>?

    /**
     * wistRaiseSetting 抬腕设置
     */
    abstract var settingWistRaise: AbWmSetting<WmWistRaise>?

    /**
     * appViewSetting 应用视图设置
     */
    abstract var settingAppView: AbWmSetting<WmAppView>?

    /**
     * drinkWaterSetting 喝水提醒设置
     */
    abstract var settingDrinkWater: AbWmSetting<WmSedentaryReminder>?

    /**
     * rateSetting 心率提醒设置
     */
    abstract var settingHeartRate: AbWmSetting<WmHeartRateAlerts>?
}