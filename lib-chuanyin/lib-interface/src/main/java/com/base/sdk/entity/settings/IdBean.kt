package com.base.sdk.entity.settings

internal interface IdBean {
    var id: Int

    companion object {
        internal fun findNewId(list: List<IdBean>?, min: Int, max: Int): Int {
            if (list.isNullOrEmpty()) return 0
            //查找没有使用的Id
            var id = -1
            for (i in min..max) {
                var used = false
                for (bean in list) {
                    if (i == bean.id) {
                        used = true
                        break
                    }
                }
                if (!used) {
                    id = i
                    break
                }
            }
            return id
        }
    }
}