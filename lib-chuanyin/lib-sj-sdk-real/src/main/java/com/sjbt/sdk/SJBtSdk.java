package com.sjbt.sdk;

import static android.bluetooth.BluetoothDevice.BOND_NONE;
import static com.base.sdk.Config.BT_DISABLE;
import static com.base.sdk.Config.CONNECT_FAIL;
import static com.base.sdk.Config.VERIFIED;
import static com.sjbt.sdk.BTConfig.CONNECT_RETRY_COUNT;
import static com.sjbt.sdk.BTConfig.CONNECT_STATE_DISCONNECTED;
import static com.sjbt.sdk.BTConfig.FILE_TRANSFER_BIN;
import static com.sjbt.sdk.BTConfig.FILE_TRANSFER_JPG;
import static com.sjbt.sdk.BTConfig.FILE_TRANSFER_MUSIC;
import static com.sjbt.sdk.BTConfig.FILE_TRANSFER_TXT;
import static com.sjbt.sdk.BTConfig.FILE_TRANSFER_UP;
import static com.sjbt.sdk.BTConfig.FILE_TRANSFER_UPEX;
import static com.sjbt.sdk.BTConfig.MAX_RETRY_COUNT;
import static com.sjbt.sdk.BTConfig.MSG_INTERVAL;
import static com.sjbt.sdk.BTConfig.MSG_INTERVAL_FRAME;
import static com.sjbt.sdk.BTConfig.MSG_INTERVAL_SLOW;
import static com.sjbt.sdk.BTConfig.UP;
import static com.sjbt.sdk.BTConfig.UP_EX;
import static com.sjbt.sdk.spp.bt.BtEngine.STATE_CONNECTED;
import static com.sjbt.sdk.spp.bt.BtEngine.STATE_CONNECTING;
import static com.sjbt.sdk.spp.bt.BtEngine.TRANSFER_END_TIMEOUT;
import static com.sjbt.sdk.spp.cmd.CmdConfig.*;
import static com.sjbt.sdk.utils.FileUtils.readFileBytes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.base.sdk.entity.apps.DialItem;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.sjbt.sdk.events.BtConnectStateEvent;
import com.sjbt.sdk.spp.BtStateReceiver;
import com.sjbt.sdk.spp.bt.BtEngine;
import com.sjbt.sdk.spp.cmd.CmdHelper;
import com.sjbt.sdk.uparser.model.JpgInfo;
import com.sjbt.sdk.utils.BtUtils;
import com.sjbt.sdk.utils.ByteUtil;
import com.sjbt.sdk.utils.ClsUtils;
import com.sjbt.sdk.utils.FileUtils;
import com.sjbt.sdk.utils.LogUtils;
import com.sjbt.sdk.utils.UParseUtil;
import com.sjbt.sdk.utils.UrlParse;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;

public class SJBtSdk extends BaseUNIWatch implements BtEngine.Listener {

    //init属性
    private static SJBtSdk sjBtSdk = new SJBtSdk();
    private BtEngine mBtEngine = BtEngine.getInstance(this);

    private static Context mContext;
    private static BluetoothAdapter mBtAdapter;
    private Handler mHandler = new Handler(Looper.myLooper());
    private int mConnectTryCount;
    private byte initInfoType;
    private String mMacAddress;
    private static SppStateBean mSppStateBean = new SppStateBean();

    private String delDialId = "";

    //文件传输相关
    private List<File> mTransferFiles;
    private byte[] mFileDataArray;
    private File mSendingFile;
    private int mSelectFileCount;
    private int mSendFileCount;
    private int mCellLength;
    private int mOtaProcess;
    private boolean mCanceledSend, mErrorSend;
    private byte mDivide;
    private int mPackageCount, mLastDataLength;
    private int mTransferRetryCount;
    private static boolean mTransferring;
    //相机预览相关
    private CameraFrameInfo mCameraFrameInfo;
    private H264FrameMap mH264FrameMap = new H264FrameMap();
    private long mLatestIframeId, mLatestPframeId;
    private HandlerThread mCameraThread = new HandlerThread("camera_send_thread");
    private Handler mCameraHandler;
    private boolean needNewH264Frame = false, continueUpdateFrame;

    //表盘列表
    private List<DialItem> mMyDialList = new ArrayList<>();

    //发现设备监听
    private DiscoveryDeviceListener mDiscoveryDeviceListener;
    //监听
//    private TransferFileListener mTransferFileListener;
    //    private ConnectListener mBtConnectListener;
    //    private OnBtStateChangeListener mOnBtStateChangeListener;
    private List<ContactBean> mContactBeans;
    private ContactBean mAddContact, mDeleteContact;
    private int contactActionType = 1;//1：同步  2添加 3删除
    private BasicInfo mBasicInfo;
    private BatteryStateInfo mBatteryStateInfo;
    private ShakeBean shakeBean;
    private int mFramePackageCount;
    private int mFrameLastLen;
    private static BtStateReceiver mBtStateReceiver;
    private ActionSupportBean mActionSupportBean;
    private List<SleepRecordBean> mSleepRecordBeans = new ArrayList<>();
    private SingleEmitter<List<DialItem>> getAppViewEmitter;
    private ObservableEmitter<ShakeBean> observableShakeEmitter;
    private ObservableEmitter<Integer> cameraActionEmitter;
    private ObservableEmitter<CameraSetActionBean> cameraSetEmitter;
    private ObservableEmitter<SwitchStateBean> switchStateBeanEmitter;
    private ObservableEmitter<BatteryStateInfo> batteryEmitter;
    private ObservableEmitter<ContactBean> deviceAddContactEmitter;
    private ObservableEmitter<ContactBean> deviceDelEmitter;
    private ObservableEmitter<SleepSetBean> sleepSetObservableEmitter;
    private ObservableEmitter<Integer> searchPhoneEmitter;

    private SingleEmitter<BasicInfo> emitterBaseInfo;
    private SingleEmitter<Byte> emitterSingle;
    private SingleEmitter<ShakeBean> shakeEmitterSingle;
    private SingleEmitter<Boolean> singleSetAppViewEmitter;
    private SingleEmitter<Boolean> setDeviceEmitter;
    private SingleEmitter<Boolean> appDelContactEmitter;
    private SingleEmitter<List<ContactBean>> contactListEmitter;
    private SingleEmitter<CameraPreviewState> cameraPreviewEmitter;
    private SingleEmitter<Boolean> requestDeviceCameraEmitter;
    private SingleEmitter<Boolean> deviceCallCameraEmitter;
    private SingleEmitter<ActionSupportBean> actionSupportEmitter;
    private SingleEmitter<AppViewBean> emitterGetAppView;
    private SingleEmitter<DialDelStateBean> dialDelEmitter;
    private SingleEmitter<Boolean> emitterSyncTime;
    private SingleEmitter timeSetEmitter;
    private SingleEmitter<Boolean> timeStateEmitter;
    private SingleEmitter<BloodPressBean> getBloodPressEmitter;
    private SingleEmitter<Float> getBloodSugarEmitter;
    private SingleEmitter<Integer> getBloodOxEmitter;
    private SingleEmitter<Boolean> setSleepEmitter;
    private SingleEmitter<SleepSetBean> sleepSetEmitter;
    private SingleEmitter<List<SleepRecordBean>> sleepRecordEmitter;
    private SingleEmitter<RateBean> rateEmitter;
    private SingleEmitter<Integer> stepEmitter;
    private SingleEmitter<SetSportInfo> setSportInfoEmitter;
    private SingleEmitter<Boolean> searchDeviceEmitter;
    private SingleEmitter<String> getSportInfoEmitter;
    private SingleEmitter<AlarmBean> getAlarmEmitter;
    private SingleEmitter<Boolean> setAlarmEmitter;
    private SingleEmitter<Boolean> sendNotifyEmitter;
    private SingleEmitter<DialStatus> getDialEmitter;
    private SingleEmitter<Boolean> appAddContactEmitter;
    private SingleEmitter<Boolean> cancelTransferEmitter;

    private ObservableEmitter connectEmitter;

    private ObservableEmitter<FileTransferStateProgress> fileTransferEmitter;
    private FileTransferStateProgress fileTransferStateProgress = new FileTransferStateProgress();

    private SJBtSdk() {
    }

    @Override
    public SppStateBean getSppStateBean() {
        return mSppStateBean;
    }

    @Override
    public ScanSdkInfo scanQr(String qrString) {
        ScanSdkInfo scanDeviceInfo = new ScanSdkInfo(DeviceMode.SJ_WATCH, "");

        if (TextUtils.isEmpty(qrString) || !qrString.contains("metawatch")) {
            return scanDeviceInfo;
        }

        String hostPath = UrlParse.getUrlHostAndPath(qrString);
        Map<String, String> params = UrlParse.getUrlParams(qrString);

        String macAddress = params.get("m");
        String deviceName = params.get("n");

        scanDeviceInfo.setAddress(macAddress);
        scanDeviceInfo.setName(deviceName);
        scanDeviceInfo.setManufactureUrl(hostPath);
        scanDeviceInfo.setMode(DeviceMode.SJ_WATCH);

        scanDeviceInfo.setRecognized(qrString.contains("metawatch"));

        return scanDeviceInfo;
    }

    @Override
    public SdkInfo getSdkInfo(DeviceMode deviceMode, String address) {
        SdkInfo sdkInfo = new SdkInfo(deviceMode, address);
        sdkInfo.setRecognized(deviceMode.equals(DeviceMode.SJ_WATCH));
        return sdkInfo;
    }

