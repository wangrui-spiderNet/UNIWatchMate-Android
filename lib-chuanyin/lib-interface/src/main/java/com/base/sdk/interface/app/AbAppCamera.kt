package com.base.sdk.`interface`.app

import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * 应用模块-相机
 */
abstract class AbAppCamera :IWmSupport {

    /**
     * 监听设备端相机状态
     */
    abstract fun observeCameraState(): Observable<Boolean>

    /**
     * 监听相机端闪光灯状态
     */
    abstract fun observeCameraFlash(): Observable<Int>

    /**
     * 相机闪光灯设置
     */
    abstract fun cameraFlashSwitch(type: Int): Observable<Int>

    /**
     * 相机前后摄像头监听
     */
    abstract fun observeCameraFrontBack(): Observable<Boolean>

    /**
     * 设置相机前后摄像头
     */
    abstract fun cameraBackSwitch(isBack: Boolean): Observable<Boolean>

    /**camera preview 相机预览相关**/
    /**
     * 是否支持相机预览
     */
    abstract fun isCameraPreviewEnable(): Boolean

    /**
     * 相机预览是否准备好
     */
    abstract fun isCameraPreviewReady(): Single<Boolean>

    /**
     * 更新相机预览
     */
    abstract fun updateCameraPreview(data: ByteArray)

}