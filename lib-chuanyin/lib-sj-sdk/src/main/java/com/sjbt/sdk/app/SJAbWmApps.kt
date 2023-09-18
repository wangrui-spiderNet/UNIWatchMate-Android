package com.sjbt.sdk.app

import com.base.sdk.`interface`.app.*

class SJAbWmApps : AbWmApps() {

    override var appAlarm: AbAppAlarm
        get() = AppAlarm()
        set(value) {}

    override var appCamera: AbAppCamera
        get() = AppCamera()
        set(value) {}

    override var appContact: AbAppContact
        get() = TODO("Not yet implemented")
        set(value) {}

    override var appFind: AbAppFind
        get() = TODO("Not yet implemented")
        set(value) {}

    override var appWeather: AbAppWeather
        get() = TODO("Not yet implemented")
        set(value) {}

    override var appSport: AbAppSport
        get() = TODO("Not yet implemented")
        set(value) {}

    override var appNotification: AbAppNotification
        get() = TODO("Not yet implemented")
        set(value) {}

    override var appDial: AbAppDial
        get() = TODO("Not yet implemented")
        set(value) {}
}