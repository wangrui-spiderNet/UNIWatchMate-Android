package com.base.sdk.entity.settings

data class AppView(
    var total: Int = 0,
    var list: List<ListBean>? = null
){
    override fun toString(): String {
        return "AppViewBean(total=$total, list=$list)"
    }
}

data class ListBean(
    var id: Int = 0,
    var name: String? = null,
    var sort: Int = 0,
    var using: Int = 0
){
    override fun toString(): String {
        return "ListBean(id=$id, name=$name, sort=$sort, using=$using)"
    }
}
