package com.base.sdk.`interface`.app

import com.base.sdk.entity.apps.WmWeatherForecast
import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Observable

abstract class AbAppWeather :IWmSupport {

    /**
     * Weather
     */
    abstract fun pushWeather(weather: WmWeatherForecast): Observable<Boolean>
    abstract fun observeWeather(): Observable<Boolean>

}