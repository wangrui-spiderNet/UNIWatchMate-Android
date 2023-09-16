package com.base.sdk.`interface`

import android.database.Observable
import com.base.sdk.entity.apps.WmTransferState
import io.reactivex.rxjava3.core.Single
import java.io.File

abstract class WmTransferFile : IWmSupport {
    abstract fun start(fileType: FileType, file: File): Observable<WmTransferState>
    abstract fun startMultiple(fileType: FileType, file: List<File>): Observable<WmTransferState>
    abstract fun isSupportMultiple(): Boolean
    abstract fun cancelTransfer(): Single<Boolean>
}

enum class FileType {
    OTA,
    DIAL,
    MUSIC,
    TXT,
    SPORT
}