package com.base.sdk.`interface`.app

import com.base.sdk.entity.apps.DialItem
import com.base.sdk.entity.apps.WmDial
import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Observable

abstract class AbAppDial : IWmSupport {
    abstract fun syncDialList(): Observable<WmDial>
    abstract fun deleteDial(alarm: DialItem): Observable<DialItem>
}