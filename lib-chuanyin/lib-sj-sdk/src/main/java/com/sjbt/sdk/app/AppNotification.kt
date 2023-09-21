package com.sjbt.sdk.app

import com.base.sdk.entity.apps.WmNotification
import com.base.sdk.`interface`.app.AbAppNotification
import io.reactivex.rxjava3.core.Observable

class AppNotification: AbAppNotification() {
    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override fun sendNotification(): Observable<List<WmNotification>> {
        TODO("Not yet implemented")
    }
}