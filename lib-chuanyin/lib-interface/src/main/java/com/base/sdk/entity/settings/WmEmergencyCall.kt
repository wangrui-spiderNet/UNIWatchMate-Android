package com.base.sdk.entity.settings

import com.base.sdk.entity.apps.WmContacts

/**
 * Emergency call(紧急联系人)
 */
class WmEmergencyCall(
    /**
     * Whether to enable emergency contact(是否启用紧急联系人)
     */
    var isEnabled: Boolean,
    /**
     * Emergency contacts(紧急联系人列表)
     */
    val emergencyContacts: MutableList<WmContacts>
) {
    fun addContact(contact: WmContacts) {
        emergencyContacts.add(contact)
    }

    fun removeContact(contact: WmContacts) {
        emergencyContacts.remove(contact)
    }
}
