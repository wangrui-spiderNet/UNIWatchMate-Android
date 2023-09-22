package com.base.api

import com.base.sdk.AbUniWatch
import com.base.sdk.`interface`.setting.AbWmSetting
import com.base.sdk.`interface`.setting.AbWmSettings
import com.base.sdk.entity.settings.*

internal class AbWmSettingsDelegate(
    watchObservable: BehaviorObservable<AbUniWatch>
) : AbWmSettings() {

    override var settingSportGoal: AbWmSetting<WmSportGoal>?
        get() = TODO("Not yet implemented")
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