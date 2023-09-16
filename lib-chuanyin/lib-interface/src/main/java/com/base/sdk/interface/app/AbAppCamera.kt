package com.base.sdk.`interface`.app

import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

abstract class AbAppCamera :IWmSupport {

    /**
     * camera action
     */
    abstract fun observeCameraState(): Observable<Boolean>
    abstract fun observeCameraFlash(): Observable<Int>
    abstract fun cameraFlashSwitch(type: Int): Observable<Int>
    abstract fun observeCameraFrontBack(): Observable<Boolean>
    abstract fun cameraBackSwitch(isBack: Boolean): Observable<Boolean>


    /**
     * camera preview
     */
    //从feature list 返回
    abstract fun isCameraPreviewEnable(): Boolean
    abstract fun isCameraPreviewReady(): Single<Boolean>
    abstract fun updateCameraPreview(data: ByteArray)

}