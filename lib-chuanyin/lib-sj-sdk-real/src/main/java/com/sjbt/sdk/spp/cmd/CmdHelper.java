package com.sjbt.sdk.spp.cmd;

import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8001;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8002;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8003;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8004;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8005;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8006;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8007;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8008;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8009;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_800A;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_800B;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_800C;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_800D;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_800E;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_800F;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8010;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8011;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8012;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8014;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8017;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8018;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8019;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_801A;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_801B;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_801C;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_801E;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8021;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8022;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8023;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8025;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_8027;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_802A;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_802C;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ID_802D;
import static com.sjbt.sdk.spp.cmd.CmdConfig.CMD_ORDER_ARRAY;
import static com.sjbt.sdk.spp.cmd.CmdConfig.DIVIDE_N_2;
import static com.sjbt.sdk.spp.cmd.CmdConfig.DIVIDE_N_JSON;
import static com.sjbt.sdk.spp.cmd.CmdConfig.DIVIDE_Y_E_2;
import static com.sjbt.sdk.spp.cmd.CmdConfig.DIVIDE_Y_F_2;
import static com.sjbt.sdk.spp.cmd.CmdConfig.DIVIDE_Y_M_2;
import static com.sjbt.sdk.spp.cmd.CmdConfig.HEAD_CAMERA_PREVIEW;
import static com.sjbt.sdk.spp.cmd.CmdConfig.HEAD_COLLECT_DEBUG_DATA;
import static com.sjbt.sdk.spp.cmd.CmdConfig.HEAD_COMMON;
import static com.sjbt.sdk.spp.cmd.CmdConfig.HEAD_FILE_OPP;
import static com.sjbt.sdk.spp.cmd.CmdConfig.HEAD_FILE_SPP_A_2_D;
import static com.sjbt.sdk.spp.cmd.CmdConfig.HEAD_FILE_SPP_D_2_A;
import static com.sjbt.sdk.spp.cmd.CmdConfig.HEAD_SPORT_HEALTH;
import static com.sjbt.sdk.spp.cmd.CmdConfig.HEAD_VERIFY;
import static com.sjbt.sdk.spp.cmd.CmdConfig.HEX_FFFF;
import static com.sjbt.sdk.spp.cmd.CmdConfig.TRANSFER_KEY;
import static com.sjbt.sdk.spp.cmd.CmdConfig.getRandomNumber;
import static com.sjbt.sdk.utils.BtUtils.byte2short;
import static com.sjbt.sdk.utils.BtUtils.hexStringToByteArray;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.base.sdk.entity.AlarmBean;
import com.base.sdk.entity.MsgBean;
import com.base.sdk.entity.NotificationMessageBean;
import com.base.sdk.entity.OtaCmdInfo;
import com.base.sdk.entity.SportInitInfo;
import com.base.sdk.entity.TimeSyncBean;
import com.base.sdk.entity.WeatherBean;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.Gson;
import com.sjbt.sdk.utils.BtUtils;
import com.sjbt.sdk.utils.ByteUtil;
import com.sjbt.sdk.utils.FileUtils;
import com.sjbt.sdk.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class CmdHelper {

    //对CMD_ORDER.length 取余后作为 CMD_ORDER 的索引下标
    public static int command_index = 0;
    private static int key1;
    private static String mKey1, mKey2;
    private static String mKeyData1;
    private static String mKeyData2;
    private static final Gson gson = GsonUtils.getGson();

    public static byte[] constructCmd(byte head, short cmd_id, byte divideType, int offset, int crc, byte[] payload) {

        int payLoadLength = payload == null ? 0 : payload.length;

        ByteBuffer byteBuffer = ByteBuffer.allocate(16 + payLoadLength);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);//采用小端

        //TYPE
        byteBuffer.put(head);
        byteBuffer.put(CMD_ORDER_ARRAY[command_index % CMD_ORDER_ARRAY.length]);
        byteBuffer.putShort((short) (cmd_id & TRANSFER_KEY));//携带方向

        //Length
        byteBuffer.put(divideType);
        byteBuffer.put((byte) 0x00);//Resolved
        byteBuffer.putShort((short) payLoadLength);

        //Offset
        byteBuffer.putInt(offset);

        //CRC
        byteBuffer.putInt(crc);

        //Payload
        if (payload != null) {
            byteBuffer.put(payload);
        }

        byteBuffer.flip();
        command_index++;
        return byteBuffer.array();
    }


    /**
     * 获取JSON格式的 Payload数据
     *
     * @param msg_result
     * @return
     */
    public static MsgBean getPayLoadJson(byte[] msg_result) {
        MsgBean msgBean = new MsgBean();
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(msg_result);
            msgBean.head = byteBuffer.get();
            msgBean.cmdOrder = byteBuffer.get();
            byte[] cmdId = new byte[2];
            System.arraycopy(msg_result, 2, cmdId, 0, cmdId.length);

            msgBean.cmdIdStr = BtUtils.bytesToHexString(cmdId);

            byte temp = cmdId[0];
            cmdId[0] = cmdId[1];
            cmdId[1] = temp;

            cmdId[0] = 0x00;
            msgBean.cmdId = byte2short(cmdId);
//            LogUtils.logBlueTooth("返回命令cmdId:" + msgBean.cmdId);

            byte divideType = byteBuffer.get(4);
            msgBean.divideType = divideType;

//            byte[] len = new byte[2];
//            System.arraycopy(msg_result, 6, len, 0, len.length);
//
//            byte a = len[0];
//            byte b = len[1];
//
//            len[0] = b;
//            len[1] = a;

//            String lenHex = BtUtils.bytesToHexString(len);
//            int length = BtUtils.hexToInt(lenHex);
            int payLoadLength = msg_result.length - 16;
//            LogUtils.logBlueTooth("lenHex:" + lenHex);
//            LogUtils.logBlueTooth("length:" + length);
//            LogUtils.logBlueTooth("divideType:" + divideType);

            msgBean.payloadLen = payLoadLength;
            byte[] offsetArray = new byte[4];
            System.arraycopy(msg_result, 8, offsetArray, 0, offsetArray.length);
            msgBean.offset = ByteUtil.bytesToInt(offsetArray, ByteOrder.LITTLE_ENDIAN);

            byte[] crcArray = new byte[4];
            System.arraycopy(msg_result, 12, crcArray, 0, crcArray.length);
            msgBean.crc = ByteUtil.bytesToInt(crcArray, ByteOrder.LITTLE_ENDIAN);

//            LogUtils.logBlueTooth("offset:" + msgBean.offset);
//            LogUtils.logBlueTooth("crc:" + msgBean.crc);
//            LogUtils.logBlueTooth("length:" + length);

            if (msgBean.divideType == DIVIDE_N_2 || msgBean.divideType == DIVIDE_N_JSON) {
                if (payLoadLength > 0) {
                    byte[] payload = new byte[payLoadLength];
                    System.arraycopy(msg_result, 16, payload, 0, payLoadLength);
                    msgBean.payload = payload;
                    if (divideType == DIVIDE_N_JSON) {
                        String payloadJson = new String(payload, StandardCharsets.UTF_8);
                        msgBean.payloadJson = payloadJson;
                    }
                }
            } else {

                byte[] divideIndexArray = new byte[4];
                System.arraycopy(msg_result, 16, divideIndexArray, 0, divideIndexArray.length);

                msgBean.divideIndex = ByteUtil.bytesToInt(divideIndexArray);

//                LogUtils.logBlueTooth("分包序号HEX：" + BtUtils.bytesToHexString(divideIndexArray));
//                LogUtils.logBlueTooth("分包序号INT：" + msgBean.divideIndex);

                byte[] payload = new byte[payLoadLength - 4];
                System.arraycopy(msg_result, 20, payload, 0, payload.length);

                msgBean.payload = payload;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return msgBean;
    }

    public static List<byte[]> packageFileToCmdList(@NonNull File file, int cell_length) {
        byte divide;
        int otaPckCrc = 0;
        byte[] dataArray = FileUtils.readFileBytes(file);

        List<byte[]> otaCmdArrayList = new ArrayList<>();

        if (!file.exists()) {
            return otaCmdArrayList;
        }

        int count = dataArray.length / cell_length;
        int lastDataLength = dataArray.length % cell_length;

        if (lastDataLength != 0) {
            count = count + 1;
        }

        for (int i = 0; i < count; i++) {
            OtaCmdInfo info = new OtaCmdInfo();

            if (i != count - 1) {
                info.offSet = i * cell_length;

                info.payload = new byte[cell_length];

                System.arraycopy(dataArray, i * cell_length, info.payload, 0, cell_length);
            } else {
                if (lastDataLength == 0) {
                    info.offSet = i * cell_length;
                    info.payload = new byte[cell_length];

                    System.arraycopy(dataArray, i * cell_length, info.payload, 0, cell_length);
                } else {
                    info.offSet = i * cell_length;
                    info.payload = new byte[lastDataLength];

                    System.arraycopy(dataArray, i * cell_length, info.payload, 0, lastDataLength);
                }
            }

            if (i == 0) {
                otaPckCrc = BtUtils.getCrc(HEX_FFFF, info.payload, info.payload.length);
                divide = DIVIDE_Y_F_2;
            } else {
                if (i == count - 1) {
                    divide = DIVIDE_Y_E_2;
                } else {
                    divide = DIVIDE_Y_M_2;
                }

                otaPckCrc = BtUtils.getCrc(otaPckCrc, info.payload, info.payload.length);
            }

            info.crc = otaPckCrc;

            otaCmdArrayList.add(CmdHelper.getTransfer03Cmd(i, info, divide));
        }

        return otaCmdArrayList;
    }

    /**
     * 计算出key2
     *
     * @param data
     */
    public static String getTheAccumulatedValueAnd(String data) {
        if (TextUtils.isEmpty(data)) {
            return "";
        }
        int total = 0;
        int len = data.length();
        int num = 0;
        while (num < len) {
            String s = data.substring(num, num + 2);
            total += Integer.parseInt(s, 16);
            num = num + 2;
        }
        /**
         * 用256求余最大是255，即16进制的FF
         */
        String hex = Integer.toHexString(total);
        return hex;
    }

    /**
     * 新协议 获取校验命令
     *
     * @return
     */
    public static byte[] getVerifyPayload() {
        String[] verificationArray = new String[5];

        verificationArray[0] = getRandomNumber(14);
        verificationArray[1] = getRandomNumber(2); //密钥
        verificationArray[2] = getRandomNumber(32);
        verificationArray[3] = getRandomNumber(16); //异或原参

        byte[] bytes = hexStringToByteArray(verificationArray[2]);
        key1 = Integer.parseInt(verificationArray[1], 16);

        mKey1 = BtUtils.intToHex(key1);
//        LogUtils.logBlueTooth("APP加密的Key1:" + mKey1);

        verificationArray[4] = BtUtils.bytesToHexString(bytes); //未加密数据

//        LogUtils.logBlueTooth("APP未加密的数据:" + verificationArray[4]);

        verificationArray[2] = BtUtils.bytesToHexString(BtUtils.encryptData(key1, bytes, bytes.length));

//        LogUtils.logBlueTooth("APP加密后的数据:" + verificationArray[2]);

        mKeyData1 = verificationArray[3];
        mKeyData2 = verificationArray[4];

        mKey2 = BtUtils.getTheAccumulatedValueAnd(verificationArray[2]);//加密后累加

//        LogUtils.logBlueTooth("APP未加密的数据_mKeyData2 :" + mKeyData2);
//        LogUtils.logBlueTooth("APP解密累加后 KEY2:" + mKey2);

        StringBuilder sbVerify = new StringBuilder();

        sbVerify.append(verificationArray[0]);
        sbVerify.append(verificationArray[1]);
        sbVerify.append(verificationArray[2]);
        sbVerify.append(verificationArray[3]);

        return hexStringToByteArray(sbVerify.toString());
    }


    /**
     * 解密并验证数据
     *
     * @param data 从设备返回的数据
     * @return
     */
    public static boolean verificationCmd(String data) {

//        LogUtils.logBlueTooth("verificationCmd 设备返回payload：" + data);

        String substring = data.substring(32, 96);
//        LogUtils.logBlueTooth("verificationCmd: new 10-2F： " + substring);


        byte[] bytes = hexStringToByteArray(substring);
        int key = Integer.parseInt(mKey2, 16);
        //解密数据
        String s2 = BtUtils.bytesToHexString(BtUtils.encryptData(key, bytes, bytes.length));
//        LogUtils.logBlueTooth("verificationCmd:解密后的10-2F： " + s2);
        //异或
        String substring1 = s2.substring(0, 32);
        String substring2 = s2.substring(32);
        byte[] bytes1 = hexStringToByteArray(substring1);
        byte[] bytes2 = hexStringToByteArray(substring2);
        byte[] bytes3 = hexStringToByteArray(mKeyData1);
        byte[] bytes4 = new byte[bytes1.length + bytes2.length];
        for (int j = 0; j < bytes1.length; j++) {
            bytes4[j] = (byte) (bytes1[j] ^ bytes3[j]);
        }
        for (int j = 0; j < bytes2.length; j++) {
            bytes4[j + bytes2.length] = (byte) (bytes2[j] ^ bytes3[j]);
        }

//        LogUtils.logBlueTooth("解密的key1：" + key + " | - key2:" + key);

        //解密
        String s3 = BtUtils.bytesToHexString(BtUtils.encryptData(key1, bytes4, bytes4.length));
//        LogUtils.logBlueTooth("verificationCmd:异或后的数据： " + BtUtils.bytesToHexString(bytes4));
//        LogUtils.logBlueTooth("verificationCmd:加密后的数据： " + s3);

        return BtUtils.bytesToHexString(bytes4).equalsIgnoreCase(mKeyData2);
    }

    /**
     * 新协议 握手命令
     *
     * @return 组装好的握手命令
     */
    public static byte[] getBiuShakeHandsCmd() {

        byte[] payload = hexStringToByteArray(getRandomNumber(61));
        int crc = BtUtils.getCrc(HEX_FFFF, payload, payload.length);
        LogUtils.logBlueTooth("发送握手消息:");
        return constructCmd(HEAD_VERIFY, CMD_ID_8001, DIVIDE_N_2, 0, crc, payload);
    }

    /**
     * 新协议 校验命令
     *
     * @return 组装好的校验命令
     */
    public static byte[] getBiuVerifyCmd() {
        byte[] payload = getVerifyPayload();
        int crc = BtUtils.getCrc(HEX_FFFF, payload, payload.length);
        LogUtils.logBlueTooth("2.发送校验信息:");
        return constructCmd(HEAD_VERIFY, CMD_ID_8002, DIVIDE_N_2, 0, crc, payload);
    }

    /**
     * 获取同步时间命令
     *
     * @return
     */
    public static byte[] getSyncTimeCmd() {
        LogUtils.logBlueTooth("4.5 同步时间信息:");
        TimeSyncBean timeSyncBean = new TimeSyncBean();

        timeSyncBean.setCurrDate(TimeUtils.getNowString(TimeUtils.getSafeDateFormat("yyyy-MM-dd")));
        timeSyncBean.setTimeZoo(SimpleTimeZone.getDefault().getDisplayName(false, TimeZone.SHORT));
        timeSyncBean.setCurrTime(TimeUtils.getNowString(TimeUtils.getSafeDateFormat("yyyy-MM-dd HH:mm:ss")));
        timeSyncBean.setTimestamp(System.currentTimeMillis() / 1000);
        timeSyncBean.setTimeformat(1);
        LogUtils.logCommon("时间同步：" + timeSyncBean);

        byte[] payload = gson.toJson(timeSyncBean).getBytes(StandardCharsets.UTF_8);
        int crc = BtUtils.getCrc(HEX_FFFF, payload, payload.length);

        return constructCmd(HEAD_COMMON, CMD_ID_8007, DIVIDE_N_JSON, 0, crc, payload);
    }

    /**
     * 获取基本信息
     *
     * @return
     */
    public static byte[] getBaseInfoCmd() {
        LogUtils.logBlueTooth("3.发送基本信息:");
        return CmdHelper.constructCmd(HEAD_COMMON, CMD_ID_8001, DIVIDE_N_JSON, 0, 0, null);
    }

    /**
     * 获取APPView list
     *
     * @return
     */
    public static byte[] getAppViewList() {
        return CmdHelper.constructCmd(HEAD_COMMON, CMD_ID_8008, DIVIDE_N_2, 0, 0, null);
    }


    /**
     * 预备收集抬腕数据
     *
     * @return
     */
    public static byte[] getDebugDataPreState() {
        byte[] payload = new byte[1];
        payload[0] = 1;//1抬腕数据
        int crc = BtUtils.getCrc(HEX_FFFF, payload, payload.length);
        return CmdHelper.constructCmd(HEAD_COLLECT_DEBUG_DATA, CMD_ID_8001, DIVIDE_N_2, 0, crc, payload);
    }

    /**
     * 预备收集抬腕数据
     *
     * @return
     */
    public static byte[] getCollectDebugData(byte page) {
        byte[] payload = new byte[1];
        payload[0] = page;//1抬腕数据
        int crc = BtUtils.getCrc(HEX_FFFF, payload, payload.length);
        return CmdHelper.constructCmd(HEAD_COLLECT_DEBUG_DATA, CMD_ID_8002, DIVIDE_N_2, 0, crc, payload);
    }

    /**
     * 生成设置Appview的命令
     *
     * @param id
     * @return
     */
    public static byte[] setAppViewCmd(byte id) {
        byte[] payload = new byte[1];
        payload[0] = id;
        int crc = BtUtils.getCrc(HEX_FFFF, payload, payload.length);

        return CmdHelper.constructCmd(
                HEAD_COMMON,
                CMD_ID_8009,
                DIVIDE_N_2,
                0,
                crc,
                payload);
    }

    /**
     * 获取电池信息
     *
     * @return
     */
    public static byte[] getBatteryInfo() {
        LogUtils.logBlueTooth("发送电量信息:");
        return CmdHelper.constructCmd(HEAD_COMMON, CMD_ID_8003, DIVIDE_N_JSON, 0, 0, null);
    }

    /**
     * 获取状态信息CMD
     *
     * @return
     */
    public static byte[] getStatusCmd() {
        LogUtils.logBlueTooth("2.发送状态信息:");
        return CmdHelper.constructCmd(HEAD_COMMON, CMD_ID_8002, DIVIDE_N_2, 0, 0, null);
    }

    /**
     * 获取同步通知消息
     *
     * @param notificationMessageBean
     * @return
     */
    public static byte[] getNotificationCmd(NotificationMessageBean notificationMessageBean) {
        byte[] payload = gson.toJson(notificationMessageBean).getBytes(StandardCharsets.UTF_8);
        LogUtils.logBlueTooth("发送通知消息:" + gson.toJson(notificationMessageBean));
        return constructCmd(HEAD_COMMON, CMD_ID_8004, DIVIDE_N_JSON, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 设置闹钟
     *
     * @param alarmBean
     * @return
     */
    public static byte[] getSetAlarmCmd(AlarmBean alarmBean) {
        byte[] payload = new byte[4];
        payload[0] = (byte) alarmBean.open;
        payload[1] = (byte) alarmBean.hour;
        payload[2] = (byte) alarmBean.min;
        payload[3] = (byte) alarmBean.repeat;
        int crc = BtUtils.getCrc(HEX_FFFF, payload, payload.length);

        LogUtils.logBlueTooth("闹钟：" + alarmBean);
        return CmdHelper.constructCmd(
                HEAD_COMMON,
                CMD_ID_801C,
                DIVIDE_N_2,
                0,
                crc,
                payload);
    }

    /**
     * 获取闹钟
     *
     * @return
     */
    public static byte[] getCurrAlarmCmd() {

        return CmdHelper.constructCmd(
                HEAD_COMMON,
                CMD_ID_801E,
                DIVIDE_N_2,
                0,
                0,
                null);
    }

    /**
     * 获取密码设置状态
     *
     * @return
     */
    public static byte[] getPwdSetCmd() {
        LogUtils.logBlueTooth("发送查询密码设置");
        return CmdHelper.constructCmd(HEAD_COMMON, CMD_ID_800A, DIVIDE_N_2, 0, 0, null);
    }

    /**
     * 获取时间同步设置状态
     *
     * @return
     */
    public static byte[] getTimeSetCmd(int mode, int open) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mode", mode);
            jsonObject.put("open", open);
            String json = jsonObject.toString();
            LogUtils.logBlueTooth("4.5发送查询时间同步设置：" + json);

            byte[] payload = json.getBytes(StandardCharsets.UTF_8);
            int crc = BtUtils.getCrc(HEX_FFFF, payload, payload.length);
            byte[] msg = CmdHelper.constructCmd(HEAD_COMMON, CMD_ID_800C, DIVIDE_N_JSON, 0, crc, payload);

            return msg;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取一次设置多个初始化信息的命令
     *
     * @return
     */
    public static byte[] getSetAllSportInitCmd(SportInitInfo initInfo) {
        LogUtils.logBlueTooth("一次设置多个初始化信息:");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("height", initInfo.getHeight());
            jsonObject.put("weight", initInfo.getWeight());
            jsonObject.put("birthday", initInfo.getBirthday());
            jsonObject.put("gender", initInfo.getGender());
            jsonObject.put("heat_goal", initInfo.getHeat_goal_int());
            jsonObject.put("step_goal", initInfo.getStep_goal_int());
            jsonObject.put("dis_goal", initInfo.getDis_goal_int());
            String json = jsonObject.toString();

            byte[] payload = json.getBytes(StandardCharsets.UTF_8);
            int crc = BtUtils.getCrc(HEX_FFFF, payload, payload.length);
            byte[] msg = CmdHelper.constructCmd(HEAD_SPORT_HEALTH, CMD_ID_8005, DIVIDE_N_JSON, 0, crc, payload);
            return msg;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取设置密码
     *
     * @param msgSetType
     * @param password
     * @return
     */
    public static byte[] getSetPwdCmd(String password, int msgSetType) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", msgSetType);
            jsonObject.put("pwd", password);
            String json = jsonObject.toString();
            LogUtils.logCommon("设置密码：" + json);

            byte[] payload = json.getBytes(StandardCharsets.UTF_8);
            int crc = BtUtils.getCrc(HEX_FFFF, payload, payload.length);
            byte[] msg = CmdHelper.constructCmd(HEAD_COMMON, CMD_ID_800B, DIVIDE_N_JSON, 0, crc, payload);

            return msg;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 同步绑定状态
     *
     * @return
     */
    public static byte[] getBindStateCmd(byte state) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(state);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();

        return constructCmd(HEAD_COMMON, CMD_ID_800D, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    public static byte[] getCreateBondCmd() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put((byte) 1);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();

        return CmdHelper.constructCmd(HEAD_COMMON, CMD_ID_8012, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 获取取消绑定协议CMD
     *
     * @return
     */
    public static byte[] getCancelBindRequestCmd() {
        return CmdHelper.constructCmd(HEAD_COMMON, CMD_ID_8011, DIVIDE_N_2, 0, 0, null);
    }

    /**
     * 获取解绑协议CMD
     *
     * @return
     */
    public static byte[] getUnBindRequestCmd() {
        return constructCmd(HEAD_COMMON, CMD_ID_800E, DIVIDE_N_2, 0, 0, null);
    }

    /**
     * 获取DialList CMD
     *
     * @return
     */
    public static byte[] getDialListCmd(byte order) {
        LogUtils.logBlueTooth("9.从设备端获取我的表盘数据");
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(order);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();
        return constructCmd(HEAD_COMMON, CMD_ID_800F, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 获取DialList CMD
     *
     * @return
     */
    public static byte[] getDialStateCmd(String id) {
        byte[] idArray = id.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(idArray.length);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(idArray);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();
        return constructCmd(HEAD_COMMON, CMD_ID_8014, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 发送文件请求
     *
     * @return
     */
    public static byte[] getOppFileTransferCmd(byte file_type) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
//        byteBuffer.put(trans_type);
        byteBuffer.put(file_type);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();

        return CmdHelper.constructCmd(HEAD_FILE_OPP, CMD_ID_8001, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 是否维持OPP
     *
     * @return
     */
    public static byte[] getOppContinueCmd(byte opp) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(opp);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();

        return CmdHelper.constructCmd(HEAD_FILE_OPP, CMD_ID_8003, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 获取停止传输OPP
     *
     * @return
     */
    public static byte[] getOppFinishFileTransferCmd(byte type) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(type);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();

        return CmdHelper.constructCmd(HEAD_FILE_OPP, CMD_ID_8002, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 操作表盘 0x0 type:1设定 2删除
     *
     * @param type
     * @param dialId
     * @return
     */
    public static byte[] getDialActionCmd(byte type, String dialId) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + dialId.getBytes().length);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(type);
        byteBuffer.put(dialId.getBytes());
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();

        return CmdHelper.constructCmd(HEAD_COMMON, CMD_ID_8010, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 获取81命令
     *
     * @param type 1:音频文件 2:OTA（根据实际需求定义） 3:BIN
     * @return
     */
    public static byte[] getTransferFile01Cmd(byte type, int fileLen, int fileCount) {

        LogUtils.logBlueTooth("文件长度:" + fileLen);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + 4 + 1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(type);
        byteBuffer.putInt(fileLen);
        byteBuffer.put((byte) fileCount);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();

        return constructCmd(HEAD_FILE_SPP_A_2_D, CMD_ID_8001, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }


    /**
     * 文件长度和名称上报
     *
     * @param len  文件长度
     * @param name 文件名称
     * @return
     */
    public static byte[] getTransferFile02Cmd(int len, @NonNull String name) {

        byte[] nameByte = name.getBytes(Charset.defaultCharset());
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + nameByte.length);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(len);
        byteBuffer.put(nameByte);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();

        return constructCmd(HEAD_FILE_SPP_A_2_D, CMD_ID_8002, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 获取83命令
     *
     * @param otaCmdInfo
     * @return
     */
    public static byte[] getTransfer03Cmd(int process, OtaCmdInfo otaCmdInfo, byte divideType) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(otaCmdInfo.payload.length + 4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(process);
        byteBuffer.put(otaCmdInfo.payload);
        byteBuffer.flip();

        otaCmdInfo.payload = byteBuffer.array();

        otaCmdInfo.crc = BtUtils.getCrc(HEX_FFFF, otaCmdInfo.payload, otaCmdInfo.payload.length);

        LogUtils.logBlueTooth("发送消息序号：" + process + " 包长:" + otaCmdInfo.payload.length);

        byte[] msg = constructCmd(HEAD_FILE_SPP_A_2_D, CMD_ID_8003,
                divideType, otaCmdInfo.offSet, otaCmdInfo.crc, otaCmdInfo.payload);

        return msg;
    }

    /**
     * 获取83命令 确认是否成功
     *
     * @return
     */
    public static byte[] getTransfer04Cmd() {
        return constructCmd(HEAD_FILE_SPP_A_2_D, CMD_ID_8004,
                DIVIDE_N_2, 0, 0, null);
    }

    /**
     * App取消文件传输
     *
     * @return
     */
    public static byte[] getTransferCancelCmd() {
        return constructCmd(HEAD_FILE_SPP_A_2_D, CMD_ID_8005,
                DIVIDE_N_2, 0, 0, null);
    }

    /**
     * 从设备端向App端传数据
     *
     * @param type
     * @return
     */
    public static byte[] getTransferD2A01Cmd(byte type) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + 4 + 1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(type);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();

        return constructCmd(HEAD_FILE_SPP_D_2_A, CMD_ID_8001, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 文件长度和名称上报
     *
     * @param index 文件序号
     * @return
     */
    public static byte[] getTransferD2A02Cmd(byte index) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(index);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();

        return constructCmd(HEAD_FILE_SPP_D_2_A, CMD_ID_8002, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 83命令
     *
     * @param state   状态
     * @param process 序号
     * @return
     */
    public static byte[] getTransferD2A03Cmd(byte state, int process) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(state);
        byteBuffer.putInt(process);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();
        LogUtils.logBlueTooth("发送消息序号：" + process + " 成功:" + state);

        byte[] msg = constructCmd(HEAD_FILE_SPP_D_2_A, CMD_ID_8003,
                DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);

        return msg;
    }

    /**
     * 返回传输结果
     *
     * @return
     */
    public static byte[] getTransferD2A04Cmd(byte result) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(result);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();

        return constructCmd(HEAD_FILE_SPP_D_2_A, CMD_ID_8004, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * App端取消接收数据
     *
     * @return
     */
    public static byte[] getTransferD2A05Cmd(byte result) {
        return constructCmd(HEAD_FILE_SPP_D_2_A, CMD_ID_8005, DIVIDE_N_2, 0, 0, null);
    }

    /**
     * 获取添加初始化信息的命令
     *
     * @param type  1. 热量(千卡)
     *              2. 距离(千米)
     *              3. 步数(步)
     *              4. 生日(19960605)
     *              5. 身高（厘米）
     *              6. 体重(千克)
     *              7. 性别：1.男 2.女
     * @param value 整数值
     * @return
     */
    public static byte[] initSportHealthCmd(byte type, int value) {

        LogUtils.logBlueTooth("type:" + type + " value:" + value);
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(type);
        byteBuffer.putInt(value);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();

        return constructCmd(HEAD_SPORT_HEALTH, CMD_ID_8004, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 获取初始化信息
     *
     * @return
     */
    public static byte[] getSportHealthInitInfoCmd() {
        return constructCmd(HEAD_SPORT_HEALTH, CMD_ID_8001, DIVIDE_N_2, 0, 0, null);
    }

    /**
     * 获取运动记录
     *
     * @return
     */
    public static byte[] getStepRecordCmd() {

        LogUtils.logBlueTooth("7.获取运动步数：");
        byte[] msg = CmdHelper.constructCmd(HEAD_SPORT_HEALTH, CMD_ID_8002, DIVIDE_N_2, 0, 0, null);
        return msg;
    }

    /**
     * 获取心率记录
     *
     * @return
     */
    public static byte[] getRateRecordCmd() {
        LogUtils.logBlueTooth("8.获取心率：");
        return CmdHelper.constructCmd(HEAD_SPORT_HEALTH, CMD_ID_8003, DIVIDE_N_2, 0, 0, null);
    }

    /**
     * 获取睡眠记录
     *
     * @return
     */
    public static byte[] getSleepRecordCmd(byte index) {
        LogUtils.logBlueTooth("12.获取睡眠");

        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(index);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();

        return CmdHelper.constructCmd(HEAD_SPORT_HEALTH, CMD_ID_800F, DIVIDE_N_2, 0,  BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 获取睡眠记录
     *
     * @return
     */
    public static byte[] getSleepSetCmd() {
        LogUtils.logBlueTooth("获取睡眠区间设置");
        return CmdHelper.constructCmd(HEAD_SPORT_HEALTH, CMD_ID_800C, DIVIDE_N_2, 0, 0, null);
    }

    /**
     * 获取睡眠记录
     *
     * @return
     */
    public static byte[] getSetSleepCmd(byte open, byte startH, byte startM, byte endH, byte endM) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(open);
        byteBuffer.put(startH);
        byteBuffer.put(startM);
        byteBuffer.put(endH);
        byteBuffer.put(endM);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();
        return constructCmd(HEAD_SPORT_HEALTH, CMD_ID_800E, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 获取血氧记录
     *
     * @return
     */
    public static byte[] getBloodOxRecordCmd() {
        LogUtils.logBlueTooth("9.获取血氧");
        return CmdHelper.constructCmd(HEAD_SPORT_HEALTH, CMD_ID_8009, DIVIDE_N_2, 0, 0, null);
    }

    /**
     * 获取血糖记录
     *
     * @return
     */
    public static byte[] getBloodSugarRecordCmd() {
        LogUtils.logBlueTooth("10.获取血糖");
        return CmdHelper.constructCmd(HEAD_SPORT_HEALTH, CMD_ID_800A, DIVIDE_N_2, 0, 0, null);
    }

    /**
     * 获取血压记录
     *
     * @return
     */
    public static byte[] getBloodPressRecordCmd() {
        LogUtils.logBlueTooth("11.获取血压");
        return CmdHelper.constructCmd(HEAD_SPORT_HEALTH, CMD_ID_800B, DIVIDE_N_2, 0, 0, null);
    }

    /**
     * 获取当前支持的功能
     *
     * @return
     */
    public static byte[] getActionSupportCmd() {
        LogUtils.logBlueTooth("获取支持功能列表");
        return CmdHelper.constructCmd(HEAD_COMMON, CMD_ID_802D, DIVIDE_N_2, 0, 0, null);
    }

    /**
     * 获取运动健康有数据的日期
     *
     * @return
     */
    public static byte[] getRecordDates(int type) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put((byte) type);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();

        return constructCmd(HEAD_SPORT_HEALTH, CMD_ID_8006, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }


    /**
     * App修改来电响铃
     * <p>
     * 0: 响铃
     * 1: 震动反馈
     * 2: 抬腕亮屏
     *
     * @param state
     * @return
     */
    public static byte[] getSetDeviceRingStateCmd(byte type, byte state) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(type);
        byteBuffer.put(state);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();
        return constructCmd(HEAD_COMMON, CMD_ID_8018, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 设备端修改来电响铃
     *
     * @return
     */
    public static byte[] getDeviceRingStateCmd() {
        return constructCmd(HEAD_COMMON, CMD_ID_8017, DIVIDE_N_2, 0, 0, null);
    }

    /**
     * 设备端修改来电响铃
     *
     * @return
     */
    public static byte[] getDeviceRingStateRespondCmd() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put((byte) 1);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();
        return constructCmd(HEAD_COMMON, CMD_ID_8019, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * App发起寻找手表
     *
     * @return
     */
    public static byte[] getSearchDeviceCmd() {
        return constructCmd(HEAD_COMMON, CMD_ID_8021, DIVIDE_N_2, 0, 0, null);
    }


    /**
     * 当设备添加通讯录的时候需要给回应，固定是成功
     * CMD_ID_8024/CMD_ID_8026/CMD_ID_8020
     *
     * @return
     */
    public static byte[] getRespondSuccessCmd(short cmdId) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put((byte) 1);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();
        return constructCmd(HEAD_COMMON, cmdId, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 读取通讯录准备命令
     *
     * @return
     */
    public static byte[] getContactPreCmd() {
        return constructCmd(HEAD_COMMON, CMD_ID_8027, DIVIDE_N_2, 0, 0, null);
    }

    /**
     * 获取通讯录
     *
     * @param index
     * @return
     */
    public static byte[] getContactListCmd(byte index) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(index);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();
        return constructCmd(HEAD_COMMON, CMD_ID_8022, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 添加联系人通讯录
     *
     * @return
     */
    public static byte[] getAddContactCmd(String name, String phoneNum) {

        byte[] nameArray = new byte[60];
        byte[] phoneArray = new byte[20];

        ByteBuffer byteBuffer = ByteBuffer.allocate(nameArray.length + phoneArray.length);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        byte[] srcNameArray = name.getBytes(Charset.defaultCharset());
        System.arraycopy(srcNameArray, 0, nameArray, 0, srcNameArray.length > nameArray.length ? nameArray.length : srcNameArray.length);

        byte[] srcPhoneArray = phoneNum.replaceAll("\\s", "").getBytes(Charset.defaultCharset());
        System.arraycopy(srcPhoneArray, 0, phoneArray, 0, srcPhoneArray.length > phoneArray.length ? phoneArray.length : srcPhoneArray.length);

//        LogUtils.logBlueTooth("添加的联系人：" + new String(nameArray));
//        LogUtils.logBlueTooth("添加的联系人长度：" + nameArray.length);
//        LogUtils.logBlueTooth("添加的电话号：" + new String(phoneArray));
//        LogUtils.logBlueTooth("添加的电话号长度：" + phoneArray.length);

        byteBuffer.put(nameArray);
        byteBuffer.put(phoneArray);

        byteBuffer.flip();
        byte[] payload = byteBuffer.array();
        return constructCmd(HEAD_COMMON, CMD_ID_8023, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }


    /**
     * 删除通讯录命令
     *
     * @return
     */
    public static byte[] getDeleteContactCmd(String name, String phoneNum) {
        byte[] nameArray = new byte[60];
        byte[] phoneArray = new byte[20];

        ByteBuffer byteBuffer = ByteBuffer.allocate(nameArray.length + phoneArray.length);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        byte[] srcNameArray = name.getBytes(Charset.defaultCharset());
        System.arraycopy(srcNameArray, 0, nameArray, 0, srcNameArray.length > nameArray.length ? nameArray.length : srcNameArray.length);

        byte[] srcPhoneArray = phoneNum.getBytes(Charset.defaultCharset());
        System.arraycopy(srcPhoneArray, 0, phoneArray, 0, srcPhoneArray.length > phoneArray.length ? phoneArray.length : srcPhoneArray.length);

        byteBuffer.put(nameArray);
        byteBuffer.put(phoneArray);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();
        return constructCmd(HEAD_COMMON, CMD_ID_8025, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 读取通讯录准备命令
     *
     * @return
     */
    public static byte[] getStopRingCmd() {
        return constructCmd(HEAD_COMMON, CMD_ID_801A, DIVIDE_N_2, 0, 0, null);
    }

    /**
     * 摄氏度 = (华氏度 - 32°F) ÷ 1.8；华氏度 = 32°F+ 摄氏度 × 1.8
     *
     * @param weatherBean
     * @return
     */
    public static byte[] getWeatherListCmd(WeatherBean weatherBean) {
        byte[] cityArray = new byte[20];

        ByteBuffer byteBuffer = ByteBuffer.allocate(cityArray.length + 4 + weatherBean.getForecasts().size() * 5);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        String cityName = weatherBean.getLocation().getCity();
        byte[] srcNameArray = cityName.getBytes(Charset.defaultCharset());
        System.arraycopy(srcNameArray, 0, cityArray, 0, srcNameArray.length > cityArray.length ? cityArray.length : srcNameArray.length);

        int date = getFormatTime(weatherBean.getPubDate());

        byteBuffer.put(cityArray);
        byteBuffer.putInt(date);

        for (WeatherBean.ForecastsBean forecastsBean : weatherBean.getForecasts()) {
            //week  周几1~7
//            byte week = getWeek(forecastsBean.getDay());
            byte week = getWeekByTimeStamp(forecastsBean.getDate());
//            LogUtils.logBlueTooth("week2:" + week2 + " - week:" + week);
            byteBuffer.put(week);
            //天气大类型 1：晴； 2：多云；3：雨；4：雪；5：沙尘
            byte weatherBigCode = getWeatherBigCode(forecastsBean.getCode());
            byteBuffer.put(weatherBigCode);
            //天气名称暂时不用
            //天气代码
            byteBuffer.put((byte) forecastsBean.getCode());
            //高温
//            byteBuffer.put((byte) ((forecastsBean.getHigh() - 32) / 1.8));
            byteBuffer.put((byte) forecastsBean.getHigh());
            //低温
            byteBuffer.put((byte) forecastsBean.getLow());
//            byteBuffer.put((byte) ((forecastsBean.getLow() - 32) / 1.8));

        }

        byte[] payload = byteBuffer.array();
        return constructCmd(HEAD_COMMON, CMD_ID_801B, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 回应相机
     *
     * @param state
     * @return
     */
    public static byte[] getCameraRespondCmd(short cmdId, byte state) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(state);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();
        return constructCmd(HEAD_COMMON, cmdId, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 相机设置
     *
     * @param state
     * @return
     */
    public static byte[] getCameraStateActionCmd(byte action, byte state) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(action);
        byteBuffer.put(state);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();
        return constructCmd(HEAD_COMMON, CMD_ID_802C, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * App拉起设备相机
     *
     * @param state
     * @return
     */
    public static byte[] getAppCallDeviceCmd(byte state) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(state);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();
        return constructCmd(HEAD_COMMON, CMD_ID_802A, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    private static int getFormatTime(long timeMillions) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
        long lt = new Long(timeMillions);
        Date date = new Date(lt);
        String res = simpleDateFormat.format(date);
        LogUtils.logBlueTooth("转换后的时间：" + res);
        return Integer.parseInt(res);
    }

    private static final int[] weatherCodeClear = {0, 1, 2, 3, 38};
    private static final int[] weatherCodeCloud = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
    private static final int[] weatherCodeRain = {20, 21, 22, 23, 24, 25, 37};
    private static final int[] weatherCodeSnow = {26, 27, 28, 29, 30, 31};
    private static final int[] weatherCodeSand = {4, 5, 6, 7, 8, 9, 32, 33, 34, 35, 36};

    /**
     * 1：晴； 2：多云；3：雨；4：雪；5：沙尘  6未知
     *
     * @param weatherCode
     * @return
     */
    private static byte getWeatherBigCode(int weatherCode) {
        byte weatherBigCode = 99;

        for (int code : weatherCodeClear) {
            if (code == weatherCode) {
                weatherBigCode = 1;
                return weatherBigCode;
            }
        }

        for (int code : weatherCodeCloud) {
            if (code == weatherCode) {
                weatherBigCode = 2;
                return weatherBigCode;
            }
        }

        for (int code : weatherCodeRain) {
            if (code == weatherCode) {
                weatherBigCode = 3;
                return weatherBigCode;
            }
        }

        for (int code : weatherCodeSnow) {
            if (code == weatherCode) {
                weatherBigCode = 4;
                return weatherBigCode;
            }
        }

        for (int code : weatherCodeSand) {
            if (code == weatherCode) {
                weatherBigCode = 5;
                return weatherBigCode;
            }
        }

        return weatherBigCode;
    }

    private static byte getWeekByTimeStamp(long weekTime) {

        String week = TimeUtils.getUSWeek(weekTime);

//        LogUtils.logBlueTooth("星期：" + week);

        if (week.contains("Sun")) {
            return 7;
        } else if (week.contains("Mon")) {
            return 1;
        } else if (week.contains("Tue")) {
            return 2;
        } else if (week.contains("Wed")) {
            return 3;
        } else if (week.contains("Thu")) {
            return 4;
        } else if (week.contains("Fri")) {
            return 5;
        } else if (week.contains("Sat")) {
            return 6;
        } else if (week.contains("Sun")) {
            return 7;
        }

        return 0;
    }

    private static byte getWeek(String weeks) {

        if (weeks.contains("Sun")) {
            return 7;
        } else if (weeks.contains("Mon")) {
            return 1;
        } else if (weeks.contains("Tue")) {
            return 2;
        } else if (weeks.contains("Wed")) {
            return 3;
        } else if (weeks.contains("Thu")) {
            return 4;
        } else if (weeks.contains("Fri")) {
            return 5;
        } else if (weeks.contains("Sat")) {
            return 6;
        } else if (weeks.contains("Sun")) {
            return 7;
        }

        return 0;
    }

    /**
     * 相机预览界面开始
     *
     * @return
     */
    public static byte[] getCameraPreviewCmd01(byte dataType) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(dataType);
        byteBuffer.flip();
        byte[] payload = byteBuffer.array();
        return constructCmd(HEAD_CAMERA_PREVIEW, CMD_ID_8001, DIVIDE_N_2, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

    /**
     * 相机预览界面数据发送
     *
     * @return
     */
    public static byte[] getCameraPreviewDataCmd02(byte[] data, byte divideType) {
        byte[] payload = data;
        return constructCmd(HEAD_CAMERA_PREVIEW, CMD_ID_8002, divideType, 0, BtUtils.getCrc(HEX_FFFF, payload, payload.length), payload);
    }

}
