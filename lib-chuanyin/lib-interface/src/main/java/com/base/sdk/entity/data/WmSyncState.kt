package com.base.sdk.entity.data

import androidx.annotation.IntDef

@IntDef(
    WmSyncState.FAILED_DISCONNECTED,
    WmSyncState.FAILED_CHECKING_ECG,
    WmSyncState.FAILED_SAVING_ECG,
    WmSyncState.FAILED_UNKNOWN,
    WmSyncState.START,
    WmSyncState.SUCCESS,
)
@Retention(AnnotationRetention.BINARY)
annotation class WmSyncState {
    companion object {
        /**
         * Sync failed because wristband disconnect.
         */
        const val FAILED_DISCONNECTED = -1

        /**
         * Sync failed because wristband is checking ecg.
         */
        const val FAILED_CHECKING_ECG = -2

        /**
         * Sync failed because wristband is saving ecg data.
         */
        const val FAILED_SAVING_ECG = -3

        /**
         * Sync failed but the reason unknown
         */
        const val FAILED_UNKNOWN = -128 //在Integer缓冲池范围内

        /**
         * Sync state start
         */
        const val START = 0

        //1-100 progress

        /**
         * Sync state success
         */
        const val SUCCESS = 127 //在Integer缓冲池范围内
    }
}