    @Override
    public void startDiscovery(DiscoveryDeviceListener discoveryDeviceListener) {
        if (mBtAdapter == null) {
            return;
        }

        mDiscoveryDeviceListener = discoveryDeviceListener;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            if (mContext.checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED) {
                mBtAdapter.startDiscovery();
            } else {
                throw new RuntimeException("no bluetooth scan permission");
            }
        } else {
            mBtAdapter.startDiscovery();
        }
    }

    @Override
    public void stopDiscovery() {
        if (mBtAdapter == null) {
            return;
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            if (mContext.checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED) {
                mBtAdapter.cancelDiscovery();
            } else {
                throw new RuntimeException("no bluetooth scan permission");
            }
        } else {
            mBtAdapter.cancelDiscovery();
        }
    }

    /**
     * 释放所有内存占用
     */
    @Override
    public void release() {
        LogUtils.logBlueTooth("release Bt");
        transferEnd();

        mBtEngine.clearStateMap();
        clearBtMsg();
        closeSpp();
        stopCameraThread();

//        if (mContext != null) {
//            mContext.unregisterReceiver(mBtStateReceiver);
//        }
//
//        mBtEngine.setListener(null);
//        removeCallBackRunner(mTransferTimeoutRunner);
//        removeCallBackRunner(mConnectTimeoutRunner);
//
//        if (mBtAdapter != null) {
//            mBtAdapter = null;
//        }
//
//        if (mMyDialList != null) {
//            mMyDialList.clear();
//            mMyDialList = null;
//        }
//
//        if (mTransferFiles != null) {
//            mTransferFiles.clear();
//            mTransferFiles = null;
//        }
//
//        mTransferFileListener = null;
//        mCancelTransferFileListener = null;
//        mBtConnectListener = null;
//        mGetAppViewsListener = null;
//        mBasicInfoListener = null;
//        mBatteryListener = null;
//        mGetDialListListener = null;
//        mSetAppViewListener = null;
//        mNotificationMsgListener = null;
//        mGetTimeSetStateListener = null;
//        mSetTimeSetStateListener = null;
//        mSyncTimeResultListener = null;
//        mDeleteDialListener = null;
//        mGetDialStateListener = null;
//        mGetSportHealthInitInfoListener = null;
//        mGetStepListener = null;
//        mGetRateListener = null;
//        mSetSportHealthInfoListener = null;
//
//        mFileDataArray = null;
//        if (mTransferFiles != null) {
//            mTransferFiles.clear();
//            mTransferFiles = null;
//        }
//        mSendingFile = null;
    }

    public static SJBtSdk getInstance() {
        return sjBtSdk;
    }

    @Override
    public void init(Context context, int msgTimeOut) {
        mContext = context;
        mBtEngine = BtEngine.getInstance(this);
        mBtEngine.setListener(this);
        mBtStateReceiver = new BtStateReceiver(context, new OnBtStateChangeListener() {
            @Override
            public void onClassicBtDisConnect(BluetoothDevice device) {
                LogUtils.logBlueTooth("onClassicBtDisConnect：" + device.getAddress());

                if (!TextUtils.isEmpty(mMacAddress) && device.getAddress().equals(mMacAddress)) {
                    mConnectTryCount = 0;
                    mTransferring = false;
                    mTransferRetryCount = 0;
                    mCanceledSend = true;
                    mBtEngine.clearMsgQueue();
                    mBtEngine.clearStateMap();
                    removeCallBackRunner(mConnectTimeoutRunner);
                    mSppStateBean.isSppConnected = false;
                    stopUpdateCameraPreviewFrameData();

                    if (observableEmitter != null) {
                        btACLState.setState(BluetoothState.BT_ACL_CONNECTED);
                        btACLState.setDevice(device);
                        observableEmitter.onNext(btACLState);
                    }
                }
            }

            @Override
            public void onClassicBtConnect(BluetoothDevice device) {
                LogUtils.logBlueTooth("onClassicBtConnect：" + device.getAddress());
                if (!TextUtils.isEmpty(mMacAddress) && device.getAddress().equals(mMacAddress)) {

                    if (observableEmitter != null) {
                        btACLState.setState(BluetoothState.BT_ACL_DISCONNECTED);
                        btACLState.setDevice(device);
                        observableEmitter.onNext(btACLState);
                    }
                }
            }

            @Override
            public void onClassicBtDisabled() {
                LogUtils.logBlueTooth("onClassicBtDisabled");
                mTransferring = false;
                mTransferRetryCount = 0;
                mCanceledSend = true;
                mBtEngine.clearMsgQueue();
                mBtEngine.clearStateMap();

                if (observableEmitter != null) {
                    btACLState.setState(BluetoothState.BT_DISABLED);
                    btACLState.setDevice(null);
                    observableEmitter.onNext(btACLState);
                }

                removeCallBackRunner(mConnectTimeoutRunner);

                stopUpdateCameraPreviewFrameData();
            }

            @Override
            public void onClassicBtOpen() {
                LogUtils.logBlueTooth("onClassicBtOpen");

                if (observableEmitter != null) {
                    btACLState.setState(BluetoothState.BT_ENABLE);
                    btACLState.setDevice(null);
                    observableEmitter.onNext(btACLState);
                }

                removeCallBackRunner(mConnectTimeoutRunner);
            }

            @Override
            public void onBindState(BluetoothDevice device, int bondState) {
                if (bondState == BOND_NONE) {
                    if (!TextUtils.isEmpty(mMacAddress) && device.getAddress().equals(mMacAddress)) {
                        mConnectTryCount = 0;
                        mSppStateBean.isSppConnected = false;
                        mBtEngine.clearStateMap();
                        if (connectEmitter != null) {
                            connectEmitter.onNext(CONNECT_FAIL);
                            connectEmitter.onComplete();
                        }

                        removeCallBackRunner(mConnectTimeoutRunner);
                        LogUtils.logBlueTooth("取消配对：" + device.getAddress());
                    }
                }
            }

            @Override
            public void onDiscoveryDevice(BluetoothDevice device) {
                if (mDiscoveryDeviceListener != null) {
                    mDiscoveryDeviceListener.onGetDevice(device);
                }
            }

            @Override
            public void onStartDiscovery() {
                if (mDiscoveryDeviceListener != null) {
                    mDiscoveryDeviceListener.onStartDiscovery();
                }
            }

            @Override
            public void onStopDiscovery() {
                if (mDiscoveryDeviceListener != null) {
                    mDiscoveryDeviceListener.onStopDiscovery();
                }
            }
        });

        BluetoothManager mBluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        mBtAdapter = mBluetoothManager.getAdapter();

        startCameraThread();
    }

    private void startCameraThread() {
        if (!mCameraThread.isAlive()) {
            mCameraThread.start();
            mCameraHandler = new Handler(mCameraThread.getLooper());
        }
    }

    private void stopCameraThread() {
        if (mCameraThread.isAlive()) {
            mCameraThread.interrupt();
        }
    }

    private void removeCallBackRunner(Runnable runner) {

        try {
            if (mHandler != null) {
                mHandler.removeCallbacks(runner);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Single<CameraPreviewState> startCameraPreviewListener() {
        LogUtils.logBlueTooth("开始启动相机预览 startCameraPreviewListener");
//        mCameraPreviewListener = cameraPreviewListener;
        return Single.create(new SingleOnSubscribe<CameraPreviewState>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<CameraPreviewState> emitter) throws Throwable {
                cameraPreviewEmitter = emitter;
                sendNormalMsg(CmdHelper.getCameraPreviewCmd01((byte) 1));
            }
        });
    }

    @Override
    public Observable<Integer> observeCameraAction() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                cameraActionEmitter = emitter;
            }
        });
    }

    @Override
    public Observable<CameraSetActionBean> observeCameraSetAction() {
        return Observable.create(new ObservableOnSubscribe<CameraSetActionBean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<CameraSetActionBean> emitter) throws Throwable {
                cameraSetEmitter = emitter;
            }
        });
    }

    @Override
    public void updateCameraPreviewFrameData(@NotNull CameraFrameInfo cameraFrameInfo) {
        LogUtils.logBlueTooth("更新frame continueUpdateFrame：" + continueUpdateFrame);

        if (cameraFrameInfo != null) {
            if (cameraFrameInfo.frameType == 2) {
                mLatestIframeId = cameraFrameInfo.frameId;
//                LogUtils.logBlueTooth("最新的I帧：" + mLatestIframeId);
            } else {
                mLatestPframeId = cameraFrameInfo.frameId;
//                LogUtils.logBlueTooth("最新的P帧：" + mLatestIframeId);
            }
            mH264FrameMap.putFrame(cameraFrameInfo);

//            LogUtils.logBlueTooth("来新数据了:" + needNewH264Frame);

            if (needNewH264Frame) {
                mCameraFrameInfo = cameraFrameInfo;
                sendFrameDataAsync(cameraFrameInfo);
                needNewH264Frame = false;
            }
        }
    }

    @Override
    public void stopUpdateCameraPreviewFrameData() {
        continueUpdateFrame = false;
        LogUtils.logBlueTooth("停止更新frame数据continueUpdateFrame：" + continueUpdateFrame);
        mTransferring = false;
        mH264FrameMap.clear();
    }

    @Override
    public Single<Boolean> requestDeviceCameraListener(byte open) {
        mTransferring = false;

        if (open == 0) {
            stopUpdateCameraPreviewFrameData();
        }

        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Boolean> emitter) throws Throwable {
                sendNormalMsg(CmdHelper.getAppCallDeviceCmd(open));
                requestDeviceCameraEmitter = emitter;
            }
        });
    }

    @Override
    public Single<Boolean> setDeviceCallCameraListener() {
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Boolean> emitter) throws Throwable {
                deviceCallCameraEmitter = emitter;
            }
        });
    }

    @Override
    public Single<ActionSupportBean> getActionSupportBean() {
        return Single.create(new SingleOnSubscribe<ActionSupportBean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<ActionSupportBean> emitter) throws Throwable {
                sendNormalMsg(CmdHelper.getActionSupportCmd());
                actionSupportEmitter = emitter;
            }
        });
    }

    @Override
    public void respondTakePhoto(byte state) {
        sendNormalMsg(CmdHelper.getCameraRespondCmd(CMD_ID_8028, state));
    }

    @Override
    public void respondCallCameraPage(byte state) {
        sendNormalMsg(CmdHelper.getCameraRespondCmd(CMD_ID_8029, state));
    }

    @Override
    public void setCameraState(byte action, byte state) {
        sendNormalMsg(CmdHelper.getCameraStateActionCmd(action, state));
    }

    //    @Override
