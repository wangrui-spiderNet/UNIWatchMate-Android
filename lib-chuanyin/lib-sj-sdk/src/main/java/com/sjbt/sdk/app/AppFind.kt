package com.sjbt.sdk.app

import com.base.sdk.entity.apps.WmFind
import com.base.sdk.`interface`.app.AbAppFind
import com.base.sdk.`interface`.app.StopType
import io.reactivex.rxjava3.core.Observable

class AppFind : AbAppFind() {
    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override var observeFindMobile: Observable<WmFind>
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun stopFindMobile(): Observable<StopType> {
        TODO("Not yet implemented")
    }

    override fun findWatch(ring_count: WmFind): Observable<Boolean> {
        TODO("Not yet implemented")
    }

    override fun stopFindWatch(flag: StopType): Observable<Boolean> {
        TODO("Not yet implemented")
    }
}