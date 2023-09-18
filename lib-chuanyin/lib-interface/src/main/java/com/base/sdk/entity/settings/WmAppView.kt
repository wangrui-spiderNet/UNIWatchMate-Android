package com.base.sdk.entity.settings

/**
 * appview(应用视图)
 */
data class WmAppView(
    var total: Int = 0,
    var list: List<ListBean>? = null
) {
    override fun toString(): String {
        return "AppViewBean(total=$total, list=$list)"
    }
}

data class ListBean(
    var id: Int = 0,//唯一id
    var name: String? = null,//名字
    var sort: Int = 0,//顺序
    var using: Int = 0//当前
) {
    override fun toString(): String {
        return "ListBean(id=$id, name=$name, sort=$sort, using=$using)"
    }
}
