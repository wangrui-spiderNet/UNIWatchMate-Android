package com.sjbt.sdk.settings

import com.base.sdk.entity.settings.WmUnitInfo
import com.base.sdk.`interface`.setting.AbWmSetting
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class SettingUnitInfo : AbWmSetting<WmUnitInfo>() {
    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override fun observeChange(): Observable<WmUnitInfo> {
        TODO("Not yet implemented")
    }

    override fun set(obj: WmUnitInfo): Single<WmUnitInfo> {
        TODO("Not yet implemented")
    }

    override fun get(): Single<WmUnitInfo> {
        TODO("Not yet implemented")
    }

}