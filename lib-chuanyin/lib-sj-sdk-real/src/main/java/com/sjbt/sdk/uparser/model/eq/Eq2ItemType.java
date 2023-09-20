package com.sjbt.sdk.uparser.model.eq;

public class Eq2ItemType {

    public static final int frequence = 0;
    public static final int Q = frequence + 1;                        //1
    public static final int Gain = frequence + 2;                    //2
    public static final int EqType = frequence + 3;                    //3

    public static final int TYPE_TSPS_START = 30;
    public static final int SwitchTone = TYPE_TSPS_START;        //6		变调开关
    public static final int Alpha_tone = TYPE_TSPS_START + 1;                //31		音调
    public static final int Alpah_voice_speed = TYPE_TSPS_START + 2;        //32	音速
    public static final int ResonanceCoefficient = TYPE_TSPS_START + 3;    //33	共振系数

    public static final int YPE_DRC_START = 60;
    public static final int CompensationGain = YPE_DRC_START;        //10	补偿增益
    public static final int VoltageLimiteSwitch = YPE_DRC_START + 1;    //61	压限开关
    public static final int StartupLevel = YPE_DRC_START + 2;            //62	启动电平
    public static final int StartupTime = YPE_DRC_START + 3;            //63	启动时间
    public static final int CompressRatio = YPE_DRC_START + 4;            //64	压缩比例
    public static final int ReleaseTime = YPE_DRC_START + 5;            //65	释放时间

    public static final int TYPE_REVERB_START = 90;
    public static final int SwitchMix = TYPE_REVERB_START;                //16	混响总开关
    public static final int MixPreDelay = TYPE_REVERB_START + 1;            //91    混响预延时
    public static final int MixTime = TYPE_REVERB_START + 2;                //92	混响时间
    public static final int ExpandCoefficient = TYPE_REVERB_START + 3;        //93	扩散系数
    public static final int MixVolume = TYPE_REVERB_START + 4;                //94	混响音量
    public static final int ThroughVolume = TYPE_REVERB_START + 5;            //95	直达音量
    public static final int LowFreDampingPoint = TYPE_REVERB_START + 6;        //96	低频阻尼频点
    public static final int LowFreDamping = TYPE_REVERB_START + 7;            //97	低频阻尼
    public static final int HighFreDampingPoint = TYPE_REVERB_START + 8;    //98	高频阻尼频点
    public static final int HighFreDamping = TYPE_REVERB_START + 9;            //99	高频阻尼
    public static final int LowpassFilterFre = TYPE_REVERB_START + 10;        //100	低通滤波截止频率
    public static final int HighpassFilterFre = TYPE_REVERB_START + 11;        //101	高通滤波截止频率
    public static final int RvebMSSwitch = TYPE_REVERB_START + 12;           //102   单双通道模式切换

    public static final int TYPE_ECHO_START = 120;
    public static final int SwitchEcho = TYPE_ECHO_START;                //121	回声总开关
    public static final int EchoSwitchLowPassFilter = TYPE_ECHO_START + 1;        //121	低通滤波
    public static final int EchoThroughVolume = TYPE_ECHO_START + 2;        //122	回声直达音量
    public static final int EchoMixVolume = TYPE_ECHO_START + 3;            //123	回声混响音量
    public static final int EchoLowpassFre = TYPE_ECHO_START + 4;            //124	低通频率
    public static final int EchoDelay = TYPE_ECHO_START + 5;                //125	回声延时
    public static final int EchoDeplicateRatio = TYPE_ECHO_START + 6;        //126	回声重复率

    public static final int InputSource = 220;      //输入源
    public static final int InputVolume = 221;      //输入音量， 预增益

    //public static final byte SWITCH_MIX_ECHO_MODE = 0xFB,		//		切换混响回声模式
    public static final int ENABLE_EQ = 0xFC;        //		使能EQ		pageID,  0xFC , group , reserved , 0.0/1.0
    public static final int RESET_ALL =  0xFE;        //      复位全部


    /*****************以下为Page type***********************/
    public static final int PID_MUSIC = 0;
    public static final int PID_EFFECT = PID_MUSIC + 1;
    public static final int PID_MIC = PID_EFFECT + 1;
    public static final int PID_OUTPUT1 = PID_MIC + 1;
    public static final int PID_OUTPUT2 = PID_OUTPUT1 + 1;
    public static final int PID_VSS = PID_OUTPUT2 + 1;
    public static final int PID_HW = PID_VSS + 1;
    public static final int PID_MAX = PID_HW + 1;  // 7

    public static final int PID_ENC_SINGLEMIC_BASE_PARAM =21;
    public static final int PID_ENC_SINGLEMIC_EX_PARAM = PID_ENC_SINGLEMIC_BASE_PARAM + 1;
    public static final int PID_ENC_SINGLEMIC_DRC_MIC = PID_ENC_SINGLEMIC_EX_PARAM + 1;
    public static final int PID_ENC_SINGLEMIC_DRC_TRUMPET = PID_ENC_SINGLEMIC_DRC_MIC + 1;
    public static final int PID_ENC_SINGLEMIC_EQ_MIC = PID_ENC_SINGLEMIC_DRC_TRUMPET + 1;
    public static final int PID_ENC_SINGLEMIC_EQ_TRUMPET = PID_ENC_SINGLEMIC_EQ_MIC + 1;

    public static final int PID_ENC_DOUBLEMIC_BASE_PARAM = PID_ENC_SINGLEMIC_EQ_TRUMPET + 1;
    public static final int PID_ENC_DOUBLEMIC_EX_PARAM = PID_ENC_DOUBLEMIC_BASE_PARAM + 1;
    public static final int PID_ENC_DOUBLEMIC_DRC_MIC = PID_ENC_DOUBLEMIC_EX_PARAM + 1;
    public static final int PID_ENC_DOUBLEMIC_DRC_TRUMPET = PID_ENC_DOUBLEMIC_DRC_MIC + 1;
    public static final int PID_ENC_DOUBLEMIC_EQ_MIC = PID_ENC_DOUBLEMIC_DRC_TRUMPET + 1;
    public static final int PID_ENC_DOUBLEMIC_EQ_TRUMPET = PID_ENC_DOUBLEMIC_EQ_MIC + 1; //32

    public static final int PID_ANC_MIC_FORWARD = 51;//前馈
    public static final int PID_ANC_MIC_FEEDBACK = PID_ANC_MIC_FORWARD + 1;//反馈
    public static final int PID_ANC_MIC_COMPENSATE = PID_ANC_MIC_FEEDBACK + 1;//补偿
    public static final int PID_ANC_MIC_TT_FORWARD = PID_ANC_MIC_COMPENSATE + 1;// 通透前馈
    public static final int PID_ANC_MIC_TT_FEEDBACK = PID_ANC_MIC_TT_FORWARD + 1;//通透反馈
    public static final int PID_ANC_MIC_TT_COMPENSATE = PID_ANC_MIC_TT_FEEDBACK + 1;//通透补偿
    public static final int PID_ANC_ANHT = PID_ANC_MIC_TT_COMPENSATE + 1;//目标通透曲线  57


    /*****************以下为filter type***********************/
    public static final int FILTER_TYPE_SHELFLOW = 0;
    public static final int FILTER_TYPE_PEAKNOTCH = 1;
    public static final int FILTER_TYPE_SHELFHIGH = 2;
    public static final int FILTER_TYPE_LOWPASS = 3;
    public static final int FILETER_TYPE_BANDPASS = 4;
    public static final int FILTER_TYPE_HIGHPASS = 5;

}
