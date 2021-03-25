package com.example.kotlinv10.Controller

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.example.kotlinv10.R
import com.zkteco.android.biometric.core.device.ParameterHelper
import com.zkteco.android.biometric.core.device.TransportType
import com.zkteco.android.biometric.core.utils.LogHelper
import com.zkteco.android.biometric.core.utils.ToolUtils
import com.zkteco.android.biometric.module.fingerprintreader.FingerprintCaptureListener
import com.zkteco.android.biometric.module.fingerprintreader.FingerprintSensor
import com.zkteco.android.biometric.module.fingerprintreader.FingprintFactory
import com.zkteco.android.biometric.module.fingerprintreader.ZKFingerService
import com.zkteco.android.biometric.module.fingerprintreader.exception.FingerprintException

class FingerprintService(context: Context) {


    private val VID = 6997
    private val PID = 288
    private var bstart = false
    private var isRegister = false
    private var uid = 1
    private val regtemparray = Array(3) { ByteArray(2048) } //register template buffer array
    private var enrollidx = 0
    private val lastRegTemp = ByteArray(2048)
    private var fingerprintSensor: FingerprintSensor? = null
    private val i = -1
    private val ACTION_USB_PERMISSION = "com.zkteco.silkiddemo.USB_PERMISSION"

     var context =context
    lateinit var textTest : TextView

    private val mUsbReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (ACTION_USB_PERMISSION == action) {
                synchronized(this) {
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        LogHelper.i("have permission!")
                    } else {
                        LogHelper.e("not permission!")
                    }
                }
            }
        }
    }

    fun startFingerprintSensor() {
        // Define output log level
        LogHelper.setLevel(Log.VERBOSE)
        // Start fingerprint sensor
        val fingerprintParams: MutableMap<Any?, Any?> = HashMap<Any?, Any?>()
        //set vid
        fingerprintParams[ParameterHelper.PARAM_KEY_VID] = VID
        //set pid
        fingerprintParams[ParameterHelper.PARAM_KEY_PID] = PID
//        fingerprintParams[ParameterHelper.PARAM_KEY_PID] = MainActivity.PID
        fingerprintSensor =
            FingprintFactory.createFingerprintSensor(
                context, TransportType.USB,
                fingerprintParams as MutableMap<String, Any>?
            )
    }
    fun initDevice(){
        val musbManager : UsbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
        var filter : IntentFilter = IntentFilter()
        filter.addAction(ACTION_USB_PERMISSION)
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_ATTACHED)

         context
        context.registerReceiver(mUsbReceiver, filter)

        for(device : UsbDevice in musbManager.deviceList.values){
            if (device.vendorId == VID && device.productId == PID){
                if (!musbManager.hasPermission(device)){
                    val intent = Intent(ACTION_USB_PERMISSION)
                    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
                    musbManager.requestPermission(device, pendingIntent)
                }
            }
        }
    }

    @Throws(FingerprintException::class)
    fun onBegin() {
        try {
            var i = 0;
            if (bstart)
                return
            Log.e("ERROR","--------------------------------------------------------------------" )
            try {
                fingerprintSensor!!.open(0)
            }catch (eF : FingerprintException){
                Log.e("ERROR","" + eF.message)
            }
            val  listenter : FingerprintCaptureListener = object : FingerprintCaptureListener {
                override fun captureOK(p0: ByteArray?) {
                    val imageWidth = fingerprintSensor!!.imageWidth
                    val imageHeight = fingerprintSensor!!.imageHeight
                    kotlin.run {
                        if ( p0 !=null){
                            ToolUtils.outputHexString(p0)
                            LogHelper.i("width=$imageWidth\nHeight=$imageHeight")
                          var   bitmapImageFingerprint = ToolUtils.renderCroppedGreyScaleBitmap(p0,imageWidth,imageHeight)

//                            var intent = Intent(context,FingerprintActivity::class.java)


                        //set image

                        }
                    }
                }

                override fun captureError(p0: FingerprintException?) {

                    kotlin.run{
                        LogHelper.e("capture Error " +p0?.errorCode +"\nmessage" + p0?.message)
                    }
                }

                override fun extractOK(p0: ByteArray?) {
                    kotlin.run {
                        if(isRegister) {
                            var bufids = ByteArray(256)
                            var ret = ZKFingerService.identify(p0, bufids, 55, 1)
                            if (ret > 0) {
                                var strRes = String(bufids).split("\t")
                                isRegister = false
                                // the finger is already enroll -> cancel
                                enrollidx = 0
                                return
                            }

                            if (enrollidx > 0 && ZKFingerService.verify(
                                    regtemparray[enrollidx - 1],
                                    p0
                                ) <= 0
                            ) {
                                // please press the same finger 3 times for the enrollment
                                return
                            }
                            System.arraycopy(p0, 0, regtemparray[enrollidx], 0, 2048)
                            enrollidx++
                            if (enrollidx == 3) {
                                var regTemp = ByteArray(2048)
                                if (0 < (ZKFingerService.merge(
                                        regtemparray[0],
                                        regtemparray[1],
                                        regtemparray[2],
                                        regTemp
                                    )).also { ret = it }
                                ) {
                                    ZKFingerService.save(regTemp, "0" + uid++)
                                    System.arraycopy(regTemp, 0, lastRegTemp, 0,ret)

                                    //enroll success
                                } else {
                                    //enroll failed
                                }
                                isRegister = false
                            } else {
                                // press finger in time bla bla bla
                            }

                        }else{
                            var bufids = ByteArray(256)
                            var ret = ZKFingerService.identify(p0,bufids,55,1)
                            if (ret >0){
                                var strRes = String(bufids).split("\t")
                                Toast.makeText(context,"CHECK5555555", Toast.LENGTH_SHORT).show()
                                //identify
                            }else{
                                //identify failed
                            }
                            //Base 64 Template
//                            var strBase64 = encodeToString1(p0, fingerprintSensor!!.lastTempLen)

                        }
                    }
                }

                override fun extractError(p0: Int) {
                    TODO("Not yet implemented")
                }

            }

            fingerprintSensor!!.setFingerprintCaptureListener(0,listenter)
            fingerprintSensor!!.startCapture(0)
            bstart = true
            Toast.makeText(context,"CHECK", Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            Toast.makeText(context,"CHECK12", Toast.LENGTH_SHORT).show()
        }
    }
    @Throws(FingerprintException::class)
    fun onBnStop(){
        try{
            if(bstart){
                //stop capture
                fingerprintSensor?.startCapture(0)
                bstart = false
                fingerprintSensor?.close(0)
                Toast.makeText(context,"CLOSE SCANNER", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"ALREADY CLOSE SCANNER", Toast.LENGTH_SHORT).show()
            }
        }catch (e : FingerprintException){
            Toast.makeText(context,"STOP FAILED", Toast.LENGTH_SHORT).show()
        }
    }


    fun onBnEnroll(){

        onBegin()
        if (bstart){
            isRegister = true
            enrollidx =0

            Toast.makeText(context,"Press 3 time enroll", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"Begin First", Toast.LENGTH_SHORT).show()
        }
    }

    fun onBnVerify(){
        if (bstart){
            isRegister = false
            enrollidx = 0
        }else {
            Toast.makeText(context, "Begin first", Toast.LENGTH_SHORT).show()
        }

    }
}