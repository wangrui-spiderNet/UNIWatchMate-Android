package com.base.sdk.`interface`.app

import com.base.sdk.entity.apps.WmNotification
import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Observable

/**
 * App-notification 应用模块-通知
 */
abstract class AbAppNotification :IWmSupport {
    /**
     * sendNotification 发送通知
     */
    abstract fun sendNotification(): Observable<List<WmNotification>>
}