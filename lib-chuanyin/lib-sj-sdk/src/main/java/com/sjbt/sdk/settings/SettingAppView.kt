package com.sjbt.sdk.settings

import com.base.sdk.entity.settings.WmAppView
import com.base.sdk.`interface`.setting.AbWmSetting
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class SettingAppView: AbWmSetting<WmAppView>(){

    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override var observeSettingChange: Observable<WmAppView>
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun set(obj: WmAppView): Single<WmAppView> {
        TODO("Not yet implemented")
    }

    override fun get(): Single<WmAppView> {
        TODO("Not yet implemented")
    }

}