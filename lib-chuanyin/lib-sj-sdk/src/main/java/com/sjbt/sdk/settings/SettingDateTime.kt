package com.sjbt.sdk.settings

import com.base.sdk.entity.settings.WmDateTime
import com.base.sdk.`interface`.setting.AbWmSetting
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class SettingDateTime: AbWmSetting<WmDateTime>() {
    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override fun observeChange(): Observable<WmDateTime> {
        TODO("Not yet implemented")
    }

    override fun set(obj: WmDateTime): Single<WmDateTime> {
        TODO("Not yet implemented")
    }

    override fun get(): Single<WmDateTime> {
        TODO("Not yet implemented")
    }

}
