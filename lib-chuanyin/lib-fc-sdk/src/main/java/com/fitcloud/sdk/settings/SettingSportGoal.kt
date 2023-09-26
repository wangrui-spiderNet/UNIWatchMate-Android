package com.fitcloud.sdk.settings

import com.base.sdk.`interface`.setting.AbWmSetting
import com.base.sdk.entity.settings.WmSportGoal
import com.topstep.fitcloud.sdk.v2.FcConnector
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

internal class SettingSportGoal(
    private val connector: FcConnector
) : AbWmSetting<WmSportGoal>() {
    override fun isSupport(): Boolean {
        return true
    }

    override fun observeChange(): Observable<WmSportGoal> {
        TODO("Not yet implemented")
    }

    override fun set(obj: WmSportGoal): Single<WmSportGoal> {
        return connector.settingsFeature().setExerciseGoal(
            step = obj.steps,
            distance = (obj.distance * 1000).toInt(),
            calorie = obj.calories.toInt(),
        ).andThen(Single.just(obj))
    }

    override fun get(): Single<WmSportGoal> {
        return connector.settingsFeature().requestExerciseGoal().map {
            WmSportGoal(
                steps = it.step,
                calories = it.calorie.toDouble(),
                distance = it.distance / 1000.0,
                activityMinutes = 1000
            )
        }
    }
}