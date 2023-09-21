package com.sjbt.sdk.settings

import com.base.sdk.entity.settings.WmSoundAndHaptic
import com.base.sdk.`interface`.setting.AbWmSetting
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class SettingSoundAndHaptic : AbWmSetting<WmSoundAndHaptic>() {
    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override var observeSettingChange: Observable<WmSoundAndHaptic>
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun set(obj: WmSoundAndHaptic): Single<WmSoundAndHaptic> {
        TODO("Not yet implemented")
    }

    override fun get(): Single<WmSoundAndHaptic> {
        TODO("Not yet implemented")
    }

}