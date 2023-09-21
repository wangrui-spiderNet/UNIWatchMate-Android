package com.sjbt.sdk.app

import com.base.sdk.entity.apps.WmLanguage
import com.base.sdk.`interface`.app.AbAppLanguage
import io.reactivex.rxjava3.core.Observable

class AppLanguage : AbAppLanguage() {
    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override var syncLanguageList: Observable<List<WmLanguage>>
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun setLanguage(language: WmLanguage): Observable<WmLanguage> {
        TODO("Not yet implemented")
    }
}