package com.base.sdk.`interface`.setting

import com.base.sdk.entity.settings.*

abstract class WmSettings {
    abstract var sportGoalSetting: AbWmSetting<WmSportGoal>?
    abstract var dateTimeSetting: AbWmSetting<WmDateTime>?
    abstract var personalInfoSetting: AbWmSetting<WmPersonalInfo>?
    abstract var sedentaryReminderSetting: AbWmSetting<WmSedentaryReminder>?
    abstract var soundAndHapticSetting: AbWmSetting<WmSoundAndHaptic>?
    abstract var unitInfoSetting: AbWmSetting<WmUnitInfo>?
    abstract var wistRaiseSetting: AbWmSetting<WmWistRaise>?
}