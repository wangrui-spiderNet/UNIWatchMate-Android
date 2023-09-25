package com.sjbt.sdk.entity

import java.nio.ByteBuffer

object NodeEncoder {

    fun buildReadMsg(node: Node): ByteArray {
        val byteArray: ByteArray = ByteArray(0)

        val byteBuffer: ByteBuffer = ByteBuffer.wrap(byteArray)
        byteBuffer

        return byteArray
    }

    fun buildWriteMsg(node: Node): ByteArray {
        val byteBuffer: ByteBuffer = ByteBuffer.allocate(byteArray)
        byteBuffer.put(node.requestId)
        byteBuffer.putInt(node.packageSeq)
        byteBuffer.put(node.request_type)
        byteBuffer.put(node.package_limit)
        byteBuffer.put(node.item_count)

        return byteBuffer.array()
    }

}