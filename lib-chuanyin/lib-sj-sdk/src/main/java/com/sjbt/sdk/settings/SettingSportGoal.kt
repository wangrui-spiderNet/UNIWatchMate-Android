package com.sjbt.sdk.settings

import android.database.Observable
import com.base.sdk.entity.settings.WmSportGoal
import com.base.sdk.`interface`.setting.AbWmSetting
import io.reactivex.rxjava3.core.Single

class SettingSportGoal : AbWmSetting<WmSportGoal>() {
    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override fun set(obj: WmSportGoal): Single<WmSportGoal> {
        TODO("Not yet implemented")
    }

    override fun get(): Single<WmSportGoal> {
        TODO("Not yet implemented")
    }

    override var observeSettingChange: io.reactivex.rxjava3.core.Observable<WmSportGoal>
        get() = TODO("Not yet implemented")
        set(value) {}
}