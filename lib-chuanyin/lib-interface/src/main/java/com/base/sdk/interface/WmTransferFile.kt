package com.base.sdk.`interface`

import android.database.Observable
import com.base.sdk.entity.apps.WmTransferState
import io.reactivex.rxjava3.core.Single
import java.io.File

abstract class WmTransferFile : IWmSupport {
    abstract fun start(file: File): Observable<WmTransferState>
    abstract fun startMultiple(file: List<File>): Observable<WmTransferState>
    abstract fun isSupportMultiple(): Boolean
    abstract fun cancelTransfer(): Single<Boolean>
}