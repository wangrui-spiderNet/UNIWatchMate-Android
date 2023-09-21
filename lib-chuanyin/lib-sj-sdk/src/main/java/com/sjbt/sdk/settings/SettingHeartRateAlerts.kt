package com.sjbt.sdk.settings

import com.base.sdk.entity.settings.WmHeartRateAlerts
import com.base.sdk.`interface`.setting.AbWmSetting
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class SettingHeartRateAlerts: AbWmSetting<WmHeartRateAlerts>() {

    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override var observeSettingChange: Observable<WmHeartRateAlerts>
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun set(obj: WmHeartRateAlerts): Single<WmHeartRateAlerts> {
        TODO("Not yet implemented")
    }

    override fun get(): Single<WmHeartRateAlerts> {
        TODO("Not yet implemented")
    }
}