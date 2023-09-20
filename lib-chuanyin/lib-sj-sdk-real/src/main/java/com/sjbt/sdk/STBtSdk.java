package com.sjbt.sdk;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.base.sdk.BaseUNIWatch;
import com.base.sdk.action.DiscoveryDeviceListener;
import com.base.sdk.entity.ActionSupportBean;
import com.base.sdk.entity.AlarmBean;
import com.base.sdk.entity.AppViewBean;
import com.base.sdk.entity.BasicInfo;
import com.base.sdk.entity.BatteryStateInfo;
import com.base.sdk.entity.BloodPressBean;
import com.base.sdk.entity.BtACLState;
import com.base.sdk.entity.CameraFrameInfo;
import com.base.sdk.entity.CameraPreviewState;
import com.base.sdk.entity.CameraSetActionBean;
import com.base.sdk.entity.ContactBean;
import com.base.sdk.entity.DeviceMode;
import com.base.sdk.entity.DialDelStateBean;
import com.base.sdk.entity.DialItem;
import com.base.sdk.entity.DialStatus;
import com.base.sdk.entity.FileTransferStateProgress;
import com.base.sdk.entity.NotificationMessageBean;
import com.base.sdk.entity.RateBean;
import com.base.sdk.entity.ScanSdkInfo;
import com.base.sdk.entity.SdkInfo;
import com.base.sdk.entity.SetSportInfo;
import com.base.sdk.entity.ShakeBean;
import com.base.sdk.entity.SleepRecordBean;
import com.base.sdk.entity.SleepSetBean;
import com.base.sdk.entity.SppStateBean;
import com.base.sdk.entity.SwitchStateBean;
import com.base.sdk.entity.WeatherBean;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;

/**
 * 拓步SDK示例
 */
public class STBtSdk extends BaseUNIWatch {

    @Override
    public Observable<BtACLState> observeBtAclState() {
        return null;
    }

    private static STBtSdk stBtSdk = new STBtSdk();

    private STBtSdk() {

    }

    public static STBtSdk getInstance() {
        return stBtSdk;
    }

    @Override
    public void init(Context context, int msgTimeOut) {

    }

    @Override
    public Observable<Integer> connect(String address) {
        return null;
    }

    @Override
    public Observable<Integer> connect(BluetoothDevice device) {
        return null;
    }

    @Override
    public ScanSdkInfo scanQr(String address) {
        ScanSdkInfo scanDeviceInfo = new ScanSdkInfo(DeviceMode.ST_WATCH, address);
        scanDeviceInfo.setRecognized(true);
        return scanDeviceInfo;
    }

    @Override
    public SdkInfo getSdkInfo(DeviceMode deviceMode, String address) {
        SdkInfo sdkInfo = new SdkInfo(deviceMode, address);
        sdkInfo.setRecognized(deviceMode.equals(DeviceMode.ST_WATCH));
        return sdkInfo;
    }

    @Override
    public void startDiscovery(DiscoveryDeviceListener discoveryDeviceListener) {

    }

    @Override
    public void stopDiscovery() {

    }

