package com.sjbt.sdk

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.os.HandlerThread
import android.text.TextUtils
import androidx.core.app.ActivityCompat
import com.base.sdk.AbUniWatch
import com.base.sdk.entity.WmDevice
import com.base.sdk.entity.WmDeviceModel
import com.base.sdk.entity.WmScanDevice
import com.base.sdk.entity.apps.WmConnectState
import com.sjbt.sdk.app.SJAbWmApps
import com.sjbt.sdk.dfu.SJTransferFile
import com.sjbt.sdk.entity.CameraFrameInfo
import com.sjbt.sdk.entity.H264FrameMap
import com.sjbt.sdk.entity.MsgBean
import com.sjbt.sdk.log.SJLog
import com.sjbt.sdk.settings.SJSettings
import com.sjbt.sdk.spp.BtStateReceiver
import com.sjbt.sdk.spp.OnBtStateListener
import com.sjbt.sdk.spp.bt.BtEngine
import com.sjbt.sdk.spp.bt.BtEngine.Listener
import com.sjbt.sdk.spp.bt.BtEngine.Listener.*
import com.sjbt.sdk.spp.cmd.CmdConfig.*
import com.sjbt.sdk.spp.cmd.CmdHelper
import com.sjbt.sdk.sync.SJSyncData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import java.io.File

object SJUniWatchSdk : AbUniWatch(), Listener {

    private val TAG = TAG_SJ + "SJUniWatchSdk"

    private var context: Context? = null
    private var msgTimeOut: Int? = null
    private var mBtStateReceiver: BtStateReceiver? = null
    private var mBtEngine = BtEngine.getInstance(this)
    private var mBtAdapter: BluetoothAdapter? = null

    private lateinit var discoveryObservableEmitter: ObservableEmitter<BluetoothDevice>

    //连接
    var mMacAddress: String? = null
    var mConnectTryCount = 0

    //文件传输相关
    var mTransferFiles: List<File>? = null
    var mFileDataArray: ByteArray? = null
    var mSendingFile: File? = null
    var mSelectFileCount = 0
    var mSendFileCount = 0
    var mCellLength = 0
    var mOtaProcess = 0
    var mCanceledSend = false
    var mErrorSend: kotlin.Boolean = false
    var mDivide: Byte = 0
    var mPackageCount = 0
    var mLastDataLength: Int = 0
    var mTransferRetryCount = 0
    var mTransferring = false

    //相机预览相关
    var mCameraFrameInfo: CameraFrameInfo? = null
    var mH264FrameMap: H264FrameMap = H264FrameMap()
    var mLatestIframeId: Long = 0
    var mLatestPframeId: Long = 0
    val mCameraThread = HandlerThread("camera_send_thread")
    var mCameraHandler: Handler? = null
    var needNewH264Frame = false
    var continueUpdateFrame: Boolean = false

