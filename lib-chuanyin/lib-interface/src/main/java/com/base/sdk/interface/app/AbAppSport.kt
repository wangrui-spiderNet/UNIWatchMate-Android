package com.base.sdk.`interface`.app

import com.base.sdk.entity.common.Sport
import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

abstract class AbAppSport :IWmSupport {

    /**
     * 运动项目
     */
    abstract fun syncSportList(): Observable<List<Sport>>
    abstract fun addSport(sport: Sport): Observable<Sport>
    abstract fun deleteSport(sport: Sport): Observable<Sport>
    abstract fun sortFixedSportList(list: List<Sport>): Single<Boolean>

}