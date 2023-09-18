package com.base.sdk.`interface`.app

/**
 * 应用模块功能聚合
 */
abstract class AbWmApps {
    /**
     * 闹钟功能
     */
    abstract var appAlarm: AbAppAlarm

    /**
     * 相机功能
     */
    abstract var appCamera: AbAppCamera

    /**
     * 通讯录
     */
    abstract var appContact: AbAppContact

    /**
     * 查找功能
     */
    abstract var appFind: AbAppFind

    /**
     * 天气功能
     */
    abstract var appWeather: AbAppWeather

    /**
     * 运动功能
     */
    abstract var appSport: AbAppSport

    /**
     * 通知功能
     */
    abstract var appNotification: AbAppNotification

    /**
     * 表盘
     */
    abstract var appDial: AbAppDial

    /**
     * 语言
     */
    abstract var appLanguage: AbAppLanguage

}