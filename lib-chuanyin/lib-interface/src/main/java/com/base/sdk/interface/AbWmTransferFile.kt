package com.base.sdk.`interface`

import android.database.Observable
import com.base.sdk.entity.apps.WmTransferState
import io.reactivex.rxjava3.core.Single
import java.io.File

/**
 * 传输文件功能抽象类
 */
abstract class WmTransferFile : IWmSupport {
    abstract fun start(fileType: FileType, file: File): Observable<WmTransferState>
    abstract fun startMultiple(fileType: FileType, file: List<File>): Observable<WmTransferState>
    abstract fun isSupportMultiple(): Boolean
    abstract fun cancelTransfer(): Single<Boolean>
}

enum class FileType {
    OTA,//设备ota
    DIAL,//表盘
    MUSIC,//MP3类型
    TXT,//Txt电子书
    SPORT,//运动文件（备用未定）
}