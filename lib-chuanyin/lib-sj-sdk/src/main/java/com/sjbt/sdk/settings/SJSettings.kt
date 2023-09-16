package com.sjbt.sdk.settings

import com.base.sdk.entity.common.WmLanguage
import com.base.sdk.entity.settings.*
import com.base.sdk.`interface`.setting.AbWmSetting
import com.base.sdk.`interface`.setting.WmSettings

class SJSettings : WmSettings() {

    override var sportGoalSetting: AbWmSetting<WmSportGoal>?
        get() = SettingSportGoal()
        set(value) {}

    override var dateTimeSetting: AbWmSetting<WmDateTime>?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var personalInfoSetting: AbWmSetting<WmPersonalInfo>?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var sedentaryReminderSetting: AbWmSetting<WmSedentaryReminder>?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var soundAndHapticSetting: AbWmSetting<WmSoundAndHaptic>?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var unitInfoSetting: AbWmSetting<WmUnitInfo>?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var wistRaiseSetting: AbWmSetting<WmWistRaise>?
        get() = TODO("Not yet implemented")
        set(value) {}

    override var appViewSetting: AbWmSetting<WmAppView>?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var drinkWaterSetting: AbWmSetting<WmSedentaryReminder>?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var languageSetting: AbWmSetting<WmLanguage>?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var rateSetting: AbWmSetting<WmHeartRateAlerts>?
        get() = TODO("Not yet implemented")
        set(value) {}
}