    @Override
    public Single<BasicInfo> getBasicInfo() {
//        mBasicInfoListener = basicInfoListener;

        return Single.create(new SingleOnSubscribe<BasicInfo>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<BasicInfo> emitter) throws Throwable {
//                sendNormalMsg(CmdHelper.getBaseInfoCmd());
            }
        });
    }

    @Override
    public Single<Byte> observeWeatherRequest() {
        return Single.create(new SingleOnSubscribe<Byte>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Byte> emitter) throws Throwable {
//                emitterPushWeather = emitter;
            }
        });
    }

    @Override
    public void stopDeviceRing() {

    }

    @Override
    public void pushWeather(WeatherBean weatherBean) {

    }

    @Override
    public Single<AppViewBean> getAppViews() {
        return null;
    }

    @Override
    public Single<Boolean> setAppView(byte appViewId) {
        return null;
    }

    @Override
    public Single<Boolean> setDeviceRingState(byte type, byte state) {
        return null;
    }

    @Override
    public Single<ShakeBean> getDeviceRingState() {
        return null;
    }

    @Override
    public Observable<ShakeBean> observeDeviceRingState() {
        return null;
    }

    @Override
    public Single<List<DialItem>> getMyDials() {
        return null;
    }

    @Override
    public void updateCameraPreviewFrameData(CameraFrameInfo cameraFrameInfo) {

    }

    @Override
    public void stopUpdateCameraPreviewFrameData() {

    }

    @Override
    public void respondTakePhoto(byte state) {

    }

    @Override
    public void setCameraState(byte action, byte state) {

    }

    @Override
    public void clearBtMsg() {

    }

    @Override
    public Single<Boolean> cancelTransfer() {
        return null;
    }

    @Override
    public void bindDevice(int state) {

    }

    @Override
    public Observable<FileTransferStateProgress> startDialThumpImage(File file) {
        return null;
    }

    @Override
    public Observable<FileTransferStateProgress> startInstallDial(File file) {
        return null;
    }

    @Override
    public Observable<FileTransferStateProgress> startOta(File file) {
        return null;
    }

    @Override
    public Observable<FileTransferStateProgress> sendMusicFiles(List<File> files) {
        return null;
    }

    @Override
    public Observable<FileTransferStateProgress> sendTxtFiles(List<File> files) {
        return null;
    }

    @Override
    public void closeSpp() {

    }

    @Override
    public SppStateBean getSppStateBean() {
        return new SppStateBean();
    }

    @Override
    public void respondCallCameraPage(byte state) {

    }

    @Override
    public void release() {

    }

    @Override
    public Observable<SwitchStateBean> observeDeviceSwitchListener() {
        return null;
    }

    @Override
    public Observable<BatteryStateInfo> observeBattery() {
        return null;
    }

    @Override
    public Single<List<ContactBean>> getContactListener(int actionType) {
        return null;
    }

    @Override
    public Single<Boolean> appAddContact(int actionType, ContactBean contactBean) {
        return null;
    }

    @Override
    public Observable<ContactBean> observeDeviceAddContact() {
        return null;
    }

    @Override
    public Observable<ContactBean> observeDeviceDelContact() {
        return null;
    }

    @Override
    public Single<Boolean> appDelContact(int actionType, ContactBean contactBean) {
        return null;
    }

    @Override
    public Observable<Integer> observeCameraAction() {
        return null;
    }

    @Override
    public Observable<CameraSetActionBean> observeCameraSetAction() {
        return null;
    }

    @Override
    public Single<CameraPreviewState> startCameraPreviewListener() {
        return null;
    }

    @Override
    public Single<Boolean> requestDeviceCameraListener(byte open) {
        return null;
    }

    @Override
    public Single<Boolean> setDeviceCallCameraListener() {
        return null;
    }

    @Override
    public Single<ActionSupportBean> getActionSupportBean() {
        return null;
    }

    @Override
    public Single<DialStatus> getDialStatus(String dialId) {
        return null;
    }

    @Override
    public Single<Boolean> sendNotificationMsg(NotificationMessageBean notificationMessageBean) {
        return null;
    }

    @Override
    public Single<Boolean> setAlarmSetting(AlarmBean alarmBean) {
        return null;
    }

    @Override
    public Single<AlarmBean> getAlarmSetting() {
        return null;
    }

    @Override
    public Single<String> getSportHealthInfo() {
        return null;
    }

    @Override
    public Single<Boolean> appSearchDevice() {
        return null;
    }

    @Override
    public Observable<Integer> observableSearchPhone() {
        return null;
    }

    @Override
    public Single<SetSportInfo> setSportHealthInfo(byte type, int value) {
        return null;
    }

    @Override
    public Single<Integer> getStep() {
        return null;
    }

    @Override
    public Single<RateBean> getRate() {
        return null;
    }

    @Override
    public Single<List<SleepRecordBean>> getSleepRecordData() {
        return null;
    }

    @Override
    public Single<SleepSetBean> getSleepSet() {
        return null;
    }

    @Override
    public Observable<SleepSetBean> observableSleepSet() {
        return null;
    }

    @Override
    public Single<Boolean> setSleepTime(SleepSetBean sleepSetBean) {
        return null;
    }

    @Override
    public Single<Integer> getBloodOxData() {
        return null;
    }

    @Override
    public Single<Float> getBloodSugarData() {
        return null;
    }

    @Override
    public Single<BloodPressBean> getBloodPressData() {
        return null;
    }

    @Override
    public Single<Boolean> getTimeSetState() {
        return null;
    }

    @Override
    public Single<Boolean> setTimeState(byte open) {
        return null;
    }

    @Override
    public Single<Boolean> syncTime() {
        return null;
    }

    @Override
    public Single<DialDelStateBean> deleteDial(String id) {
        return null;
    }
}
