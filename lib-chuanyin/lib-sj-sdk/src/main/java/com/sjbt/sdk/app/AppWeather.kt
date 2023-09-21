package com.sjbt.sdk.app

import com.base.sdk.entity.apps.WmWeatherForecast
import com.base.sdk.`interface`.app.AbAppWeather
import io.reactivex.rxjava3.core.Observable

class AppWeather: AbAppWeather() {
    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override fun pushWeather(weather: WmWeatherForecast): Observable<Boolean> {
        TODO("Not yet implemented")
    }

    override var observeWeather: Observable<Boolean>
        get() = TODO("Not yet implemented")
        set(value) {}
}