package com.base.sdk.`interface`.setting

import android.database.Observable
import com.base.sdk.`interface`.IWmSupport

abstract class AbWmSetting<T>: IWmSupport {
    lateinit var model: Observable<T>
    abstract fun set(obj:T):Observable<T>
    abstract fun get():Observable<T>
}