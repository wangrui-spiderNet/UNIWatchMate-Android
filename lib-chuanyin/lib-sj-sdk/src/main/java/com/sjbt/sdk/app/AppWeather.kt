package com.sjbt.sdk.app

import com.base.sdk.entity.apps.WmWeatherForecast
import com.base.sdk.`interface`.app.AbAppWeather
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class AppWeather: AbAppWeather() {
    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override fun pushWeather(weather: WmWeatherForecast): Single<Boolean> {
        TODO("Not yet implemented")
    }

    override var observeWeather: Observable<Boolean>
        get() = TODO("Not yet implemented")
        set(value) {}
}