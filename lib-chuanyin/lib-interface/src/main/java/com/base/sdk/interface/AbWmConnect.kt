package com.base.sdk.`interface`

import io.reactivex.rxjava3.core.Observable

/**
 * 连接模块
 */
abstract class AbWmConnect {
    /**
     * 连接方法
     */
    abstract fun connect(address: String)

    /**
     * 断开连接
     */
    abstract fun disconnect(): Boolean

    /**
     * 恢复出厂设置
     */
    abstract fun reset()

    /**
     * 连接状态监听
     */
    abstract fun isConnect(): Observable<Boolean>

    /**
     * 是否准备好进行私有协议通讯
     */
    var isReady: Boolean = false
}