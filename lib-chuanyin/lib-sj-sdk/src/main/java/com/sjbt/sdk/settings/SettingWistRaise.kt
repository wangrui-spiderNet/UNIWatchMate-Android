package com.sjbt.sdk.settings

import com.base.sdk.entity.settings.WmWistRaise
import com.base.sdk.`interface`.setting.AbWmSetting
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class SettingWistRaise: AbWmSetting<WmWistRaise>()  {
    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override fun observeChange(): Observable<WmWistRaise> {
        TODO("Not yet implemented")
    }

    override fun set(obj: WmWistRaise): Single<WmWistRaise> {
        TODO("Not yet implemented")
    }

    override fun get(): Single<WmWistRaise> {
        TODO("Not yet implemented")
    }

}