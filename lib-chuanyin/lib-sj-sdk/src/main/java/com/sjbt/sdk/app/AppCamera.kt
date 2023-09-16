package com.sjbt.sdk.app

import com.base.sdk.`interface`.app.AbAppCamera
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class AppCamera :AbAppCamera() {

    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override fun observeCameraState(): Observable<Boolean> {
        TODO("Not yet implemented")
    }

    override fun observeCameraFlash(): Observable<Int> {
        TODO("Not yet implemented")
    }

    override fun cameraFlashSwitch(type: Int): Observable<Int> {
        TODO("Not yet implemented")
    }

    override fun observeCameraFrontBack(): Observable<Boolean> {
        TODO("Not yet implemented")
    }

    override fun cameraBackSwitch(isBack: Boolean): Observable<Boolean> {
        TODO("Not yet implemented")
    }

    override fun isCameraPreviewEnable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isCameraPreviewReady(): Single<Boolean> {
        TODO("Not yet implemented")
    }

    override fun updateCameraPreview(data: ByteArray) {
        TODO("Not yet implemented")
    }
}