package com.base.sdk.`interface`.app

import com.base.sdk.entity.apps.WmContact
import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Observable

/**
 * 应用模块-通讯录
 */
abstract class AbAppContact :IWmSupport {

    /**
     * syncContactList 同步通讯录列表
     */
    abstract var syncContactList : Observable<List<WmContact>>

    /**
     * addContact 添加通讯录
     */
    abstract fun addContact(contact: WmContact): Observable<WmContact>

    /**
     * deleteContact 删除通讯录
     */
    abstract fun deleteContact(contact: WmContact): Observable<WmContact>

    /**
     * setEmergencyContact 设置紧急联系人
     */
    abstract fun setEmergencyContact(contacts: WmContact): Observable<WmContact>

}