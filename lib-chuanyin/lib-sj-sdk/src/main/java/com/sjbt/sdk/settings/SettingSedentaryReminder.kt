package com.sjbt.sdk.settings

import com.base.sdk.entity.settings.WmSedentaryReminder
import com.base.sdk.`interface`.setting.AbWmSetting
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class SettingSedentaryReminder : AbWmSetting<WmSedentaryReminder>(){
    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override var observeSettingChange: Observable<WmSedentaryReminder>
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun set(obj: WmSedentaryReminder): Single<WmSedentaryReminder> {
        TODO("Not yet implemented")
    }

    override fun get(): Single<WmSedentaryReminder> {
        TODO("Not yet implemented")
    }

}