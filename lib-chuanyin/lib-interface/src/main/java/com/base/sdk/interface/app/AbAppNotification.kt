package com.base.sdk.`interface`.app

import com.base.sdk.entity.apps.WmNotification
import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Observable

abstract class AbAppNotification :IWmSupport {
    abstract fun sendNotification(): Observable<List<WmNotification>>
}