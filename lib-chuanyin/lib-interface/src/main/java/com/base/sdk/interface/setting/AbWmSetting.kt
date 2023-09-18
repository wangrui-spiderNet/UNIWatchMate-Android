package com.base.sdk.`interface`.setting

import android.database.Observable
import com.base.sdk.`interface`.IWmSupport

/**
 * 所有设置模块父类
 */
abstract class AbWmSetting<T> : IWmSupport {
    lateinit var observeSettingChange: Observable<T>
    abstract fun set(obj: T): Observable<T>
    abstract fun get(): Observable<T>
}