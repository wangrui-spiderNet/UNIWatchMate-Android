package com.sjbt.sdk.entity

data class Node(
    val requestId: ByteArray,//2 bytes
    val packageSeq: Int,//4 bytes  分包序号
    val request_type: Byte,//1 byte
    val package_limit: ByteArray,//2 bytes 单包大小限制
    val item_count: Byte,//1 byte  节点个数
    val nodeItems: Array<NodeItem>//
){
    var totalLen= requestId.size+4+1+package_limit.size+1
//
//    nodeItems.forEach {
//        totalLen += it.data.size
//    }

}

data class NodeItem(
    val nodeId: ByteArray,//节点ID 4 bytes
    val reserved: ByteArray,//保留数据
    val data_fmt: Byte,//数据类型
    val data_len: ByteArray,//数据字节长度
    val data: ByteArray//节点数据类型
)

enum class ErrCode {
    ERRCODE_NOERR,
    ERRCODE_NODATA,
    ERRCODE_DENIED
}

enum class DataFormat {
    FMT_BINARY,
    FMT_PLAINTEXT,
    FMT_JSON,
    FMT_NODATA,
    FMT_ERRCODE
}

enum class RequestType {
    ACCESS_READ,
    ACCESS_WRITE,
    ACCESS_EXECUTE
}

enum class ResponseType {
    RESPONSE_EACH,
    RESPONSE_ALL_OK,
    RESPONSE_ALL_FAIL,
}

val REQUEST_TYPE: ByteArray = byteArrayOf(0x00, 0x00)

const val CONNECT_P = 0x01
const val CONNECT_C_BIND = 0x01
const val CONNECT_C_UNBIND = 0x02


