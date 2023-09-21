package com.sjbt.sdk.settings

import com.base.sdk.entity.settings.WmPersonalInfo
import com.base.sdk.`interface`.setting.AbWmSetting
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class SettingPersonalInfo: AbWmSetting<WmPersonalInfo>(){

    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override var observeSettingChange: Observable<WmPersonalInfo>
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun set(obj: WmPersonalInfo): Single<WmPersonalInfo> {
        TODO("Not yet implemented")
    }

    override fun get(): Single<WmPersonalInfo> {
        TODO("Not yet implemented")
    }

}