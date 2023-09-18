package com.base.sdk.`interface`.app

import com.base.sdk.entity.common.WmSportType
import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
/**
 * 应用模块-运动
 */
abstract class AbAppSport :IWmSupport {

    /**
     * syncSportList 同步运动列表
     */
    abstract fun syncSportList(): Observable<List<WmSportType>>

    /**
     * addSport 增加运动项目
     * TODO 待定，这里有一点不太确定，添加体育项是否需要传输运动相关文件，如果是设备端预安装了所有体育项，只需要设定体育Id就行
     */
    abstract fun addSport(sport: WmSportType): Observable<WmSportType>

    /**
     * deleteSport 删除运动项目
     */
    abstract fun deleteSport(sport: WmSportType): Observable<WmSportType>

    /**
     * sortFixedSportList 运动列表排序
     */
    abstract fun sortFixedSportList(list: List<WmSportType>): Single<Boolean>

}