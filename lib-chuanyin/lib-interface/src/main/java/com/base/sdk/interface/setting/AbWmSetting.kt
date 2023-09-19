package com.base.sdk.`interface`.setting

import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * 所有设置模块父类
 */
abstract class AbWmSetting<T> : IWmSupport {
    abstract var observeSettingChange: Observable<T>
    abstract fun set(obj: T): Single<T>
    abstract fun get(): Single<T>
}