package com.sjbt.sdk.uparser;


import com.sjbt.sdk.uparser.model.JpgInfo;
import com.sjbt.sdk.uparser.model.UpPartitionInfo;

public class UparserJni {

    //标签定义
    public static final String TAG_FIRM = "FIRM";
    public static final String TAG_EQ2P = "EQ2P";
    public static final String TAG_TONE = "TONE";
    public static final String TAG_PSMP = "PSMP";
    public static final String TAG_OTAP = "OTAP";
    //其他标签......

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("btsdk-lib");
    }

    /**
     * 初始化up分析器，必须和upDeinit成对执行
     * @param upFilePath 要分析或保存的up文件路径
     * @return 分析器的上下文，对应c层的上下文指针，其指向要操作的文件内容的内存地址，很重要！
     */
    public native long upInit(String upFilePath);

    public native long upInitByBytes(byte[] upBytes);

    /**
     * 销毁分析器，必须和upInit成对执行
     * @param upContext 分析器的上下文，其指向要操作的文件内容的内存地址
     */
    public native void upDeinit(long upContext);

    /**
     * 读取分区信息及数据
     * @param upContext 分析器的上下文，其指向要操作的文件内容的内存地址
     * @param tag 分区标签字符串
     * @param needData 是否需要读取分区的内容数据，如果为false，仅仅读取分区信息而没有数据
     * @return 分区信息及数据对象
     */
    public native UpPartitionInfo upPartitionGet(long upContext, String tag, boolean needData);

    /**
     * 仅读取分区数据
     * @param upContext 分析器的上下文，其指向要操作的文件内容的内存地址
     * @param tag 分区标签字符串
     * @return 分区数据
     */
    public native byte[] upPartitionGetData(long upContext, String tag);

    /**
     * 写入数据到up文件的某个分区，此时没有真正写入文件，只是保存在内存中。
     * @param upContext
     * @param partionInfo 要写入的数据对象，其中的tag，partitionLength，partitionData为必填项，
     *                    partitionData的长度和partitionLength的值相同
     * @return 0：成功 其它：失败
     */
    public native int upPartitionSet(long upContext, UpPartitionInfo partionInfo);

    public native int upPartitionKeep(long upContext, UpPartitionInfo partionInfo);


    public native byte[] upGetAllBytes(long upContext);

    /**
     * 保存upContext指向的内存中的数据到文件中。
     * @param upContext 分析器的上下文，其指向要操作的文件内容的内存地址
     * @param upFilePath 要保存的文件路径
     * @return 0：成功 其它：失败
     */
    public native int upSave(long upContext, String upFilePath);


    public native int peekJpgFromDial(String dialFilePath, JpgInfo jpgInfo);

}
