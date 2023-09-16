package com.base.sdk.`interface`.app

import com.base.sdk.entity.apps.WmContacts
import com.base.sdk.`interface`.IWmSupport
import io.reactivex.rxjava3.core.Observable

abstract class AbAppContact :IWmSupport {

    abstract fun syncContactList(): Observable<List<WmContacts>>
    abstract fun addContact(contact: WmContacts): Observable<WmContacts>
    abstract fun deleteContact(contact: WmContacts): Observable<WmContacts>
}