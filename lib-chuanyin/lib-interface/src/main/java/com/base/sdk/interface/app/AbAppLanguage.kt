package com.base.sdk.`interface`.app

import com.base.sdk.entity.apps.WmLanguage
import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * App - Language（应用模块 - 语言）
 */
abstract class AbAppLanguage : IWmSupport {
    /**
     * 同步语言列表
     */
    abstract var syncLanguageList : Single<List<WmLanguage>>

    /**
     * 设定语言
     */
    abstract fun setLanguage(language: WmLanguage): Single<WmLanguage>
}