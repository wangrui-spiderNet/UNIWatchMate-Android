package com.base.sdk.entity.apps

import java.io.File

/**
 * 传输状态
 */
open class WmTransferState(
    progress: Double,
    state: State,
    success: Boolean,
    errReason: Int,
    index: Int,
    total: Int,
    sendingFile: File
)

enum class State {
    PRE_TRANSFER,
    TRANSFERRING,
    ALL_FINISH
}