    override fun socketNotify(state: Int, obj: Any?) {
        try {
            when (state) {
                MSG -> {
                    val msg = obj as ByteArray
                    val msgBean: MsgBean = CmdHelper.getPayLoadJson(msg)

                    when (msgBean.head) {
                        HEAD_VERIFY -> {

                        }

                        HEAD_COMMON -> {

                        }

                        HEAD_SPORT_HEALTH -> {

                        }

                        HEAD_CAMERA_PREVIEW -> {

                        }

                        HEAD_FILE_SPP_A_2_D -> {

                        }
                    }
                }

                TIME_OUT -> {

                }

                ON_SOCKET_CLOSE -> {

                }

                CONNECTED -> {
                    (wmConnect as SJConnect).btStateChange(WmConnectState.CONNECTED)
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun socketNotifyError(obj: ByteArray?) {
        TODO("Not yet implemented")
    }

    override fun onConnectFailed(device: BluetoothDevice?, msg: String?) {
        TODO("Not yet implemented")
    }

    override fun init(context: Context, msgTimeOut: Int) {
        this.context = context
        this.msgTimeOut = msgTimeOut
        wmSettings = SJSettings()
        wmApps = SJAbWmApps()
        wmSync = SJSyncData()
        wmConnect = SJConnect()
        wmTransferFile = SJTransferFile()

        val mBluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE)
        mBtAdapter = (mBluetoothManager as (android.bluetooth.BluetoothManager)).getAdapter()

        mBtStateReceiver = BtStateReceiver(context!!, object : OnBtStateListener {

            override fun onClassicBtDisConnect(device: BluetoothDevice) {
                SJLog.logBt(TAG, "onClassicBtDisConnect：" + device.address)

                mMacAddress?.let {
                    if (device.address == mMacAddress) {
                        (wmConnect as SJConnect).mConnectTryCount = 0

                        mTransferring = false
                        mTransferRetryCount = 0
                        mCanceledSend = true
                        mBtEngine.clearMsgQueue()
                        mBtEngine.clearStateMap()
                        wmApps?.appCamera?.stopCameraPreview()
                        wmConnect?.disconnect()

//                        removeCallBackRunner(mConnectTimeoutRunner)
                    }
                }
            }

            override fun onClassicBtConnect(device: BluetoothDevice) {
                SJLog.logBt(TAG, "onClassicBtConnect：" + device.address)
                if (!TextUtils.isEmpty(mMacAddress) && device.address == mMacAddress) {
                    wmConnect?.disconnect()
                }
            }

            override fun onClassicBtDisabled() {
                SJLog.logBt(TAG, "onClassicBtDisabled")
                mTransferring = false
                mTransferRetryCount = 0
                mCanceledSend = true
                mBtEngine.clearMsgQueue()
                mBtEngine.clearStateMap()

                wmConnect?.let {
                    it.disconnect()
                    (it as SJConnect).btStateChange(WmConnectState.BT_DISABLE)
                }

                wmApps?.appCamera?.stopCameraPreview()

//                removeCallBackRunner(mConnectTimeoutRunner)
            }

            override fun onClassicBtOpen() {
                SJLog.logBt(TAG, "onClassicBtOpen")
                wmConnect?.let {
                    (it as SJConnect).btStateChange(WmConnectState.BT_ENABLE)
                }
//                removeCallBackRunner(mConnectTimeoutRunner)
            }

            override fun onBindState(device: BluetoothDevice, bondState: Int) {
                if (bondState == BluetoothDevice.BOND_NONE) {
                    if (!TextUtils.isEmpty(mMacAddress) && device.address == mMacAddress) {
                        mConnectTryCount = 0
                        mBtEngine.clearStateMap()

                        wmConnect?.let {
                            (it as SJConnect).btStateChange(WmConnectState.CONNECT_FAIL)
                        }

//                        removeCallBackRunner(mConnectTimeoutRunner)
                        SJLog.logBt(TAG, "取消配对：" + device.address)
                    }
                }
            }

            override fun onDiscoveryDevice(device: BluetoothDevice?) {
                discoveryObservableEmitter.onNext(device)
            }

            override fun onStartDiscovery() {

            }

            override fun onStopDiscovery() {

            }
        })
    }

    override fun startDiscovery(): Observable<BluetoothDevice> {
        return Observable.create(object : ObservableOnSubscribe<BluetoothDevice> {
            override fun subscribe(emitter: ObservableEmitter<BluetoothDevice>) {
                discoveryObservableEmitter = emitter

                context?.let {
                    if (ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.BLUETOOTH_SCAN
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return
                    }

                    mBtAdapter?.startDiscovery()
                }
            }
        })
    }

    override fun stopDiscovery() {
        context?.let {
            if (ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.BLUETOOTH_SCAN
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            mBtAdapter?.cancelDiscovery()
        }
    }

    override fun getDevice(wmDeviceModel: WmDeviceModel): WmDevice? {
        val wmDevice = WmDevice(WmDeviceModel.SJ_WATCH)
        wmDevice.isRecognized = wmDeviceModel == WmDeviceModel.SJ_WATCH
        return wmDevice
    }

    override fun parseScanQr(qrString: String): WmScanDevice {
        val wmScanDevice = WmScanDevice(WmDeviceModel.SJ_WATCH)
        wmScanDevice.qrUrl = qrString
        wmScanDevice.address = qrString.substring(0, 12)

        wmScanDevice.isRecognized =
            qrString.contains("shenju") && isLegalMacAddress(wmScanDevice.address)

        return wmScanDevice
    }

    private fun isLegalMacAddress(address: String?): Boolean {
        return !TextUtils.isEmpty(address)
    }
}