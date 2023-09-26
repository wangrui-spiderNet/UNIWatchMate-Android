package com.base.api

import android.app.Application
import com.base.sdk.AbUniWatch
import com.base.sdk.`interface`.AbWmConnect
import com.base.sdk.`interface`.WmTransferFile
import com.base.sdk.`interface`.app.AbWmApps
import com.base.sdk.`interface`.setting.AbWmSettings
import com.base.sdk.`interface`.sync.AbWmSyncs
import com.base.sdk.entity.WmDeviceModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

object UNIWatchMate {
    private lateinit var application: Application
    private val uniWatches: MutableList<AbUniWatch> = ArrayList()

    var mWmTransferFile: WmTransferFile? = null
    var mWmApps: AbWmApps? = null
    var mWmSyncs: AbWmSyncs? = null

    var mInstance: AbUniWatch? = null

    private val uniWatchSubject = BehaviorSubject.create<AbUniWatch>()
    private val uniWatchObservable = BehaviorObservable<AbUniWatch>(uniWatchSubject)

    val wmConnect: AbWmConnect = AbWmConnectDelegate(uniWatchSubject)
    val wmSettings: AbWmSettings = AbWmSettingsDelegate(uniWatchObservable)

    fun init(application: Application, uniWatches: List<AbUniWatch>) {
        if (this::application.isInitialized) {
            return
        }
        this.application = application
        this.uniWatches.addAll(uniWatches)

        if (uniWatches.isEmpty()) {
            throw RuntimeException("No Sdk Register Exception!")
        }
    }

    fun setDeviceModel(deviceMode: WmDeviceModel) {
        if (uniWatchSubject.value?.getDeviceModel() == deviceMode) {
            //deviceMode����
            return
        }

        for (i in uniWatches.indices) {
            val uniWatch = uniWatches[i]
            if (uniWatch.setDeviceMode(deviceMode)) {
                uniWatchSubject.onNext(uniWatch)
                mInstance = uniWatch
                return
            }
        }

        //���ִ������˵��������û����ȷ���� init
        throw RuntimeException("No Sdk Match Exception!")
    }

    fun scanQr(qrString: String) {
        uniWatches.forEach {
            val scanDevice = it.parseScanQr(qrString)
            scanDevice?.let { device ->
                if (device.isRecognized) {
                    mWmApps = it.wmApps
                    mWmSyncs = it.wmSync
                    mWmTransferFile = it.wmTransferFile

                    mInstance = it
                    uniWatchSubject.onNext(it)

                    wmConnect?.connect(device.address!!, device.mode)
                }
            }
        }
    }

    fun observeUniWatchChange(): Observable<AbUniWatch> {
        return uniWatchObservable
    }

}