package com.base.sdk.entity;

public class ActionSupportBean {
    public boolean backFromDev;

    public int dialSupportState;//1
    public int searchDeviceSupportState;//1
    public int searchPhoneSupportState;//1
    public int otaSupportState;//1
    public int ebookSupportState;
    public int sleepSupportState;
    public int cameraSupportState;//1

    public int sportSupportState;//1
    public int rateSupportState;//1
    public int bloodOxSupportState;
    public int bloodPressSupportState;
    public int bloodSugarSupportState;

    public int notifyMsgSupportState;//1
    public int alarmSupportState;//1
    public int musicSupportState;//1
    public int contactSupportState;//1
    public int appViewSupportState;//1
    public int setRingSupportState;//1
    public int setNotifyTouchSupportState;//1
    public int setWatchTouchSupportState;//1
    public int setSystemTouchSupportState;//1
    public int armSupportState;//1
    public int weatherSupportState;//1
    public int supportSlowModel;//开启慢速模式
    public int supportCameraPreview;//支持相机预览

    @Override
    public String toString() {
        return "ActionSupportBean{" +
                "backFromDev=" + backFromDev +
                ", dialSupportState=" + dialSupportState +
                ", searchDeviceSupportState=" + searchDeviceSupportState +
                ", searchPhoneSupportState=" + searchPhoneSupportState +
                ", otaSupportState=" + otaSupportState +
                ", ebookSupportState=" + ebookSupportState +
                ", sleepSupportState=" + sleepSupportState +
                ", cameraSupportState=" + cameraSupportState +
                ", sportSupportState=" + sportSupportState +
                ", rateSupportState=" + rateSupportState +
                ", bloodOxSupportState=" + bloodOxSupportState +
                ", bloodPressSupportState=" + bloodPressSupportState +
                ", bloodSugarSupportState=" + bloodSugarSupportState +
                ", notifyMsgSupportState=" + notifyMsgSupportState +
                ", alarmSupportState=" + alarmSupportState +
                ", musicSupportState=" + musicSupportState +
                ", contactSupportState=" + contactSupportState +
                ", appViewSupportState=" + appViewSupportState +
                ", setRingSupportState=" + setRingSupportState +
                ", setNotifyTouchSupportState=" + setNotifyTouchSupportState +
                ", setWatchTouchSupportState=" + setWatchTouchSupportState +
                ", setSystemTouchSupportState=" + setSystemTouchSupportState +
                ", armSupportState=" + armSupportState +
                ", weatherSupportState=" + weatherSupportState +
                ", supportSlowModel=" + supportSlowModel +
                ", supportCameraPreview=" + supportCameraPreview +
                '}';
    }
}
