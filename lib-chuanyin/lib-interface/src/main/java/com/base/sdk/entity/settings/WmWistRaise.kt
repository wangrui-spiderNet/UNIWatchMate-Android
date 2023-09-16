package com.base.sdk.entity.settings

data class WmWistRaise(
    /**
     * Whether to enable wrist lift(是否开启抬腕)
     */
    var isScreenWakeEnabled: Boolean
) {
    override fun toString(): String {
        return "WmWistRaise(isScreenWakeEnabled=$isScreenWakeEnabled)"
    }
}