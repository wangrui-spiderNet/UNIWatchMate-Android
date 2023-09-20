package com.sjbt.sdk.utils;

import com.sjbt.sdk.uparser.UparserJni;
import com.sjbt.sdk.uparser.model.JpgInfo;
import com.sjbt.sdk.uparser.model.UpPartitionInfo;

public class UParseUtil {
    private final String TAG = UParseUtil.class.getSimpleName();
    private static UParseUtil _instance;
    public UparserJni upaserJni = null;

    private UParseUtil() {
        upaserJni = new UparserJni();
    }

    public static UParseUtil getInstance() {
        if (_instance == null) {
            synchronized (UParseUtil.class) {
                if (_instance == null) {
                    _instance = new UParseUtil();
                }
            }
        }
        return _instance;
    }

    private UpPartitionInfo getPartInfo(long upContext, String tag, byte[] partData) {
        UpPartitionInfo partInfo = new UpPartitionInfo();
        partInfo.setPartitionData(partData);
        partInfo.setPartitionLength(partData.length);
        partInfo.setTag(tag);
        byte orgData[] = upaserJni.upPartitionGetData(upContext, tag);
        int len = (orgData == null) ? 0 : orgData.length;
        BtUtils.logBlueTooth("org data length:" + len);

        return partInfo;
    }

    /**
     * 保存新的eq和tone分区数据到up数据并保存为新文件
     *
     * @param eqBytes     eq分区数据
     * @param toneBytes   tone分区数据
     * @param filePath    原始up文件路径
     * @param newFilePath 新生成的up文件路径
     * @return 0：成功 其他：失败
     */
    public int savePartDataToUpFile(byte[] indexTable, byte[] eqBytes, byte[] toneBytes, String filePath, String newFilePath) {

        long upContext = upaserJni.upInit(filePath);
        BtUtils.logBlueTooth("upContext:" + upContext);

        if (eqBytes == null || eqBytes.length == 0) {
            BtUtils.logBlueTooth("error: data is null.");
            return -3;
        }

        UpPartitionInfo partInfo = getPartInfo(upContext, UparserJni.TAG_EQ2P, eqBytes);
        int ret = upaserJni.upPartitionSet(upContext, partInfo);
        BtUtils.logBlueTooth("eq upPartitionSet:" + ret);

        partInfo = getPartInfo(upContext, UparserJni.TAG_TONE, toneBytes);
        ret = upaserJni.upPartitionSet(upContext, partInfo);
        BtUtils.logBlueTooth("tone upPartitionSet:" + ret);

        partInfo = new UpPartitionInfo();
        partInfo.setPartitionData(indexTable);
        partInfo.setPartitionLength(indexTable.length);
        partInfo.setTag(UparserJni.TAG_OTAP);
        BtUtils.logBlueTooth("otap upPartitionKeep indexTable.length:" + indexTable.length);
        ret = upaserJni.upPartitionKeep(upContext, partInfo);
        BtUtils.logBlueTooth("otap upPartitionKeep:" + ret);

        if (ret == 0) {
            ret = upaserJni.upSave(upContext, newFilePath);
            BtUtils.logBlueTooth("upSave:" + ret);
        }
        upaserJni.upDeinit(upContext);

        return ret;
    }

    /**
     * 从dial中提取jpg图片和信息
     * @param filePath
     * @param jpgInfo
     * @return
     */
    public int getJpgFromDial(String filePath, JpgInfo jpgInfo) {
        return upaserJni.peekJpgFromDial(filePath, jpgInfo);
    }

}

