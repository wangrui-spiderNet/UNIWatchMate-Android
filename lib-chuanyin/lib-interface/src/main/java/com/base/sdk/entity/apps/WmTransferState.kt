package com.base.sdk.entity.apps

import java.io.File

/**
 * Alarm clock(闹钟)
 */
open class WmTransferState(
    progress: Double,
    state: TransferState,//pre:1 transferring finish
    success: Boolean,
    reason: Int,
    index: Int,
    total: Int,
    sending: File
)