//    public void setConnectListener(@NonNull ConnectListener connectListener) {
//        mBtConnectListener = connectListener;
//    }
    @Override
    public Observable<Integer> connect(@NonNull BluetoothDevice device) {
        if (device != null) {
            return connect(device.getAddress());
        } else {
            mSppStateBean.isSppConnected = false;
        }

        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                connectEmitter = emitter;
            }
        });
    }

    /**
     * 通过蓝牙地址连接设备
     *
     * @param macAddress
     */
    @Override
    public Observable<Integer> connect(@NonNull String macAddress) {
//        mBtConnectListener = connectListener;
        mMacAddress = macAddress.toUpperCase();
        mSppStateBean.macAddress = mMacAddress;
        mBtEngine.setListener(this);

        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                connectEmitter = emitter;

                if (!mBtAdapter.isEnabled()) {
                    mSppStateBean.isSppConnected = false;
                    connectEmitter.onNext(BT_DISABLE);
                } else if (mBtEngine == null) {
                    mSppStateBean.isSppConnected = false;
                    connectEmitter.onNext(Config.DISCONNECTED);
                } else if (TextUtils.isEmpty(mMacAddress)) {
                    mSppStateBean.isSppConnected = false;
                    connectEmitter.onNext(Config.DISCONNECTED);
                }

                mBtStateReceiver.setmCurrDevice(macAddress);

                try {
                    LogUtils.logBlueTooth("请求连接的设备：" + mMacAddress);

                    if (mBtEngine.getDeviceConnectState(mMacAddress) == 0) {
                        connectEmitter.onNext(Config.CONNECTING);
                        mHandler.postDelayed(mConnectTimeoutRunner, 1500 * 60);
                        mBtEngine.putStateMap(mMacAddress, STATE_CONNECTING);
                        mBtEngine.clearMsgQueue();
                        BluetoothDevice bluetoothDevice = mBtAdapter.getRemoteDevice(mMacAddress);
                        mSppStateBean.bluetoothDevice = bluetoothDevice;

                        connectEmitter.onNext(Config.PRE_CONNECTED);
                        mBtEngine.connect(bluetoothDevice);
                    } else if (mBtEngine.getDeviceConnectState(mMacAddress) == STATE_CONNECTED) {
                        LogUtils.logBlueTooth("已连接的设备：" + mMacAddress);
                        sendNormalMsg(CmdHelper.getBiuShakeHandsCmd());
                    } else {
                        connectEmitter.onNext(Config.CONNECTING);
                        LogUtils.logBlueTooth("正在连接的设备：" + mMacAddress);
                    }

                    stopUpdateCameraPreviewFrameData();
                } catch (Exception e) {
                    e.printStackTrace();
                    mSppStateBean.isSppConnected = false;
                }
            }
        });
    }

    public void startPair(String address) {
        try {
            BluetoothDevice bluetoothDevice = mBtAdapter.getRemoteDevice(address);
            ClsUtils.createBond(BluetoothDevice.class, bluetoothDevice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableEmitter<BtACLState> observableEmitter;
    private BtACLState btACLState = new BtACLState();

    @Override
    public Observable<BtACLState> observeBtAclState() {
        return Observable.create(new ObservableOnSubscribe<BtACLState>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<BtACLState> emitter) throws Throwable {
                observableEmitter = emitter;
            }
        });
    }

    @Override
    public Single<BasicInfo> getBasicInfo() {
        return Single.create(new SingleOnSubscribe<BasicInfo>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<BasicInfo> emitter) throws Throwable {
                emitterBaseInfo = emitter;
                sendNormalMsg(CmdHelper.getBaseInfoCmd());
            }
        });
    }

    @Override
    public void stopDeviceRing() {
        sendNormalMsg(CmdHelper.getStopRingCmd());
    }

    /**
     * 推送天气
     */
    @Override
    public Single<Byte> observeWeatherRequest() {
        return Single.create(new SingleOnSubscribe<Byte>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Byte> emitter) throws Throwable {
                emitterSingle = emitter;
            }
        });

    }

    /**
     * 主动推送天气
     *
     * @param weatherBean
     */
    @Override
    public void pushWeather(WeatherBean weatherBean) {
        sendNormalMsg(CmdHelper.getWeatherListCmd(weatherBean));
    }

    /**
     * 获取AppViews
     */
    @Override
    public Single<AppViewBean> getAppViews() {
        return Single.create(new SingleOnSubscribe<AppViewBean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<AppViewBean> emitter) throws Throwable {
                emitterGetAppView = emitter;
                sendNormalMsg(CmdHelper.getAppViewList());
            }
        });
    }

    /**
     * 设置APPView
     */
    @Override
    public Single<Boolean> setAppView(byte appViewId) {

        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Boolean> emitter) throws Throwable {
                singleSetAppViewEmitter = emitter;
                sendNormalMsg(CmdHelper.setAppViewCmd(appViewId));
            }
        });

    }

    /**
     * 设置设备响铃开关
     */
    @Override
    public Single<Boolean> setDeviceRingState(byte type, byte state) {
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Boolean> emitter) throws Throwable {
                setDeviceEmitter = emitter;
                sendNormalMsg(CmdHelper.getSetDeviceRingStateCmd(type, state));
            }
        });
    }

    /**
     * 读取设备响铃开关
     */
    @Override
    public Single<ShakeBean> getDeviceRingState() {
        return Single.create(new SingleOnSubscribe<ShakeBean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<ShakeBean> emitter) throws Throwable {
                shakeEmitterSingle = emitter;
                if (shakeBean == null) {
                    sendNormalMsg(CmdHelper.getDeviceRingStateCmd());
                } else {
                    shakeEmitterSingle.onSuccess(shakeBean);
                }
            }
        });
    }

    @Override
    public Observable<ShakeBean> observeDeviceRingState() {
        return Observable.create(new ObservableOnSubscribe<ShakeBean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<ShakeBean> emitter) throws Throwable {
                observableShakeEmitter = emitter;
            }
        });
    }

    @Override
    public Observable<SwitchStateBean> observeDeviceSwitchListener() {
        return Observable.create(new ObservableOnSubscribe<SwitchStateBean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<SwitchStateBean> emitter) throws Throwable {
                switchStateBeanEmitter = emitter;
            }
        });
    }

    /**
     * 获取电池信息
     */
    @Override
    public Observable<BatteryStateInfo> observeBattery() {
        return Observable.create(new ObservableOnSubscribe<BatteryStateInfo>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<BatteryStateInfo> emitter) throws Throwable {
                batteryEmitter = emitter;
                sendNormalMsg(CmdHelper.getBatteryInfo());
            }
        });
    }

    /**
     * 获取我的表盘
     */
    @Override
    public Single<List<DialItem>> getMyDials() {
        return Single.create(new SingleOnSubscribe<List<DialItem>>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<List<DialItem>> emitter) throws Throwable {
                getAppViewEmitter = emitter;
                sendNormalMsg(CmdHelper.getDialListCmd((byte) 0));
            }
        });
    }

    /**
     * 获取通讯录列表
     */
    @Override
    public Single<List<ContactBean>> getContactListener(int actionType) {
        contactActionType = actionType;
        mContactBeans = new ArrayList<>();
        return Single.create(new SingleOnSubscribe<List<ContactBean>>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<List<ContactBean>> emitter) throws Throwable {
                contactListEmitter = emitter;
                sendNormalMsg(CmdHelper.getContactPreCmd());
            }
        });
    }

    /**
     * App添加联系人
     *
     * @param contactBean
     */
    @Override
    public Single<Boolean> appAddContact(int actionType, ContactBean contactBean) {
        contactActionType = actionType;
        mAddContact = contactBean;
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Boolean> emitter) throws Throwable {
                appAddContactEmitter = emitter;
                sendNormalMsg(CmdHelper.getContactPreCmd());
            }
        });
    }

    @Override
    public Observable<ContactBean> observeDeviceAddContact() {
        return Observable.create(new ObservableOnSubscribe<ContactBean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<ContactBean> emitter) throws Throwable {
                deviceAddContactEmitter = emitter;
            }
        });
    }

    /**
     * App删除联系人
     *
     * @param contactBean
     */
    @Override
    public Single<Boolean> appDelContact(int actionType, ContactBean contactBean) {
        contactActionType = actionType;
        mDeleteContact = contactBean;
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Boolean> emitter) throws Throwable {
                appDelContactEmitter = emitter;
                sendNormalMsg(CmdHelper.getContactPreCmd());
            }
        });
    }

    /**
     * 监听设备删除通讯录
     *
     * @return
     */
    @Override
    public Observable<ContactBean> observeDeviceDelContact() {
        return Observable.create(new ObservableOnSubscribe<ContactBean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<ContactBean> emitter) throws Throwable {
                deviceDelEmitter = emitter;
            }
        });
    }

    /**
     * 获取表盘状态信息
     *
     * @param dialId
     */
    @Override
    public Single<DialStatus> getDialStatus(String dialId) {
        return Single.create(new SingleOnSubscribe<DialStatus>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<DialStatus> emitter) throws Throwable {
                getDialEmitter = emitter;
                sendNormalMsg(CmdHelper.getDialStateCmd(dialId));
            }
        });
    }

    /**
     * 发送消息通知
     */
    @Override
    public Single<Boolean> sendNotificationMsg(NotificationMessageBean notificationMessageBean) {
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Boolean> emitter) throws Throwable {
                sendNormalMsg(CmdHelper.getNotificationCmd(notificationMessageBean));
                sendNotifyEmitter = emitter;
            }
        });

    }

    /**
     * 设置闹钟
     *
     * @param alarmBean
     */

    @Override
    public Single<Boolean> setAlarmSetting(AlarmBean alarmBean) {
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Boolean> emitter) throws Throwable {
                sendNormalMsg(CmdHelper.getSetAlarmCmd(alarmBean));
                setAlarmEmitter = emitter;
            }
        });

    }

    @Override
    public Single<AlarmBean> getAlarmSetting() {
        return Single.create(new SingleOnSubscribe<AlarmBean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<AlarmBean> emitter) throws Throwable {
                getAlarmEmitter = emitter;
                sendNormalMsg(CmdHelper.getCurrAlarmCmd());
            }
        });
    }

    /**
     * 获取初始化信息
     */
    @Override
    public Single<String> getSportHealthInfo() {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<String> emitter) throws Throwable {
                getSportInfoEmitter = emitter;
                sendNormalMsg(CmdHelper.getSportHealthInitInfoCmd());
            }
        });
    }

    /**
     * 寻找设备监听
     */
    @Override
    public Single<Boolean> appSearchDevice() {
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Boolean> emitter) throws Throwable {
                searchDeviceEmitter = emitter;
                sendNormalMsg(CmdHelper.getSearchDeviceCmd());
            }
        });
    }

    /**
     * 寻找手机监听
     */
    @Override
    public Observable<Integer> observableSearchPhone() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                searchPhoneEmitter = emitter;
            }
        });

    }

    /**
     * 清理消息
     */
    @Override
    public void clearBtMsg() {
        mBtEngine.clearMsgQueue();
    }

    /**
     * 关闭Spp
     */
    @Override
    public void closeSpp() {
        mBtEngine.closeSocket("user close", false);
    }

    /**
     * 修改运动健康信息
     *
     * @param type  0x01热量(千卡)
     *              0x02距离(千米)
     *              0x03步数(步)
     *              0x04生日(199CONTACT_NAME_LENCONTACT_NAME_LEN5)
     *              0x05身高（厘米）
     *              0x06体重(千克)
     *              0x07性别(1.男 0.女)
     * @param value 生日(199CONTACT_NAME_LENCONTACT_NAME_LEN5)
     *              性别(1.男 0.女)
     */
    @Override
    public Single<SetSportInfo> setSportHealthInfo(byte type, int value) {
        initInfoType = type;
        return Single.create(new SingleOnSubscribe<SetSportInfo>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<SetSportInfo> emitter) throws Throwable {
                setSportInfoEmitter = emitter;
                sendNormalMsg(CmdHelper.initSportHealthCmd(type, value));
            }
        });
    }

    /**
     * 获取当天累计步数
     */
    @Override
    public Single<Integer> getStep() {
        return Single.create(new SingleOnSubscribe<Integer>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Integer> emitter) throws Throwable {
                stepEmitter = emitter;
                sendNormalMsg(CmdHelper.getStepRecordCmd());
            }
        });
    }

    /**
     * 获取当天最新心率
     */
    @Override
    public Single<RateBean> getRate() {
        return Single.create(new SingleOnSubscribe<RateBean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<RateBean> emitter) throws Throwable {
                rateEmitter = emitter;
                sendNormalMsg(CmdHelper.getRateRecordCmd());
            }
        });
    }

    /**
     * 获取睡眠数据
     */
    @Override
    public Single<List<SleepRecordBean>> getSleepRecordData() {
        return Single.create(new SingleOnSubscribe<List<SleepRecordBean>>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<List<SleepRecordBean>> emitter) throws Throwable {
                sendNormalMsg(CmdHelper.getSleepRecordCmd((byte) 1));
                sleepRecordEmitter = emitter;
            }
        });
    }

    /**
     * 获取睡眠设置
     */
    @Override
    public Single<SleepSetBean> getSleepSet() {
        return Single.create(new SingleOnSubscribe<SleepSetBean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<SleepSetBean> emitter) throws Throwable {
                sleepSetEmitter = emitter;
                sendNormalMsg(CmdHelper.getSleepSetCmd());
            }
        });
    }

    @Override
    public Observable<SleepSetBean> observableSleepSet() {
        return Observable.create(new ObservableOnSubscribe<SleepSetBean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<SleepSetBean> emitter) throws Throwable {
                sleepSetObservableEmitter = emitter;
            }
        });
    }

    /**
     * 设置睡眠时间
     */
    @Override
    public Single<Boolean> setSleepTime(SleepSetBean sleepSetBean) {
//        mSetSleepResultListener = setSleepResultListener;

        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Boolean> emitter) throws Throwable {
                setSleepEmitter = emitter;
                sendNormalMsg(CmdHelper.getSetSleepCmd((byte) (sleepSetBean.getOpenState()), (byte) (sleepSetBean.getStartH()), (byte) sleepSetBean.getStartM(), (byte) (sleepSetBean.getEndH()), (byte) (sleepSetBean.getEndM())));
            }
        });
    }

    /**
     * 获取血氧数据
     */
    @Override
    public Single<Integer> getBloodOxData() {
//        mGetBloodOxListener = getBloodOxListener;

        return Single.create(new SingleOnSubscribe<Integer>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Integer> emitter) throws Throwable {
                getBloodOxEmitter = emitter;
                sendNormalMsg(CmdHelper.getBloodOxRecordCmd());
            }
        });
    }

    /**
     * 获取血糖数据
     */
    @Override
    public Single<Float> getBloodSugarData() {
//        mGetBloodSugarListener = getBloodSugarListener;
        return Single.create(new SingleOnSubscribe<Float>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Float> emitter) throws Throwable {
                sendNormalMsg(CmdHelper.getBloodSugarRecordCmd());
                getBloodSugarEmitter = emitter;
            }
        });
    }

    /**
     * 获取血压数据
     */
    @Override
    public Single<BloodPressBean> getBloodPressData() {
//        mGetBloodPressListener = getBloodPressListener;
        return Single.create(new SingleOnSubscribe<BloodPressBean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<BloodPressBean> emitter) throws Throwable {
                getBloodPressEmitter = emitter;
                sendNormalMsg(CmdHelper.getBloodPressRecordCmd());
            }
        });
    }

    /**
     * 查询时间是否打开
     */
    @Override
    public Single<Boolean> getTimeSetState() {
//        mGetTimeSetStateListener = getTimeSetStateListener;
        sendNormalMsg(CmdHelper.getTimeSetCmd(TIME_SYNC_SEARCH, -1));

        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Boolean> emitter) throws Throwable {
                timeStateEmitter = emitter;

            }
        });
    }

    /**
     * 设置时间同步
     *
     * @param open 1开 0关
     */
    @Override
    public Single<Boolean> setTimeState(byte open) {
//        mSetTimeSetStateListener = setTimeSetStateListener;
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Boolean> emitter) throws Throwable {
                timeSetEmitter = emitter;
                sendNormalMsg(CmdHelper.getTimeSetCmd(TIME_SYNC_SET, open));
            }
        });

    }

    /**
     * 同步时间
     */
    @Override
    public Single<Boolean> syncTime() {
//        mSyncTimeResultListener = syncTimeResultListener;
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Boolean> emitter) throws Throwable {
                emitterSyncTime = emitter;
                sendNormalMsg(CmdHelper.getSyncTimeCmd());
            }
        });
    }

    /**
     * 删除表盘
     *
     * @param id
     */
    @Override
    public Single<DialDelStateBean> deleteDial(String id) {
        delDialId = id;
        return Single.create(new SingleOnSubscribe<DialDelStateBean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<DialDelStateBean> emitter) throws Throwable {
                dialDelEmitter = emitter;
                sendNormalMsg(CmdHelper.getDialActionCmd((byte) 2, id));
            }
        });
    }

    /**
     * App端取消传输
     */
    @Override
    public Single<Boolean> cancelTransfer() {
        mCanceledSend = true;
        mTransferring = false;
        mFileDataArray = null;
        sendNormalMsg(CmdHelper.getTransferCancelCmd());

        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull SingleEmitter<Boolean> emitter) throws Throwable {
                cancelTransferEmitter = emitter;
            }
        });
    }

    /**
     * 绑定状态同步
     *
     * @param state 失败 0 成功 1
     */
    @Override
    public void bindDevice(int state) {
        sendNormalMsg(CmdHelper.getBindStateCmd((byte) state));
    }

    private File parsePicFile(File dialFile) {
        String savePath = mContext.getExternalCacheDir().getAbsolutePath() + "/dial/thump/dial_thump.jpg";
        File savePic = new File(savePath);

        try {
            JpgInfo jpgInfo = new JpgInfo();

            int result = UParseUtil.getInstance().getJpgFromDial(dialFile.getAbsolutePath(), jpgInfo);

            if (result == 0 || jpgInfo.resouceInfo.length == 0 || jpgInfo.jpgdata.length == 0) {

                ByteBuffer byteBuffer =
                        ByteBuffer.allocate(jpgInfo.resouceInfo.length + jpgInfo.jpgdata.length);
                byteBuffer.put(jpgInfo.resouceInfo);
                byteBuffer.put(jpgInfo.jpgdata);

//                LogUtils.logBlueTooth("封面图片文件28个字节头：" + BtUtils.bytesToHexString(jpgInfo.resouceInfo));
                boolean dialThumbFileWithHead = FileUtils.saveFile(savePic, byteBuffer.array());

                if (dialThumbFileWithHead) {
                    return savePic;
                } else {
                    return null;
                }

            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 发送图片
     *
     * @param dialFile
     */
    @Override
    public Observable<FileTransferStateProgress> startDialThumpImage(@NonNull File dialFile) {
        mCanceledSend = false;
        if (dialFile != null && dialFile.exists()) {
            mTransferFiles = new ArrayList<>();
            mTransferFiles.add(dialFile);
        } else {
            return Observable.create(new ObservableOnSubscribe<FileTransferStateProgress>() {
                @Override
                public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<FileTransferStateProgress> emitter) throws Throwable {
                    fileTransferEmitter.onError(new RuntimeException("empty files"));
                }
            });
        }

        return startTransferFile(FILE_TRANSFER_JPG);
    }


    /**
     * 安装表盘
     *
     * @param dialFile
     */
    @Override
    public Observable<FileTransferStateProgress> startInstallDial(@NonNull File dialFile) {
        if (mCanceledSend) {
            return Observable.create(new ObservableOnSubscribe<FileTransferStateProgress>() {
                @Override
                public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<FileTransferStateProgress> emitter) throws Throwable {
                    fileTransferEmitter = emitter;
                    fileTransferEmitter.onError(new RuntimeException("cancel transfer"));
                }
            });
        }

        if (dialFile != null && dialFile.exists()) {
            mSendingFile = dialFile;
            mTransferFiles = new ArrayList<>();
            mTransferFiles.add(dialFile);

        } else {
            return Observable.create(new ObservableOnSubscribe<FileTransferStateProgress>() {
                @Override
                public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<FileTransferStateProgress> emitter) throws Throwable {
                    fileTransferEmitter = emitter;
                    fileTransferEmitter.onError(new RuntimeException("file not exist"));
                }
            });
        }

        return startTransferFile(FILE_TRANSFER_BIN);
    }

    @Override
    public Observable<FileTransferStateProgress> startOta(@NonNull File file) {
        mCanceledSend = false;
//        mTransferFileListener = transferFileListener;
        if (file != null && file.exists()) {
            mSendingFile = file;
            mTransferFiles = new ArrayList<>();
            mTransferFiles.add(file);
        } else {
            return Observable.create(new ObservableOnSubscribe<FileTransferStateProgress>() {
                @Override
                public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<FileTransferStateProgress> emitter) throws Throwable {
                    fileTransferEmitter = emitter;
                    fileTransferEmitter.onError(new RuntimeException("empty files"));
                }
            });

        }

        String extension = com.blankj.utilcode.util.FileUtils.getFileExtension(file);

        if (extension.equals(UP)) {
            return startTransferFile(FILE_TRANSFER_UP);
        } else if (extension.equals(UP_EX)) {
            return startTransferFile(FILE_TRANSFER_UPEX);
        }

        return Observable.create(new ObservableOnSubscribe<FileTransferStateProgress>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<FileTransferStateProgress> emitter) throws Throwable {
                fileTransferEmitter = emitter;
                fileTransferEmitter.onError(new RuntimeException("unknown up files"));
            }
        });
    }

    @Override
    public Observable<FileTransferStateProgress> sendTxtFiles(List<File> files) {
        mCanceledSend = false;
        if (files != null && !files.isEmpty()) {
            mTransferFiles = files;
            fileTransferStateProgress.setTotalCount(mTransferFiles.size());
            fileTransferStateProgress.setFinishCount(0);
            return startTransferFile(FILE_TRANSFER_TXT);
        }

        return Observable.create(new ObservableOnSubscribe<FileTransferStateProgress>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<FileTransferStateProgress> emitter) throws Throwable {
                fileTransferEmitter = emitter;
                fileTransferEmitter.onError(new RuntimeException("empty files"));
            }
        });
    }

    @Override
    public Observable<FileTransferStateProgress> sendMusicFiles(@NonNull List<File> files) {
        mCanceledSend = false;
        if (files != null && !files.isEmpty()) {
            mTransferFiles = files;
            fileTransferStateProgress.setTotalCount(mTransferFiles.size());
            fileTransferStateProgress.setFinishCount(0);
            return startTransferFile(FILE_TRANSFER_MUSIC);
        }

        return Observable.create(new ObservableOnSubscribe<FileTransferStateProgress>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<FileTransferStateProgress> emitter) throws Throwable {
                fileTransferEmitter = emitter;
                fileTransferEmitter.onError(new RuntimeException("empty files"));
            }
        });
    }

    private Observable<FileTransferStateProgress> startTransferFile(byte type) {
        return Observable.create(new ObservableOnSubscribe<FileTransferStateProgress>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<FileTransferStateProgress> emitter) throws Throwable {
                fileTransferEmitter = emitter;

                mSelectFileCount = mTransferFiles.size();
                if (type == FILE_TRANSFER_UP || type == FILE_TRANSFER_UPEX) {
                    sendNormalMsg(CmdHelper.getTransferFile01Cmd(type, -1, 1));
                } else if (type == FILE_TRANSFER_BIN) {
                    sendNormalMsg(CmdHelper.getTransferFile01Cmd(type, (int) mSendingFile.length(), 1));
                } else if (type == FILE_TRANSFER_MUSIC || type == FILE_TRANSFER_TXT) {
                    int fileLen = 0;
                    for (int i = 0; i < mTransferFiles.size(); i++) {
                        fileLen = fileLen + (int) mTransferFiles.get(i).length();
                    }
                    sendNormalMsg(CmdHelper.getTransferFile01Cmd(type, fileLen, mTransferFiles.size()));
                } else if (type == FILE_TRANSFER_JPG) {
                    fileTransferStateProgress.setFileTransferState(FileTransferStateProgress.FileTransferState.PREPARE_TRANSFER);
                    fileTransferEmitter.onNext(fileTransferStateProgress);
                    mSendingFile = parsePicFile(mSendingFile);
                    fileTransferStateProgress.setSendingFile(mSendingFile);
                    fileTransferStateProgress.setFileTransferState(FileTransferStateProgress.FileTransferState.TRANSFER_ING);
                    fileTransferEmitter.onNext(fileTransferStateProgress);
                    sendNormalMsg(CmdHelper.getTransferFile01Cmd(type, (int) mSendingFile.length(), 1));
                }
            }
        });
    }

    @Override
    public void socketNotify(int state, Object obj) {
        try {
            switch (state) {
                case MSG:
                    byte[] msg = (byte[]) obj;

                    MsgBean msgBean = CmdHelper.getPayLoadJson(msg);
                    switch (msgBean.head) {
                        case HEAD_VERIFY:
                            if (msgBean.cmdIdStr.equals(CMD_STR_8001)) {
                                LogUtils.logBlueTooth("握手成功");
                                removeCallBackRunner(mConnectTimeoutRunner);
                                sendNormalMsg(CmdHelper.getBiuVerifyCmd());
                            } else if (msgBean.cmdIdStr.equals(CMD_STR_8002)) {
                                boolean verify_result = CmdHelper.verificationCmd(BtUtils.bytesToHexString(msgBean.payload));
                                LogUtils.logBlueTooth("校验结果：" + verify_result);
                                if (connectEmitter != null) {
                                    connectEmitter.onNext(VERIFIED);
                                }
                            }
                            break;

                        case HEAD_COMMON:
                            switch (msgBean.cmdIdStr) {
                                case CMD_STR_8001://基本信息
                                    BasicInfo basicInfo = GsonUtils.fromJson(msgBean.payloadJson, BasicInfo.class);
                                    mBasicInfo = basicInfo;
                                    if (emitterBaseInfo != null) {
                                        emitterBaseInfo.onSuccess(mBasicInfo);
                                    }

                                    break;
                                case CMD_STR_8002:

                                    break;

                                case CMD_STR_8003://电量消息

                                    if (batteryEmitter != null) {
                                        LogUtils.logBlueTooth("电量信息：" + msgBean.payloadJson);
                                        mBatteryStateInfo = GsonUtils.fromJson(msgBean.payloadJson, BatteryStateInfo.class);
                                        batteryEmitter.onNext(mBatteryStateInfo);
                                    }

                                    break;

                                case CMD_STR_8004://通知消息

                                    if (sendNotifyEmitter != null) {
                                        sendNotifyEmitter.onSuccess(msg[16] == 1);
                                    }

                                    break;
                                case CMD_STR_8007:
                                    byte sync_s = msg[16];
                                    LogUtils.logBlueTooth("时间同步结果:" + (sync_s == 1));

                                    if (emitterSyncTime != null) {
                                        emitterSyncTime.onSuccess(sync_s == 1);
                                    }
                                    break;

                                case CMD_STR_8008:
                                    LogUtils.logBlueTooth("AppView:" + msgBean.payloadJson);
                                    if (emitterGetAppView != null) {
                                        AppViewBean appViewBean = GsonUtils.fromJson(msgBean.payloadJson, AppViewBean.class);
                                        emitterGetAppView.onSuccess(appViewBean);
                                    }

                                    break;
                                case CMD_STR_8009://APP 视图设置
                                    if (singleSetAppViewEmitter != null) {
                                        singleSetAppViewEmitter.onSuccess(msg[16] == 1);
                                    }
                                    break;

                                case CMD_STR_8010://设置/删除表盘

                                    int type = msg[16]; // 1设定 2删除
                                    int actResult = msg[17]; //是否操作成功
                                    int reason = msg[18]; //是否操作成功

                                    if (dialDelEmitter != null) {
                                        DialDelStateBean dialDelStateBean = new DialDelStateBean();
                                        dialDelStateBean.setId(delDialId);
                                        dialDelStateBean.setReason(reason);
                                        dialDelStateBean.setResult(actResult == 1);

                                        dialDelEmitter.onSuccess(dialDelStateBean);
                                    }

                                    break;

                                case CMD_STR_8014://获取单个表盘状态

                                    if (getDialEmitter != null) {
                                        DialStatus dialStatus = GsonUtils.fromJson(msgBean.payloadJson, DialStatus.class);
                                        getDialEmitter.onSuccess(dialStatus);
                                    }

                                    LogUtils.logBlueTooth("表盘当前信息：" + msgBean.payloadJson);

                                    break;

                                case CMD_STR_800C://是否打开时间同步

                                    int mode = msg[16];
                                    int value_time = msg[17];

                                    LogUtils.logBlueTooth("时间同步 mode:" + mode + " value:" + value_time);

                                    switch (mode) {
                                        case TIME_SYNC_SET:
                                            boolean openTimeSync = value_time == 1;

                                            if (timeSetEmitter != null) {
                                                timeSetEmitter.onSuccess(openTimeSync);
                                            }
//                                            if (openTimeSync) {
//                                                syncTime();
//                                            }

                                            break;

                                        case TIME_SYNC_SEARCH:
                                            boolean isTimeSync = value_time == 1;
                                            LogUtils.logBlueTooth("时间是否打开：" + isTimeSync);

                                            if (timeStateEmitter != null) {
                                                timeStateEmitter.onSuccess(isTimeSync);
                                            }
//                                            if (isTimeSync) {//如果是打开的状态同步
//                                                syncTime();
//                                            }

                                            break;

                                    }

                                    break;


                                case CMD_STR_800F://获取我的表盘

                                    if (msgBean.divideType == DIVIDE_N_2) {
                                        mMyDialList.clear();
                                        addDialList(msgBean);
                                    } else {
                                        if (msgBean.divideType == DIVIDE_Y_F_2) {
                                            mMyDialList.clear();
                                            addDialList(msgBean);
                                            return;
                                        } else if (msgBean.divideType == DIVIDE_Y_M_2) {
                                            addDialList(msgBean);
                                            return;
                                        } else if (msgBean.divideType == DIVIDE_Y_E_2) {
                                            addDialList(msgBean);
                                        }
                                    }

                                    LogUtils.logBlueTooth("我的表盘个数：" + mMyDialList);
                                    if (getAppViewEmitter != null) {
                                        getAppViewEmitter.onSuccess(mMyDialList);
                                    }

                                    break;

                                case CMD_STR_8017:
                                    if (msg.length == 17) {
                                        int ringState = msg[16];
//                                        if (mGetDeviceRingStateListener != null) {
//                                            mGetDeviceRingStateListener.onGetState(ringState, 0, 0, 0, 0);
//                                        }

                                        if (shakeEmitterSingle != null) {
                                            ShakeBean shakeRingState = new ShakeBean();
                                            shakeRingState.setRingState(ringState);
                                            shakeEmitterSingle.onSuccess(shakeRingState);
                                        }

                                        if (observableShakeEmitter != null) {
                                            ShakeBean shakeRingState = new ShakeBean();
                                            shakeRingState.setRingState(ringState);
                                            observableShakeEmitter.onNext(shakeRingState);
                                            observableShakeEmitter.onComplete();
                                        }

                                    } else if (msg.length > 17) {
                                        int ringState = msg[16];
                                        int msgShake = msg[17];
                                        int crowShake = msg[18];
                                        int sysShake = msg[19];
                                        int armScreen = msg[20];

//                                        if (mGetDeviceRingStateListener != null) {
//                                            mGetDeviceRingStateListener.onGetState(ringState, msgShake, crowShake, sysShake, armScreen);
//                                        }

                                        if (shakeEmitterSingle != null) {
                                            ShakeBean shakeRingState = new ShakeBean();
                                            shakeRingState.setRingState(ringState);
                                            shakeRingState.setMsgShake(msgShake);
                                            shakeRingState.setCrowShake(crowShake);
                                            shakeRingState.setSystemShake(sysShake);
                                            shakeRingState.setArmScreen(armScreen);
                                            shakeEmitterSingle.onSuccess(shakeRingState);
                                        }

                                        if (observableShakeEmitter != null) {
                                            ShakeBean shakeRingState = new ShakeBean();
                                            shakeRingState.setRingState(ringState);
                                            shakeRingState.setMsgShake(msgShake);
                                            shakeRingState.setCrowShake(crowShake);
                                            shakeRingState.setSystemShake(sysShake);
                                            shakeRingState.setArmScreen(armScreen);
                                            observableShakeEmitter.onNext(shakeRingState);
                                            observableShakeEmitter.onComplete();
                                        }
                                    }

                                    break;

                                case CMD_STR_8018:
                                    int ringStateResult = msg[16];

                                    if (setDeviceEmitter != null) {
                                        setDeviceEmitter.onSuccess(ringStateResult == 1);
                                    }

                                    break;

                                case CMD_STR_8019:
                                    sendNormalMsg(CmdHelper.getDeviceRingStateRespondCmd());
                                    int ctype = msg[16];
                                    int vValue = msg[17];

                                    if (switchStateBeanEmitter != null) {
                                        SwitchStateBean switchStateBean = new SwitchStateBean();
                                        switchStateBean.setType(ctype);
                                        switchStateBean.setValue(vValue);
                                        switchStateBeanEmitter.onNext(switchStateBean);
                                    }

                                    break;

                                case CMD_STR_801A:
                                    if (searchPhoneEmitter != null) {
                                        searchPhoneEmitter.onNext(0);
                                    }

                                    break;
                                case CMD_STR_801C:
                                    if (setAlarmEmitter != null) {
                                        setAlarmEmitter.onSuccess(msg[16] == 1);
                                    }
                                    break;
                                case CMD_STR_801D:
                                case CMD_STR_801E:
                                    if (getAlarmEmitter != null) {
                                        AlarmBean alarmBean = new AlarmBean();
                                        alarmBean.open = msg[16];
                                        alarmBean.hour = msg[17];
                                        alarmBean.min = msg[18];
                                        alarmBean.repeat = msg[19];
                                        getAlarmEmitter.onSuccess(alarmBean);
                                    }
                                    break;

                                case CMD_STR_8020:
                                    sendNormalMsg(CmdHelper.getRespondSuccessCmd(CMD_ID_8020));
                                    if (searchPhoneEmitter != null) {
                                        searchPhoneEmitter.onNext(1);
                                    }
                                    break;

                                case CMD_STR_8021:
                                    if (searchDeviceEmitter != null) {
                                        searchDeviceEmitter.onSuccess(true);
                                    }
                                    break;

                                case CMD_STR_8022:

                                    if (msgBean.divideType == DIVIDE_N_2) {
                                        mContactBeans.clear();
                                        addContactList(msgBean);
                                    } else {
                                        if (msgBean.divideType == DIVIDE_Y_F_2) {
                                            mContactBeans.clear();
                                            addContactList(msgBean);
                                            return;
                                        } else if (msgBean.divideType == DIVIDE_Y_M_2) {
                                            addContactList(msgBean);
                                            return;
                                        } else if (msgBean.divideType == DIVIDE_Y_E_2) {
                                            addContactList(msgBean);
                                        }
                                    }

                                    LogUtils.logBlueTooth("通讯录列表：" + mContactBeans);

                                    if (contactListEmitter != null) {
                                        contactListEmitter.onSuccess(mContactBeans);
                                    }

                                    break;

                                case CMD_STR_8023:
                                    int addContactResult = msg[16];

                                    if (appAddContactEmitter != null) {
                                        if (addContactResult == 1) {
                                            appAddContactEmitter.onSuccess(true);
                                        } else {
                                            appAddContactEmitter.onSuccess(false);
                                        }
                                    }

                                    break;
                                case CMD_STR_8024:

                                    sendNormalMsg(CmdHelper.getRespondSuccessCmd(CMD_ID_8024));

                                    byte[] nameArray = new byte[CONTACT_NAME_LEN];
                                    byte[] phoneArray = new byte[CONTACT_PHONE_LEN];

                                    System.arraycopy(msgBean.payload, 0, nameArray, 0, CONTACT_NAME_LEN);
                                    System.arraycopy(msgBean.payload, CONTACT_NAME_LEN, phoneArray, 0, CONTACT_PHONE_LEN);

                                    String name = new String(nameArray);
                                    String phone = new String(phoneArray);

                                    ContactBean contactBean = new ContactBean(name, phone);

                                    if (deviceAddContactEmitter != null) {
                                        deviceAddContactEmitter.onNext(contactBean);
                                    }

                                    break;

                                case CMD_STR_8025:
                                    int delContactResult = msg[16];
                                    if (appDelContactEmitter != null) {
                                        appDelContactEmitter.onSuccess(delContactResult == 1);
                                    }
                                    break;
                                case CMD_STR_8026:
                                    sendNormalMsg(CmdHelper.getRespondSuccessCmd(CMD_ID_8026));

                                    byte[] nameByteArray = new byte[CONTACT_NAME_LEN];
                                    byte[] phoneByteArray = new byte[CONTACT_PHONE_LEN];

                                    System.arraycopy(msgBean.payload, 0, nameByteArray, 0, CONTACT_NAME_LEN);
                                    System.arraycopy(msgBean.payload, CONTACT_NAME_LEN, phoneByteArray, 0, CONTACT_PHONE_LEN);

                                    ContactBean devDelContactBean = new ContactBean(new String(nameByteArray), new String(phoneByteArray));

                                    if (deviceDelEmitter != null) {
                                        deviceDelEmitter.onNext(devDelContactBean);
                                    }

                                    break;

                                case CMD_STR_8027:
                                    if (contactActionType == CONTACT_ACTION_LIST) {
                                        sendNormalMsg(CmdHelper.getContactListCmd((byte) 1));
                                    } else if (contactActionType == CONTACT_ACTION_ADD) {
                                        sendNormalMsg(CmdHelper.getAddContactCmd(mAddContact.getName(), mAddContact.getNumber()));
                                    } else if (contactActionType == CONTACT_ACTION_DELETE) {
                                        sendNormalMsg(CmdHelper.getDeleteContactCmd(mDeleteContact.getName(), mDeleteContact.getNumber()));
                                    }

                                    break;

                                case CMD_STR_8028:
                                    LogUtils.logBlueTooth("收到拍照命令");
                                    if (cameraActionEmitter != null) {
                                        cameraActionEmitter.onNext(1);
                                        respondTakePhoto((byte) 1);
                                    }
                                    break;

                                case CMD_STR_802B:
                                    LogUtils.logBlueTooth("接收 前后摄切换、闪光灯切换");
                                    if (cameraSetEmitter != null) {
                                        byte action = msg[16];
                                        byte stateOn = msg[17];
                                        CameraSetActionBean setActionBean = new CameraSetActionBean();
                                        setActionBean.setAction(action);
                                        setActionBean.setOn(stateOn);
                                        cameraSetEmitter.onNext(setActionBean);

                                        sendNormalMsg(CmdHelper.getCameraRespondCmd(CMD_ID_802B, (byte) 1));
                                    }
                                    break;

                                case CMD_STR_802C:
                                    LogUtils.logBlueTooth("发送 前后摄切换、闪光灯切换");
                                    break;

                                case CMD_STR_8029:
                                    byte action = msg[16];
                                    LogUtils.logBlueTooth("收到设备打开App 相机:" + action);
//                                    continueUpdateFrame = action == 1;
//                                    LogUtils.logBlueTooth("收到设备操作App命令 - 更新frame数据continueUpdateFrame：" + continueUpdateFrame);
                                    if (deviceCallCameraEmitter != null) {
                                        deviceCallCameraEmitter.onSuccess(action == 1);
                                    }
                                    break;

                                case CMD_STR_802A:
                                    LogUtils.logBlueTooth("App操作设备相机：" + msg[16]);
                                    if (requestDeviceCameraEmitter != null) {
                                        requestDeviceCameraEmitter.onSuccess(msg[16] == 1);
                                    }
                                    break;

                                case CMD_STR_801B:
                                    LogUtils.logBlueTooth("天气请求：" + msg[16]);
                                    if (emitterSingle != null) {
                                        emitterSingle.onSuccess(msg[16]);
                                    }

                                    break;

                                case CMD_STR_802D:
                                    if (actionSupportEmitter != null) {
                                        mActionSupportBean = getActionSupportBean(msgBean.payload);
                                        actionSupportEmitter.onSuccess(mActionSupportBean);
                                    }
                                    break;
                            }

                            break;

                        case HEAD_SPORT_HEALTH:
                            switch (msgBean.cmdIdStr) {
                                case CMD_STR_8001://初始化信息
                                    LogUtils.logBlueTooth("初始化消息：" + msgBean.payloadJson);
//                                    mSportInitInfo = GsonUtils.fromJson(msgBean.payloadJson, SportInitInfo.class);
                                    if (getSportInfoEmitter != null) {
                                        getSportInfoEmitter.onSuccess(msgBean.payloadJson);
                                    }

                                    break;

                                case CMD_STR_8004://设置初始化信息
                                    if (setSportInfoEmitter != null) {
                                        SetSportInfo setSportInfo = new SetSportInfo();
                                        setSportInfo.setInitType(initInfoType);
                                        setSportInfo.setSuccess(msg[16] == 1);
                                        setSportInfoEmitter.onSuccess(setSportInfo);
                                    }
                                    break;

                                case CMD_STR_8002://累计步数
                                    int step = ByteUtil.bytesToInt(msgBean.payload);
                                    LogUtils.logBlueTooth("今天累计步数：" + step);

                                    if (stepEmitter != null) {
//                                        if (mSportInitInfo != null) {
                                        stepEmitter.onSuccess(step);
//                                        } else {
//                                            mGetStepListener.onGetFail("Please get SportHealthInitInfo first!");
//                                        }
                                    }

                                    break;

                                case CMD_STR_8003://当天心率
                                    byte minRate = msg[16];
                                    byte maxRate = msg[17];
                                    byte avRate = msg[18];
                                    LogUtils.logBlueTooth("心率记录数据 maxRate：" + maxRate + " minRate:" + minRate + " avRate:" + avRate);

                                    if (rateEmitter != null) {
                                        RateBean rateBean = new RateBean();
                                        rateBean.setAvRate(avRate);
                                        rateBean.setMaxRate(maxRate);
                                        rateBean.setMinRate(minRate);
                                        rateEmitter.onSuccess(rateBean);
                                    }

                                    break;

                                case CMD_STR_8008:
                                    break;

                                case CMD_STR_8009://当天血氧
                                    int bloodOx = msg[16] & 0xFF;
                                    LogUtils.logBlueTooth("今天血氧：" + bloodOx);
                                    if (getBloodOxEmitter != null) {
                                        getBloodOxEmitter.onSuccess(bloodOx);
                                    }

                                    break;
                                case CMD_STR_800A://当天血糖

                                    ByteBuffer byteBuffer = ByteBuffer.wrap(msgBean.payload).order(ByteOrder.LITTLE_ENDIAN);
                                    float bloodSugar = byteBuffer.getFloat();
                                    LogUtils.logBlueTooth("今天血糖float：" + bloodSugar);

                                    if (getBloodSugarEmitter != null) {
                                        getBloodSugarEmitter.onSuccess(bloodSugar);
                                    }

                                    break;
                                case CMD_STR_800B://当天血压
                                    int bloodPressMin = msg[16] & 0xFF;
                                    int bloodPressMax = msg[17] & 0xFF;

                                    LogUtils.logBlueTooth("今天血压 低：" + bloodPressMin + "-高：" + bloodPressMax);
                                    if (getBloodPressEmitter != null) {

                                        BloodPressBean bloodPressBean = new BloodPressBean();
                                        bloodPressBean.setBloodPressMin(bloodPressMin);
                                        bloodPressBean.setBloodPressMax(bloodPressMax);

                                        getBloodPressEmitter.onSuccess(bloodPressBean);
                                    }

                                    break;

                                case CMD_STR_800C://睡眠设置
                                    int sleepOpen = msg[16];
                                    int startHour = msg[17];
                                    int startMin = msg[18];
                                    int endHour = msg[19];
                                    int endMin = msg[20];

                                    LogUtils.logBlueTooth("睡眠设置获取 打开：" + sleepOpen + " 开始时：" + startHour + "-分：" + startMin + "   结束时：" + endHour + "-分：" + endMin);
                                    if (sleepSetEmitter != null) {

                                        SleepSetBean sleepSetBean = new SleepSetBean();
                                        sleepSetBean.setStartH(startHour);
                                        sleepSetBean.setStartM(startMin);
                                        sleepSetBean.setEndH(endHour);
                                        sleepSetBean.setEndM(endMin);
                                        sleepSetBean.setOpenState(sleepOpen);

                                        sleepSetEmitter.onSuccess(sleepSetBean);
                                    }

                                    if (sleepSetObservableEmitter != null) {
                                        SleepSetBean sleepSetBean = new SleepSetBean();
                                        sleepSetBean.setStartH(startHour);
                                        sleepSetBean.setStartM(startMin);
                                        sleepSetBean.setEndH(endHour);
                                        sleepSetBean.setEndM(endMin);
                                        sleepSetBean.setOpenState(sleepOpen);

                                        sleepSetObservableEmitter.onNext(sleepSetBean);
                                    }

                                    break;
                                case CMD_STR_800D:
                                    int sleepOpen2 = msg[16];
                                    int startHour2 = msg[17];
                                    int startMin2 = msg[18];
                                    int endHour2 = msg[19];
                                    int endMin2 = msg[20];

                                    LogUtils.logBlueTooth("睡眠设置从同步 打开：" + sleepOpen2 + " 开始时：" + startHour2 + "-分：" + startMin2 + "   结束时：" + endHour2 + "-分：" + endMin2);
                                    if (sleepSetEmitter != null) {
                                        SleepSetBean sleepSetBean = new SleepSetBean();

                                        sleepSetBean.setStartH(startHour2);
                                        sleepSetBean.setStartM(startMin2);
                                        sleepSetBean.setEndH(endHour2);
                                        sleepSetBean.setEndM(endMin2);
                                        sleepSetBean.setOpenState(sleepOpen2);

                                        sleepSetEmitter.onSuccess(sleepSetBean);
                                    }

                                    if (sleepSetObservableEmitter != null) {
                                        SleepSetBean sleepSetBean = new SleepSetBean();
                                        sleepSetBean.setStartH(startHour2);
                                        sleepSetBean.setStartM(startMin2);
                                        sleepSetBean.setEndH(endHour2);
                                        sleepSetBean.setEndM(endMin2);
                                        sleepSetBean.setOpenState(sleepOpen2);

                                        sleepSetObservableEmitter.onNext(sleepSetBean);
                                    }

                                    sendNormalMsg(CmdHelper.getRespondSuccessCmd(CMD_ID_800D));

                                    break;
                                case CMD_STR_800E:
                                    int setSleepResult = msg[16];
                                    if (setSleepEmitter != null) {
                                        setSleepEmitter.onSuccess(setSleepResult == 1);
                                    }
                                    break;

                                case CMD_STR_800F:

                                    LogUtils.logBlueTooth("睡眠返回：" + msgBean);
                                    if (msgBean.divideType == DIVIDE_N_2) {
                                        mSleepRecordBeans.clear();
                                        if (msgBean.payload != null) {
                                            byte[] sleepData = msgBean.payload;
                                            int count = sleepData.length / 3;

                                            for (int i = 0; i < count; i++) {
                                                SleepRecordBean sleepRecordBean = new SleepRecordBean();
                                                int hour = sleepData[i * 3];
                                                int min = sleepData[i * 3 + 1];
                                                int state_type = sleepData[i * 3 + 2];
                                                sleepRecordBean.setHour(hour);
                                                sleepRecordBean.setMinute(min);
                                                sleepRecordBean.setState(state_type);

                                                mSleepRecordBeans.add(sleepRecordBean);
                                            }
                                        }

                                    } else {
                                        byte[] payload = msgBean.payload;
                                        byte[] indexArr = new byte[4];
                                        byte[] sleepData = new byte[payload.length - indexArr.length];
                                        System.arraycopy(payload, 0, indexArr, 0, indexArr.length);
                                        System.arraycopy(payload, 4, sleepData, 0, sleepData.length);

                                        int count = sleepData.length / 3;

                                        for (int i = 0; i < count; i++) {
                                            SleepRecordBean sleepRecordBean = new SleepRecordBean();
                                            int hour = sleepData[i * 3];
                                            int min = sleepData[i * 3 + 1];
                                            int state_type = sleepData[i * 3 + 2];
                                            sleepRecordBean.setHour(hour);
                                            sleepRecordBean.setMinute(min);
                                            sleepRecordBean.setState(state_type);

                                            mSleepRecordBeans.add(sleepRecordBean);
                                        }
                                    }

                                    LogUtils.logBlueTooth("获取睡眠数据：" + mSleepRecordBeans);
                                    if (sleepRecordEmitter != null) {
                                        sleepRecordEmitter.onSuccess(mSleepRecordBeans);
                                    }

                                    break;
                            }

                            break;

                        case HEAD_CAMERA_PREVIEW:
                            switch (msgBean.cmdIdStr) {
                                case CMD_STR_8001:
                                    ByteBuffer byteBuffer = ByteBuffer.wrap(msg).order(ByteOrder.LITTLE_ENDIAN);
                                    byte camera_pre_allow = byteBuffer.get(16);//是否容许同步画面 0允许 1不允许
                                    byte reason = byteBuffer.get(17);
                                    byte[] lenArray = new byte[4];
                                    System.arraycopy(msg, 18, lenArray, 0, lenArray.length);
                                    mOtaProcess = 0;
                                    mCellLength = ByteBuffer.wrap(lenArray).order(ByteOrder.LITTLE_ENDIAN).getInt();
                                    mCellLength = mCellLength - 5;
                                    LogUtils.logBlueTooth("相机预览传输包大小：" + mCellLength);

                                    continueUpdateFrame = camera_pre_allow == 1;

                                    LogUtils.logBlueTooth("是否支持相机预览 continueUpdateFrame：" + continueUpdateFrame);

                                    if (cameraPreviewEmitter != null) {

                                        CameraPreviewState cameraPreviewState = new CameraPreviewState(camera_pre_allow, reason);
                                        cameraPreviewEmitter.onSuccess(cameraPreviewState);
                                    }

                                    if (camera_pre_allow == 1) {
                                        LogUtils.logBlueTooth("预发送数据：" + mH264FrameMap.getFrameCount());
                                        if (!mH264FrameMap.isEmpty()) {
                                            LogUtils.logBlueTooth("发送的帧ID：" + mLatestIframeId);
                                            mCameraFrameInfo = mH264FrameMap.getFrame(mLatestIframeId);
                                            LogUtils.logBlueTooth("发送的帧信息：" + mCameraFrameInfo);
                                            sendFrameDataAsync(mCameraFrameInfo);
                                        } else {
                                            needNewH264Frame = true;
                                        }
                                    }

                                    break;

                                case CMD_STR_8003:
                                    byte frameSuccess = msg[16];

                                    LogUtils.logBlueTooth("发送成功：" + frameSuccess);
                                    LogUtils.logBlueTooth("发送下一帧：" + mH264FrameMap.getFrameCount());

                                    LogUtils.logBlueTooth("continueUpdateFrame 03:" + continueUpdateFrame);

                                    if (continueUpdateFrame) {
                                        if (mH264FrameMap.isEmpty()) {
                                            needNewH264Frame = true;
                                            LogUtils.logBlueTooth("没数据了-》1");
                                            return;
                                        }

                                        if (frameSuccess == 1) { //发送成功
                                            //删除掉已经发送成功之前的帧
                                            mH264FrameMap.removeOldFrames(mCameraFrameInfo.frameId);
                                            LogUtils.logBlueTooth("移除发送过的帧数");

                                            if (mCameraFrameInfo.frameId == mLatestIframeId) {
                                                mCameraFrameInfo = mH264FrameMap.getFrame(mLatestPframeId);
                                                LogUtils.logBlueTooth("没有新的I帧,发送最新的P帧：" + mCameraFrameInfo);
                                            } else {
                                                if (mLatestIframeId > mCameraFrameInfo.frameId) {
                                                    mCameraFrameInfo = mH264FrameMap.getFrame(mLatestIframeId);
                                                    LogUtils.logBlueTooth("有新的I帧,发送最新的I帧：" + mCameraFrameInfo);
                                                } else {
                                                    mCameraFrameInfo = mH264FrameMap.getFrame(mLatestPframeId);
                                                    LogUtils.logBlueTooth("没有新的I帧,发送最新的P帧：" + mCameraFrameInfo);
                                                }
                                            }
                                        } else { //发送失败
                                            if (mCameraFrameInfo.frameType == 0) {

                                                if (mLatestIframeId > mCameraFrameInfo.frameId) {
                                                    mCameraFrameInfo = mH264FrameMap.getFrame(mLatestIframeId);
                                                    LogUtils.logBlueTooth("P发送失败,发送最新的I帧：" + mCameraFrameInfo);
                                                } else {
                                                    mCameraFrameInfo = mH264FrameMap.getFrame(mLatestPframeId);
                                                    LogUtils.logBlueTooth("P发送失败,发送最新的P帧：" + mCameraFrameInfo);
                                                }

                                            } else {
                                                mCameraFrameInfo = mH264FrameMap.getFrame(mLatestIframeId);
                                                LogUtils.logBlueTooth("发送失败,发送最新的I帧：" + mCameraFrameInfo);
                                            }
                                        }

                                        sendFrameDataAsync(mCameraFrameInfo);
                                    } else {
                                        LogUtils.logBlueTooth("相机关闭，停止发送");
                                        mH264FrameMap.clear();
                                    }

                                    break;
                            }
                            break;

                        case HEAD_FILE_SPP_A_2_D:
                            switch (msgBean.cmdIdStr) {
                                case CMD_STR_8001://文件传输是否可以
                                    mSendFileCount = 0;
                                    mErrorSend = false;
                                    ByteBuffer byteBuffer = ByteBuffer.wrap(msg);
                                    byte ota_allow = byteBuffer.get(16);//是否容许升级 0允许 1不允许
                                    byte reason = byteBuffer.get(17);//是否容许升级 0允许 1不允许
                                    LogUtils.logBle("1.允许传输:" + ota_allow);

                                    if (ota_allow == 1) {
                                        mSendingFile = mTransferFiles.get(0);
                                        fileTransferStateProgress.setSendingFile(mSendingFile);
                                        sendNormalMsg(CmdHelper.getTransferFile02Cmd(readFileBytes(mSendingFile).length, mSendingFile.getName()));
                                    } else {
                                        if (fileTransferEmitter != null) {
                                            FileTransferStateProgress.FileTransferState.TRANSFER_FAIL.failReason = reason;
                                            fileTransferStateProgress.setFileTransferState(FileTransferStateProgress.FileTransferState.TRANSFER_FAIL);
                                            fileTransferEmitter.onNext(fileTransferStateProgress);
                                        }
                                    }

                                    break;

                                case CMD_STR_8002://文件名称和长度
                                    byte[] lenArray = new byte[4];
                                    System.arraycopy(msg, 16, lenArray, 0, lenArray.length);
                                    mOtaProcess = 0;
                                    mCellLength = ByteBuffer.wrap(lenArray).order(ByteOrder.LITTLE_ENDIAN).getInt() - 4;
                                    LogUtils.logBlueTooth("cell_length:" + mCellLength);

                                    if (mCellLength != 0) {
                                        ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<Object>() {
                                            @Override
                                            public Object doInBackground() throws Throwable {
                                                mFileDataArray = readFileBytes(mTransferFiles.get(mSendFileCount));
                                                continueSendFileData(0, mFileDataArray);
                                                return null;
                                            }

                                            @Override
                                            public void onSuccess(Object result) {

                                            }
                                        });
                                    } else {
                                        mCanceledSend = true;
                                        transferEnd();
                                        fileTransferStateProgress.setFileTransferState(FileTransferStateProgress.FileTransferState.TRANSFER_FAIL);
                                        fileTransferEmitter.onNext(fileTransferStateProgress);
                                    }

                                    break;
                                case CMD_STR_8003://出错的时候会返回

                                    ByteBuffer buffer = ByteBuffer.wrap(msg).order(ByteOrder.LITTLE_ENDIAN);

                                    byte isRight = buffer.get(16);

                                    mTransferring = true;

                                    mErrorSend = isRight != 1;
                                    mOtaProcess = buffer.getInt(17);

                                    LogUtils.logBlueTooth("返回消息状态:" + mErrorSend + " 返回ota_process:" + mOtaProcess + " 总包个数:" + mPackageCount);

                                    if (mErrorSend) {//失败

                                        removeCallBackRunner(mTransferTimeoutRunner);
//                                        mOtaProcess = mOtaProcess > 0 ? mOtaProcess - 1 : mOtaProcess;
                                        LogUtils.logBlueTooth("出错后序号：" + mOtaProcess);
                                        if (mTransferRetryCount < MAX_RETRY_COUNT) {
                                            sendErrorMsg(mOtaProcess);
                                        } else {
                                            transferEnd();
                                        }

                                    } else {//成功
                                        mTransferRetryCount = 0;
                                        LogUtils.logBlueTooth("掰正的消息：" + mOtaProcess);

                                        ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<Object>() {
                                            @Override
                                            public Object doInBackground() throws Throwable {
                                                if (mOtaProcess != mPackageCount - 1) {
                                                    mOtaProcess++;
                                                    continueSendFileData(mOtaProcess, mFileDataArray);
                                                }

                                                return null;
                                            }

                                            @Override
                                            public void onSuccess(Object result) {

                                            }
                                        });
                                    }

                                    break;
                                case CMD_STR_8004://传输结束
                                    mTransferRetryCount = 0;
                                    mOtaProcess = 0;
                                    mBtEngine.clearMsgQueue();

                                    removeCallBackRunner(mTransferTimeoutRunner);

                                    byte data_success = ByteBuffer.wrap(msg).get(16);
                                    LogUtils.logBlueTooth("发送结果:" + data_success);
                                    mBtEngine.clearMsgQueue();
                                    if (data_success == (byte) 1) {
                                        mSendFileCount++;

                                        if (mSendFileCount >= mTransferFiles.size()) {

                                            mTransferring = false;
                                            if (fileTransferEmitter != null) {
                                                fileTransferStateProgress.setFinishCount(mSendFileCount);
                                                fileTransferStateProgress.setSendingFile(mSendingFile);
                                                fileTransferStateProgress.setTotalCount(mTransferFiles.size());
                                                fileTransferStateProgress.setFileTransferState(FileTransferStateProgress.FileTransferState.TRANSFER_ALL_FINISH);
                                                fileTransferEmitter.onNext(fileTransferStateProgress);
                                            }

                                            transferEnd();
                                        } else {

                                            mSendingFile = mTransferFiles.get(mSendFileCount);
                                            fileTransferStateProgress.setFinishCount(mSendFileCount);
                                            fileTransferStateProgress.setSendingFile(mSendingFile);
                                            fileTransferStateProgress.setFileTransferState(FileTransferStateProgress.FileTransferState.TRANSFER_STEP_FINISH);

                                            if (fileTransferEmitter != null) {
                                                fileTransferEmitter.onNext(fileTransferStateProgress);
                                            }

                                            sendNormalMsg(CmdHelper.getTransferFile02Cmd(readFileBytes(mSendingFile).length, mSendingFile.getName()));
                                        }

                                    } else {
                                        mTransferring = false;
                                        if (fileTransferEmitter != null) {
                                            fileTransferEmitter.onError(new RuntimeException("data error fail"));
                                        }
                                        transferEnd();
                                    }

                                    break;
                                case CMD_STR_8005://App端取消传输
                                    mTransferring = false;
                                    mCanceledSend = true;
                                    if (cancelTransferEmitter != null) {
                                        cancelTransferEmitter.onSuccess(true);
                                    }
                                    break;

                                case CMD_STR_8006://设备端取消传输
                                    mTransferring = false;
                                    mCanceledSend = true;
                                    byte reason_cancel = ByteBuffer.wrap(msg).get(16);

                                    LogUtils.logBlueTooth("设备取消传输原因：" + reason_cancel);

                                    transferEnd();
                                    if (fileTransferEmitter != null) {
                                        fileTransferEmitter.onError(new RuntimeException("cancel transfer by device"));
                                    }

                                    break;
                            }
                            break;
                    }
                    break;

                case BUSY:
                    LogUtils.logBlueTooth("设备正在忙...");

                    break;

                case TIME_OUT:
                    msgTimeOut((byte[]) obj);
                    break;

                case ON_SOCKET_CLOSE:
                    LogUtils.logBlueTooth(" ON_SOCKET_CLOSE");
                    mConnectTryCount = 0;
                    mSppStateBean.isSppConnected = false;
                    mBtEngine.clearStateMap();
                    break;

                case CONNECTED:
                    removeCallBackRunner(mConnectTimeoutRunner);
                    BluetoothDevice device = ((BluetoothDevice) obj);
                    LogUtils.logBlueTooth("SJBtSdk CONNECTED:" + device.getAddress());
                    sendNormalMsg(CmdHelper.getBiuShakeHandsCmd());
                    mConnectTryCount = 0;
                    mSppStateBean.isSppConnected = true;

                    if (connectEmitter != null) {
                        connectEmitter.onNext(CONNECTED);
                    }

                    if (mBtStateReceiver != null) {
                        mBtStateReceiver.setmSocket(mBtEngine.getmSocket());
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private ActionSupportBean getActionSupportBean(byte[] actionStateArray) {

        ActionSupportBean actionSupportBean = new ActionSupportBean();
        actionSupportBean.backFromDev = true;

        int actionPosition = 0;
        final char v = '0';

        for (byte b : actionStateArray) {
            String binaryString = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', v);
            StringBuilder sb = new StringBuilder(binaryString);
            binaryString = sb.reverse().toString();

            LogUtils.logBlueTooth("字节的二进制表示：" + binaryString);

            // 逐位判断二进制表示的每一位是0还是1
            for (int i = 0; i < 8; i++) {
                char bit = binaryString.charAt(i);

                switch (actionPosition) {
                    case 0:
                        actionSupportBean.weatherSupportState = bit - v;
                        LogUtils.logBlueTooth("天气为 " + bit);
                        break;
                    case 1:
                        actionSupportBean.sportSupportState = bit - v;
                        LogUtils.logBlueTooth("健身为 " + bit);
                        break;
                    case 2:
                        actionSupportBean.rateSupportState = bit - v;
                        LogUtils.logBlueTooth("心率为 " + bit);
                        break;
                    case 3:
                        actionSupportBean.cameraSupportState = bit - v;
                        LogUtils.logBlueTooth("相机为 " + bit);
                        break;
                    case 4:
                        actionSupportBean.notifyMsgSupportState = bit - v;
                        LogUtils.logBlueTooth("通知为 " + bit);
                        break;
                    case 5:
                        actionSupportBean.alarmSupportState = bit - v;
                        LogUtils.logBlueTooth("闹钟为 " + bit);
                        break;
                    case 6:
                        actionSupportBean.musicSupportState = bit - v;
                        LogUtils.logBlueTooth("音乐为 " + bit);
                        break;
                    case 7:
                        actionSupportBean.contactSupportState = bit - v;
                        LogUtils.logBlueTooth("联系人为 " + bit);
                        break;
                    case 8:
                        actionSupportBean.searchDeviceSupportState = bit - v;
                        LogUtils.logBlueTooth("查找手表为 " + bit);
                        break;
                    case 9:
                        actionSupportBean.searchPhoneSupportState = bit - v;
                        LogUtils.logBlueTooth("查找手机为 " + bit);
                        break;
                    case 10:
                        actionSupportBean.appViewSupportState = bit - v;
                        LogUtils.logBlueTooth("【设置】应用视图为 " + bit);
                        break;
                    case 11:
                        actionSupportBean.setRingSupportState = bit - v;
                        LogUtils.logBlueTooth("【设置】来电响铃为" + bit);
                        break;
                    case 12:
                        actionSupportBean.setNotifyTouchSupportState = bit - v;
                        LogUtils.logBlueTooth("【设置】通知触感为" + bit);
                        break;
                    case 13:
                        actionSupportBean.setWatchTouchSupportState = bit - v;
                        LogUtils.logBlueTooth("【设置】表冠触感为" + bit);
                        break;

                    case 14:
                        actionSupportBean.setSystemTouchSupportState = bit - v;
                        LogUtils.logBlueTooth("【设置】系统触感反馈为 " + bit);
                        break;

                    case 15:
                        actionSupportBean.armSupportState = bit - v;
                        LogUtils.logBlueTooth("【设置】抬腕亮屏为 " + bit);
                        break;

                    case 16:
                        actionSupportBean.bloodOxSupportState = bit - v;
                        LogUtils.logBlueTooth("血氧为" + bit);
                        break;

                    case 17:
                        actionSupportBean.bloodPressSupportState = bit - v;
                        LogUtils.logBlueTooth("血压为" + bit);
                        break;

                    case 18:
                        actionSupportBean.bloodSugarSupportState = bit - v;
                        LogUtils.logBlueTooth("血糖为" + bit);
                        break;

                    case 19:
                        actionSupportBean.sleepSupportState = bit - v;
                        LogUtils.logBlueTooth("睡眠为" + bit);
                        break;

                    case 20:
                        actionSupportBean.ebookSupportState = bit - v;
                        LogUtils.logBlueTooth("电子书为" + bit);
                        break;

                    case 21:
                        actionSupportBean.supportSlowModel = bit - v;
                        LogUtils.logBlueTooth("支持慢速模式为" + bit);
                        break;

                    case 22:
                        actionSupportBean.supportCameraPreview = bit - v;
                        LogUtils.logBlueTooth("支持相机预览模式为" + bit);
                        break;
                }
                actionPosition++;
            }
        }

        return actionSupportBean;

    }

    @Override
    public void socketNotifyError(byte[] obj) {

    }

    @Override
    public void onConnectFailed(BluetoothDevice device, String msg) {

        LogUtils.logBlueTooth("onConnectFailed 连接失败:" + device.getAddress() + " msg:" + msg);

        if (device.getAddress().equals(mSppStateBean.macAddress)) {
            if (msg.contains("read failed, socket might closed or timeout")
                    || msg.contains("Connection reset by peer")
                    || msg.contains("Connect refused")) {

                reConnect(device, msg);
            } else {
                mConnectTryCount = 0;
                mSppStateBean.isSppConnected = false;
                BtConnectStateEvent disConnectStateEvent = new BtConnectStateEvent();
                disConnectStateEvent.setDevice(device);
                disConnectStateEvent.setState(CONNECT_STATE_DISCONNECTED);
                EventBus.getDefault().post(disConnectStateEvent);
            }
        }
    }

    private void reConnect(BluetoothDevice deviceAddress, String msg) {
        removeCallBackRunner(mConnectTimeoutRunner);
        mBtEngine.clearStateMap();
        LogUtils.logBlueTooth("重试次数：" + mConnectTryCount);

        mHandler.postDelayed(new Runnable() {
            @SuppressLint("MissingPermission")
            @Override
            public void run() {
//                if (mConnectTryCount != -1) {
                if (mConnectTryCount < CONNECT_RETRY_COUNT) {
                    mConnectTryCount++;
                    connect(deviceAddress.getAddress());
                } else {
                    mConnectTryCount = 0;
                    mSppStateBean.isSppConnected = false;

                    if (connectEmitter != null) {
                        connectEmitter.onNext(CONNECT_FAIL);
                        connectEmitter.onComplete();
                    }
                }
//                } else {
//                    mConnectTryCount = 0;
//                }
            }

        }, 1000);
    }

    private void msgTimeOut(byte[] msg) {

        if (!mBtAdapter.isEnabled()) {
            mBtEngine.clearMsgQueue();
            mBtEngine.clearStateMap();

            if (connectEmitter != null) {
                connectEmitter.onNext(BT_DISABLE);
            }

            return;
        }

        if (mCanceledSend) {
            mBtEngine.clearMsgQueue();
            return;
        }

        MsgBean msgBean = CmdHelper.getPayLoadJson(msg);
        switch (msgBean.head) {

            case HEAD_VERIFY:
                switch (msgBean.cmdIdStr) {
                    case CMD_STR_8001_TIME_OUT:
                    case CMD_STR_8002_TIME_OUT:
                        mBtEngine.clearStateMap();
                        mBtEngine.clearMsgQueue();

                        if (connectEmitter != null) {
                            connectEmitter.onNext(CONNECT_FAIL);
                            connectEmitter.onComplete();
                        }

                        break;
                }

                break;

            case HEAD_COMMON:

                switch (msgBean.cmdIdStr) {
                    case CMD_STR_8001_TIME_OUT:
                        if (emitterBaseInfo != null) {
                            emitterBaseInfo.onError(new RuntimeException("get basicInfo timeout!"));
                        }
                        break;
                    case CMD_STR_8002_TIME_OUT:
                        break;
                    case CMD_STR_8003_TIME_OUT:
                        if (batteryEmitter != null) {
                            batteryEmitter.onError(new RuntimeException("get battery timeout!"));
                        }
                        break;
                    case CMD_STR_8004_TIME_OUT:
                        if (sendNotifyEmitter != null) {
                            sendNotifyEmitter.onError(new RuntimeException("send notify timeout!"));
                        }
                        break;
                    case CMD_STR_8005_TIME_OUT:
                        break;
                    case CMD_STR_8006_TIME_OUT:
                        break;
                    case CMD_STR_8007_TIME_OUT:
                        if (emitterSyncTime != null) {
                            emitterSyncTime.onError(new RuntimeException("get sync time timeout!"));
                        }
                        break;
                    case CMD_STR_8008_TIME_OUT:
                        if (emitterGetAppView != null) {
                            emitterGetAppView.onError(new RuntimeException("get app views"));
                        }
                        break;

                    case CMD_STR_8009_TIME_OUT:
                        if (singleSetAppViewEmitter != null) {
                            singleSetAppViewEmitter.onError(new RuntimeException("set app view time out"));
                        }
                        break;
                    case CMD_STR_800A_TIME_OUT:
                        break;
                    case CMD_STR_800B_TIME_OUT:
                        break;
                    case CMD_STR_800C_TIME_OUT:
                        if (timeStateEmitter != null) {
                            timeStateEmitter.onError(new RuntimeException("get time state timeout!"));
                        }
                        break;
                    case CMD_STR_800D_TIME_OUT:
                        break;
                    case CMD_STR_800E_TIME_OUT:
                        break;
                    case CMD_STR_800F_TIME_OUT:
                        if (getAppViewEmitter != null) {
                            getAppViewEmitter.onError(new RuntimeException("getAppViews timeout!"));
                        }

                        break;
                    case CMD_STR_8010_TIME_OUT:
                        if (dialDelEmitter != null) {
                            dialDelEmitter.onError(new RuntimeException("delete dial timeout!"));
                        }
                        break;
                    case CMD_STR_8011_TIME_OUT:
                        break;
                    case CMD_STR_8012_TIME_OUT:
                        break;
                    case CMD_STR_8013_TIME_OUT:
                        break;
                    case CMD_STR_8014_TIME_OUT:
                        if (getDialEmitter != null) {
                            getDialEmitter.onError(new RuntimeException("get dial timeout!"));
                        }
                        break;

                    case CMD_STR_8017_TIME_OUT:
//                        if (mGetDeviceRingStateListener != null) {
//                            mGetDeviceRingStateListener.onTimeOut(msgBean);
//                        }

                        if (shakeEmitterSingle != null) {
                            shakeEmitterSingle.onError(new RuntimeException("shake timeout!"));
                        }

                        break;

                    case CMD_STR_8018_TIME_OUT:
//                        if (mSetDeviceRingStateListener != null) {
//                            mSetDeviceRingStateListener.onTimeOut(msgBean);
//                        }

                        if (setDeviceEmitter != null) {
                            setDeviceEmitter.onError(new RuntimeException("set state timeout!"));
                        }

                        break;

                    case CMD_STR_801C_TIME_OUT:
                        if (setAlarmEmitter != null) {
                            setAlarmEmitter.onError(new RuntimeException("set alarm timeout!"));
                        }
                        break;
                    case CMD_STR_801E_TIME_OUT:
                        if (getAlarmEmitter != null) {
                            getAlarmEmitter.onError(new RuntimeException("get alarm timeout!"));
                        }
                        break;
                    case CMD_STR_8021_TIME_OUT:
                        if (searchDeviceEmitter != null) {
                            searchDeviceEmitter.onError(new RuntimeException("search device timeout!"));
                        }
                        break;

                    case CMD_STR_8022_TIME_OUT:
                        if (contactListEmitter != null) {
                            contactListEmitter.onError(new RuntimeException("get contact list timeout!"));
                        }
                        break;

                    case CMD_STR_8023_TIME_OUT:
                        if (appAddContactEmitter != null) {
                            appAddContactEmitter.onError(new RuntimeException("app add contact time out"));
                        }
                        break;

                    case CMD_STR_8025_TIME_OUT:
                        if (appDelContactEmitter != null) {
                            appDelContactEmitter.onError(new RuntimeException("app delete contact timeout"));
                        }
                        break;

                    case CMD_STR_8026_TIME_OUT:
                        break;

                    case CMD_STR_8027_TIME_OUT:
                        if (contactActionType == CONTACT_ACTION_LIST) {
                            contactListEmitter.onError(new RuntimeException("get contact list timeout!"));
                        } else if (contactActionType == CONTACT_ACTION_ADD) {
                            appAddContactEmitter.onError(new RuntimeException("app add contact time out"));
                        } else if (contactActionType == CONTACT_ACTION_DELETE) {
                            appDelContactEmitter.onError(new RuntimeException("app delete contact timeout"));
                        }
                        break;

                    case CMD_STR_8029_TIME_OUT:

                        break;

                    case CMD_STR_802A_TIME_OUT:
                        if (requestDeviceCameraEmitter != null) {
                            requestDeviceCameraEmitter.onError(new RuntimeException("request device camera timeout!"));
                        }
                        break;

                    case CMD_STR_802D_TIME_OUT:
                        if (actionSupportEmitter != null) {
                            actionSupportEmitter.onError(new RuntimeException("action bean error!"));
                        }
                        break;
                }

                break;

            case HEAD_SPORT_HEALTH:

                switch (msgBean.cmdIdStr) {
                    case CMD_STR_8001_TIME_OUT:
                        if (getSportInfoEmitter != null) {
                            getSportInfoEmitter.onError(new RuntimeException("get sport info timeout"));
                        }
                        break;
                    case CMD_STR_8002_TIME_OUT:
                        if (stepEmitter != null) {
                            stepEmitter.onError(new RuntimeException("get step timeout"));
                        }
                        break;
                    case CMD_STR_8003_TIME_OUT:
                        if (rateEmitter != null) {
                            rateEmitter.onError(new RuntimeException("get rate timeout"));
                        }
                        break;
                    case CMD_STR_8008_TIME_OUT:
                        if (sleepRecordEmitter != null) {
                            sleepRecordEmitter.onError(new RuntimeException("get sleep record timeout"));
                        }
                        break;
                    case CMD_STR_8009_TIME_OUT:
                        if (getBloodOxEmitter != null) {
                            getBloodOxEmitter.onError(new RuntimeException("get blood ox timeout"));
                        }
                        break;
                    case CMD_STR_800A_TIME_OUT:
                        if (getBloodSugarEmitter != null) {
                            getBloodSugarEmitter.onError(new RuntimeException("get blood sugar timeout"));
                        }
                        break;
                    case CMD_STR_800B_TIME_OUT:
                        if (getBloodPressEmitter != null) {
                            getBloodPressEmitter.onError(new RuntimeException("get blood press timeout"));
                        }
                        break;

                    case CMD_STR_800C_TIME_OUT:
                        if (sleepSetEmitter != null) {
                            sleepSetEmitter.onError(new RuntimeException("sleep set timeout"));
                        }
                        break;

                    case CMD_STR_800D_TIME_OUT:
                        if (setSleepEmitter != null) {
                            setSleepEmitter.onError(new RuntimeException("set sleep timeout"));
                        }
                        break;
                }

                break;

            case HEAD_CAMERA_PREVIEW:
                mTransferring = false;
                switch (msgBean.cmdIdStr) {
                    case CMD_STR_8001_TIME_OUT:
                        if (cameraPreviewEmitter != null) {
                            cameraPreviewEmitter.onError(new RuntimeException("camera preview timeout"));
                        }

                        break;
                }
                break;

            case HEAD_FILE_SPP_A_2_D:
                mTransferring = false;
                switch (msgBean.cmdIdStr) {
                    case CMD_STR_8001_TIME_OUT:
                        break;

                    case CMD_STR_8002_TIME_OUT:
                        if (mTransferRetryCount < MAX_RETRY_COUNT) {
                            mTransferRetryCount++;
                            mSendingFile = mTransferFiles.get(mSendFileCount);
                            sendNormalMsg(CmdHelper.getTransferFile02Cmd(readFileBytes(mSendingFile).length, mSendingFile.getName()));
                        } else {
                            transferEnd();
                        }
                        break;

                    case CMD_STR_8003_TIME_OUT:

                        if (mTransferRetryCount < MAX_RETRY_COUNT) {
                            mTransferRetryCount++;
                            sendNormalMsg(CmdHelper.getTransfer03Cmd(mOtaProcess, getOtaDataInfoNew(mFileDataArray, mOtaProcess), mDivide));
                        } else {
                            if (fileTransferEmitter != null) {
                                fileTransferEmitter.onError(new RuntimeException("8003 time out"));
                            }
                        }

                        break;

                    case CMD_STR_8004_TIME_OUT:
                        if (mTransferRetryCount < MAX_RETRY_COUNT) {
                            mTransferRetryCount++;
                            byte[] ota_data = CmdHelper.getTransfer04Cmd();
                            sendNormalMsg(ota_data);
                        } else {
                            if (fileTransferEmitter != null) {
                                fileTransferEmitter.onError(new RuntimeException("8003 time out"));
                            }
                        }
                        break;
                }
                break;
        }

    }

    private void addContactList(MsgBean msgBean) {

        if (msgBean.payload == null) {
            return;
        }

        int contactLen = msgBean.payload.length;
        int contactCount = contactLen / CONTACT_MSG_LEN;

        LogUtils.logBlueTooth("本包通讯录个数：" + contactCount);
        LogUtils.logBlueTooth("本包通讯录长度：" + contactLen);

        byte[] byteDialAll = new byte[contactLen];
        System.arraycopy(msgBean.payload, 0, byteDialAll, 0, contactLen);

        for (int i = 0; i < contactCount; i++) {
            byte[] dialItemArray = new byte[CONTACT_MSG_LEN];
            System.arraycopy(byteDialAll, CONTACT_MSG_LEN * i, dialItemArray, 0, CONTACT_MSG_LEN);

            byte[] nameByteArray = new byte[CONTACT_NAME_LEN];
            byte[] phoneByteArray = new byte[CONTACT_PHONE_LEN];

            System.arraycopy(dialItemArray, 0, nameByteArray, 0, CONTACT_NAME_LEN);
            System.arraycopy(dialItemArray, CONTACT_NAME_LEN, phoneByteArray, 0, CONTACT_PHONE_LEN);

            String name = new String(nameByteArray);
            String phone = new String(phoneByteArray);

            LogUtils.logBlueTooth(i + "name:" + name);
            LogUtils.logBlueTooth(i + "phone:" + phone);

            ContactBean deviceDailItem = new ContactBean(name, phone);
            mContactBeans.add(deviceDailItem);
        }
    }

    private void addDialList(MsgBean msgBean) {

        if (msgBean.payload == null) {
            return;
        }

        int dialIdLen = msgBean.payload.length;
        int dialCount = dialIdLen / DIAL_MSG_LEN;
        int order = msgBean.payload[0];
//        LogUtils.logBlueTooth("表盘信息 序号：$order")
        byte[] byteDialAll = new byte[dialIdLen];
        System.arraycopy(msgBean.payload, 0, byteDialAll, 0, dialIdLen);

        for (int i = 0; i < dialCount; i++) {

            byte[] dialItemArray = new byte[17];
            System.arraycopy(byteDialAll, DIAL_MSG_LEN * i, dialItemArray, 0, DIAL_MSG_LEN);

            ByteBuffer dialByteBuffer = ByteBuffer.wrap(dialItemArray);
//            LogUtils.logBlueTooth("表盘信息 完整：${dialItemArray.size}")

            byte builtIn = dialByteBuffer.get();
//            LogUtils.logBlueTooth("表盘信息 内置：$builtIn")

            byte[] byteArrayId = new byte[16];
            System.arraycopy(dialItemArray, 1, byteArrayId, 0, byteArrayId.length);
            String id = BtUtils.bytesToHexString(byteArrayId).toLowerCase(Locale.ROOT);

//            LogUtils.logBlueTooth("表盘信息 id：$id")
            DialItem deviceDailItem = new DialItem(id, builtIn, "");

            mMyDialList.add(deviceDailItem);
        }
    }

    private void transferEnd() {
        try {
            mBtEngine.clearMsgQueue();
            mOtaProcess = 0;
            mTransferRetryCount = 0;
            mTransferring = false;
            mSendFileCount = 0;

            removeCallBackRunner(mTransferTimeoutRunner);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendFrameDataAsync(CameraFrameInfo frameInfo) {
        if (frameInfo == null) {
            needNewH264Frame = true;
            LogUtils.logBlueTooth("没数据了-》2");
            return;
        }

        mCameraHandler.post(new Runnable() {
            @Override
            public void run() {
                sendFrameData(frameInfo);
            }
        });
    }

    /**
     * 继续发送帧数据,分包,需要在单独的线程中执行
     *
     * @param cameraFrameInfo
     */
    private void sendFrameData(CameraFrameInfo cameraFrameInfo) {
        byte[] dataArray = cameraFrameInfo.frameData;
        mFramePackageCount = dataArray.length / mCellLength;
        mFrameLastLen = dataArray.length % mCellLength;

        if (mFrameLastLen != 0) {
            mFramePackageCount = mFramePackageCount + 1;
        }

        if (mFramePackageCount > 0) {
            for (int i = 0; i < mFramePackageCount; i++) {

                this.mOtaProcess = i;
                if (dataArray == null || !continueUpdateFrame) {
                    break;
                }

                try {
                    mTransferring = true;
                    OtaCmdInfo info = getCameraPreviewCmdInfo(cameraFrameInfo, i);
//                    LogUtils.logBlueTooth("执行发送：" + info + " 分包类型：" + mDivide);
                    sendNormalMsg(CmdHelper.getCameraPreviewDataCmd02(info.payload, mDivide));
                    if (i != mFramePackageCount - 1) {
                        Thread.sleep(MSG_INTERVAL_FRAME);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mTransferring = false;
                }
            }
        } else {
            mDivide = DIVIDE_N_2;
            sendNormalMsg(CmdHelper.getCameraPreviewDataCmd02(dataArray, mDivide));
        }

    }

    private void continueSendFileData(int startProcess, @NonNull byte[] dataArray) {
        mPackageCount = dataArray.length / mCellLength;
        mLastDataLength = dataArray.length % mCellLength;

        if (mLastDataLength != 0) {
            mPackageCount = mPackageCount + 1;
        }

        for (int i = startProcess; i < mPackageCount; i++) {

            this.mOtaProcess = i;

            if (mCanceledSend || mErrorSend) {//取消或者中途出错
                LogUtils.logBlueTooth("消息取消或者出错：" + mOtaProcess);
                mTransferring = false;
                break;
            }

            if (dataArray == null) {
                break;
            }

            try {
                mTransferring = true;
                OtaCmdInfo info = getOtaDataInfoNew(dataArray, i);
                sendNormalMsg(CmdHelper.getTransfer03Cmd(i, info, mDivide));

                if (mHandler != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            float process_percent = 100f * (mOtaProcess + 1) / mPackageCount;

                            if (fileTransferEmitter != null) {
                                fileTransferStateProgress.setProcess(process_percent);
                                fileTransferStateProgress.setSendingFile(mTransferFiles.get(mSendFileCount));
                                fileTransferStateProgress.setFinishCount(mSendFileCount);
                                fileTransferStateProgress.setTotalCount(mSelectFileCount);
                                fileTransferEmitter.onNext(fileTransferStateProgress);
                            }

                        }
                    });
                }

                if (mActionSupportBean != null && mActionSupportBean.supportSlowModel == 1) {
                    Thread.sleep(MSG_INTERVAL_SLOW);
                } else {
                    Thread.sleep(MSG_INTERVAL);
                }

                if (mActionSupportBean == null || mActionSupportBean.supportSlowModel == 0) {
                    if (mOtaProcess == mPackageCount - 1) {
                        mHandler.postDelayed(mTransferTimeoutRunner, TRANSFER_END_TIMEOUT);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                mTransferring = false;
                LogUtils.logBlueTooth("连续发送过程中出错：" + e.getMessage());
                if (fileTransferEmitter != null) {
                    fileTransferEmitter.onError(new RuntimeException(e.getMessage()));
                }
            }
        }
    }

    Runnable mTransferTimeoutRunner = new Runnable() {
        @Override
        public void run() {
            mTransferRetryCount = 0;
            mOtaProcess = 0;
            mBtEngine.clearMsgQueue();
            mTransferring = false;

            if (fileTransferEmitter != null) {
                fileTransferEmitter.onError(new RuntimeException("8004 time out"));
            }
        }
    };

    Runnable mConnectTimeoutRunner = new Runnable() {
        @Override
        public void run() {
            mBtEngine.clearStateMap();
            mSppStateBean.isSppConnected = false;

            if (connectEmitter != null) {
                connectEmitter.onNext(CONNECT_FAIL);
                connectEmitter.onComplete();
            }
        }
    };

    private OtaCmdInfo getCameraPreviewCmdInfo(@NonNull CameraFrameInfo frameInfo, int i) {
        OtaCmdInfo info = new OtaCmdInfo();
        byte[] dataArray = frameInfo.frameData;
        if (i == 0 && mFramePackageCount > 1) {
            mDivide = DIVIDE_Y_F_2;
        } else {
            if (mFramePackageCount == 1) {
                mDivide = DIVIDE_N_2;
            } else if (i == mFramePackageCount - 1) {
                mDivide = DIVIDE_Y_E_2;
            } else {
                mDivide = DIVIDE_Y_M_2;
            }
        }

//        LogUtils.logBlueTooth("分包类型：" + mDivide);

        if (i == mFramePackageCount - 1 && mDivide != DIVIDE_N_2) {
//            LogUtils.logBlueTooth("最后一包长度：" + mFrameLastLen);
            if (mFrameLastLen == 0) {
                info.offSet = i * mCellLength;
                info.payload = new byte[mCellLength];

                System.arraycopy(dataArray, i * mCellLength, info.payload, 0, info.payload.length);
            } else {
                info.offSet = i * mCellLength;
                info.payload = new byte[mFrameLastLen];

                System.arraycopy(dataArray, i * mCellLength, info.payload, 0, info.payload.length);
            }
        } else {
            info.offSet = i * mCellLength;

            if (mDivide == DIVIDE_Y_F_2 || mDivide == DIVIDE_N_2) {//首包或者不分包的时候需要传帧大小
                LogUtils.logBlueTooth("本帧大小:" + dataArray.length);
                LogUtils.logBlueTooth("帧数据长度：" + BtUtils.intToHex(dataArray.length));

                if (dataArray.length < mCellLength) {
                    LogUtils.logBlueTooth("不分包：" + mDivide);
                    mCellLength = dataArray.length;
                }

                ByteBuffer byteBuffer = ByteBuffer.allocate(mCellLength + 5).order(ByteOrder.LITTLE_ENDIAN);
                byteBuffer.put(frameInfo.frameType);
                byteBuffer.putInt(dataArray.length);

                byte[] payload = new byte[mCellLength];
                System.arraycopy(dataArray, 0, payload, 0, payload.length);
                LogUtils.logBlueTooth("数据payload：" + payload.length);
                byteBuffer.put(payload);

                info.payload = byteBuffer.array();
                LogUtils.logBlueTooth("首包payload总长度：" + info.payload.length);
            } else {
                info.payload = new byte[mCellLength];
                System.arraycopy(dataArray, i * mCellLength, info.payload, 0, info.payload.length);
            }
        }

        return info;
    }

    private OtaCmdInfo getOtaDataInfoNew(@NonNull byte[] dataArray, int otaProcess) {
        OtaCmdInfo info = new OtaCmdInfo();

        if (otaProcess == 0 && mPackageCount > 1) {
            mDivide = DIVIDE_Y_F_2;
        } else {
            if (otaProcess == mPackageCount - 1) {
                mDivide = DIVIDE_Y_E_2;
            } else {
                mDivide = DIVIDE_Y_M_2;
            }
        }

//        LogUtils.logBlueTooth("分包类型：" + mDivide);

        if (otaProcess != mPackageCount - 1) {
            info.offSet = otaProcess * mCellLength;
            info.payload = new byte[mCellLength];

            System.arraycopy(dataArray, otaProcess * mCellLength, info.payload, 0, info.payload.length);
        } else {
//            LogUtils.logBlueTooth("最后一包长度：" + mLastDataLength);
            if (mLastDataLength == 0) {
                info.offSet = otaProcess * mCellLength;
                info.payload = new byte[mCellLength];

                System.arraycopy(dataArray, otaProcess * mCellLength, info.payload, 0, info.payload.length);
            } else {
                info.offSet = otaProcess * mCellLength;
                info.payload = new byte[mLastDataLength];

                System.arraycopy(dataArray, otaProcess * mCellLength, info.payload, 0, info.payload.length);
            }
        }

        return info;
    }

    private void sendErrorMsg(int errorProcess) {
        mTransferRetryCount++;
        byte[] bytes = CmdHelper.getTransfer03Cmd(errorProcess, getOtaDataInfoNew(mFileDataArray, errorProcess), mDivide);
        sendNormalMsg(bytes);
    }

    public void sendNormalMsg(byte[] msg) {
        if (mBtEngine == null || msg == null) {
            return;
        }

        if (mTransferring) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(msg);
            byte head = byteBuffer.get();
            byte cmdId = byteBuffer.get(2);
//            LogUtils.logBlueTooth("HEAD:" + head + " cmdId:" + cmdId);
            if (isMsgStopped(head, cmdId)) {
                LogUtils.logBlueTooth("正在 传输文件中...:" + BtUtils.bytesToHexString(msg));
                return;
            }
        }

        mBtEngine.sendMsgOnWorkThread(msg);
    }

    private boolean isMsgStopped(byte head, byte cmdId) {
        return head != HEAD_FILE_SPP_A_2_D && head != HEAD_CAMERA_PREVIEW && !isCameraCmd(head, cmdId);
    }

    private boolean isCameraCmd(byte head, byte cmdId) {
        return (head == HEAD_COMMON && (cmdId == CMD_ID_8028 || cmdId != CMD_ID_8029 || cmdId != CMD_ID_802A || cmdId != CMD_ID_802B || cmdId != CMD_ID_802C));
    }

}
