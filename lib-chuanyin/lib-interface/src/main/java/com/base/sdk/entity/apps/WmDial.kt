package com.base.sdk.entity.apps

data class WmDial(var total: Int, var list: List<DialItem>)
data class DialItem(var id: String, var using: Int)