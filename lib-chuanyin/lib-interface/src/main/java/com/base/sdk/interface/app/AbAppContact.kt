package com.base.sdk.`interface`.app

import com.base.sdk.entity.apps.WmContact
import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Observable

abstract class AbAppContact :IWmSupport {

    abstract fun syncContactList(): Observable<List<WmContact>>
    abstract fun addContact(contact: WmContact): Observable<WmContact>
    abstract fun deleteContact(contact: WmContact): Observable<WmContact>
    abstract fun setEmergencyContact(contacts: WmContact): Observable<WmContact>

}