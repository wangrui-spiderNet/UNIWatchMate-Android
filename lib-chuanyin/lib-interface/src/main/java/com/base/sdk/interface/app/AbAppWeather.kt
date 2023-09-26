package com.base.sdk.`interface`.app

import com.base.sdk.entity.apps.WmWeatherForecast
import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * 应用模块 - 天气同步
 */
abstract class AbAppWeather :IWmSupport {

    /**
     * pushWeather 为设备推送天气信息
     */
    abstract fun pushWeather(weather: WmWeatherForecast): Single<Boolean>

    /**
     * observeWeather 监听设备端天气请求
     */
    abstract var observeWeather : Observable<Boolean>

}