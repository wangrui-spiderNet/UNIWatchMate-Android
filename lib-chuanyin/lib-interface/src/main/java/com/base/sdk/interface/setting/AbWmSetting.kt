package com.base.sdk.`interface`.setting

import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Single

/**
 * 所有设置模块父类
 */
abstract class AbWmSetting<T> : IWmSupport {
    lateinit var observeSettingChange: Single<T>
    abstract fun set(obj: T): Single<T>
    abstract fun get(): Single<T>
}