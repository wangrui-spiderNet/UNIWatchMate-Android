package com.base.sdk.`interface`

import io.reactivex.rxjava3.core.Observable

abstract class AbWmConnect {
    abstract fun connect(address: String)
    abstract fun disconnect(): Boolean

    /**
     * 恢复出厂设置
     */
    abstract fun reset()

    abstract fun isConnect(): Observable<Boolean>
    var isReady: Boolean = false
}