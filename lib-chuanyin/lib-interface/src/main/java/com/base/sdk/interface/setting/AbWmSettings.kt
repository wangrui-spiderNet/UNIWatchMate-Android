package com.base.sdk.`interface`.setting

import com.base.sdk.entity.common.WmLanguage
import com.base.sdk.entity.settings.*

abstract class AbWmSettings {
    abstract var sportGoalSetting: AbWmSetting<WmSportGoal>?
    abstract var dateTimeSetting: AbWmSetting<WmDateTime>?
    abstract var personalInfoSetting: AbWmSetting<WmPersonalInfo>?
    abstract var sedentaryReminderSetting: AbWmSetting<WmSedentaryReminder>?
    abstract var soundAndHapticSetting: AbWmSetting<WmSoundAndHaptic>?
    abstract var unitInfoSetting: AbWmSetting<WmUnitInfo>?
    abstract var wistRaiseSetting: AbWmSetting<WmWistRaise>?
    abstract var appViewSetting: AbWmSetting<WmAppView>?
    abstract var drinkWaterSetting: AbWmSetting<WmSedentaryReminder>?
    abstract var languageSetting: AbWmSetting<WmLanguage>?
    abstract var rateSetting: AbWmSetting<WmHeartRateAlerts>?
}