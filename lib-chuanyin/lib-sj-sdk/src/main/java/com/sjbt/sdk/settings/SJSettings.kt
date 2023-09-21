package com.sjbt.sdk.settings

import com.base.sdk.entity.settings.*
import com.base.sdk.`interface`.setting.AbWmSetting
import com.base.sdk.`interface`.setting.AbWmSettings

class SJSettings : AbWmSettings() {

    override var settingSportGoal: AbWmSetting<WmSportGoal>?
        get() = SettingSportGoal()
        set(value) {}

    override var settingDateTime: AbWmSetting<WmDateTime>?
        get() = TODO("Not yet implemented")
        set(value) {}

    override var settingPersonalInfo: AbWmSetting<WmPersonalInfo>?
        get() = TODO("Not yet implemented")
        set(value) {}

    override var settingSedentaryReminder: AbWmSetting<WmSedentaryReminder>?
        get() = TODO("Not yet implemented")
        set(value) {}

    override var settingSoundAndHaptic: AbWmSetting<WmSoundAndHaptic>?
        get() = TODO("Not yet implemented")
        set(value) {}

    override var settingUnitInfo: AbWmSetting<WmUnitInfo>?
        get() = TODO("Not yet implemented")
        set(value) {}

    override var settingWistRaise: AbWmSetting<WmWistRaise>?
        get() = TODO("Not yet implemented")
        set(value) {}

    override var settingAppView: AbWmSetting<WmAppView>?
        get() = TODO("Not yet implemented")
        set(value) {}

    override var settingDrinkWater: AbWmSetting<WmSedentaryReminder>?
        get() = TODO("Not yet implemented")
        set(value) {}

    override var settingHeartRate: AbWmSetting<WmHeartRateAlerts>?
        get() = TODO("Not yet implemented")
        set(value) {}
}