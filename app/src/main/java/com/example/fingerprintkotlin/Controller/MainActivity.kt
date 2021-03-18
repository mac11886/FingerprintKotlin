package com.example.fingerprintkotlin.Controller

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fingerprintkotlin.R
import com.zkteco.android.biometric.core.device.ParameterHelper
import com.zkteco.android.biometric.core.device.TransportType
import com.zkteco.android.biometric.core.utils.LogHelper
import com.zkteco.android.biometric.module.fingerprintreader.FingerprintSensor
import com.zkteco.android.biometric.module.fingerprintreader.FingprintFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    private val VID = 6997
    private val PID = 288
    private val bstart = false
    private val isRegister = false
    private val uid = 1
    private val regtemparray = Array(3) {
        ByteArray(2048)
    } //register template buffer array

    private val enrollidx = 0
    private val lastRegTemp = ByteArray(2048)
    private var fingerprintSensor: FingerprintSensor? = null
    private val i = -1
    private val ACTION_USB_PERMISSION = "com.zkteco.silkiddemo.USB_PERMISSION"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("Hekkii", "sdsd")
    }

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


    public fun startFingerprintSensor() {
        // Start fingerprint sensor
//        var  fingerprintParams  = HashMap<>()
        // Start fingerprint sensor
        val fingerprintParams = HashMap<Any?, Any?>()
        //set pid
        fingerprintParams.put(ParameterHelper.PARAM_KEY_PID, PID)
        //set vid
        fingerprintParams.put(ParameterHelper.PARAM_KEY_VID, VID)
        fingerprintSensor = FingprintFactory.createFingerprintSensor(this, TransportType.USB,)
    }


    public fun initDevice(){
        val musbManager : UsbManager = this.getSystemService(Context.USB_SERVICE) as UsbManager
        var filter : IntentFilter = IntentFilter()
        filter.addAction(ACTION_USB_PERMISSION)
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_ATTACHED)

       val context : Context = this.applicationContext
        context.registerReceiver(mUsbReceiver,filter)


        for(device :UsbDevice in musbManager.deviceList.values){
            if (device.vendorId == VID && device.productId == PID){
                if (!musbManager.hasPermission(device)){
                    val intent = Intent(ACTION_USB_PERMISSION)
                    val pendingIntent = PendingIntent.getBroadcast(context,0,intent,0)
                    musbManager.requestPermission(device,pendingIntent)
                }
            }
        }
    }



    public fun onBegin() {
        try {
         var i = 0;
         if (bstart) return
            fingerprintSensor?.open(0)
        }catch (e:Exception){
            
        }
    }

}