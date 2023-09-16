package com.base.sdk.`interface`.app

import com.base.sdk.entity.apps.WmContacts
import com.base.sdk.entity.apps.WmWeatherForecast
import com.base.sdk.entity.common.Sport
import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

abstract class AbAppFind :IWmSupport {

    /**
     * find mobile(查找手机)
     * @return 0:连续响 其它：响铃次数
     */
    abstract fun observeFindMobile(): Observable<Int>

    /**
     * stop find mobile(停止查找手机)
     * @return 0:停止声音和关闭界面 1:停止声音 2:关闭界面
     */
    abstract fun stopFindMobile(): Observable<Int>

    /**
     * find watch(查找手表)
     * @param ring_count 铃声次数
     */
    abstract fun findWatch(ring_count: Int): Observable<Boolean>

    /**
     * stop find watch(停止查找手表)
     *
     * @param flag 0:停止声音和关闭界面 1:停止声音 2:关闭界面
     * @return
     */
    abstract fun stopFindWatch(flag: Int): Observable<Boolean>
}