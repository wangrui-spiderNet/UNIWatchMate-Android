package com.sjbt.sdk.app

import com.base.sdk.entity.apps.WmContact
import com.base.sdk.`interface`.app.AbAppContact
import io.reactivex.rxjava3.core.Observable

class AppContact : AbAppContact() {
    override fun isSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override var syncContactList: Observable<List<WmContact>>
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun addContact(contact: WmContact): Observable<WmContact> {
        TODO("Not yet implemented")
    }

    override fun deleteContact(contact: WmContact): Observable<WmContact> {
        TODO("Not yet implemented")
    }

    override fun setEmergencyContact(contacts: WmContact): Observable<WmContact> {
        TODO("Not yet implemented")
    }
}