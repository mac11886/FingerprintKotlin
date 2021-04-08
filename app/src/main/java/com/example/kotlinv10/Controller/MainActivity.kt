package com.example.kotlinv10.Controller

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinv10.R
import com.example.kotlinv10.model.*
import com.zkteco.android.biometric.core.device.ParameterHelper
import com.zkteco.android.biometric.core.device.TransportType
import com.zkteco.android.biometric.core.utils.LogHelper
import com.zkteco.android.biometric.core.utils.ToolUtils
import com.zkteco.android.biometric.module.fingerprintreader.FingerprintCaptureListener
import com.zkteco.android.biometric.module.fingerprintreader.FingerprintSensor
import com.zkteco.android.biometric.module.fingerprintreader.FingprintFactory
import com.zkteco.android.biometric.module.fingerprintreader.ZKFingerService
import com.zkteco.android.biometric.module.fingerprintreader.exception.FingerprintException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    private val VID = 6997
    private val PID = 288
    private var bstart = false
    lateinit var dialog: AlertDialog
    private var isRegister = false
    private var uid = 1
    private val regtemparray = Array(3) {
        ByteArray(2048)
    } //register template buffer array

    private var enrollidx = 0
    private val lastRegTemp = ByteArray(2048)
    private var fingerprintSensor: FingerprintSensor? = null
    private val i = -1
    private val ACTION_USB_PERMISSION = "com.zkteco.silkiddemo.USB_PERMISSION"
    lateinit var imageView: ImageView


    lateinit var textTest: TextView
    lateinit var timeText: TextView
    lateinit var dateText: TextView
    lateinit var helloText: TextView
    lateinit var checkInBtn: Button
    lateinit var checkOutBtn: Button
    lateinit var nameText: TextView
    lateinit var showText : TextView
    var userList: List<DataUser>? = null
    lateinit var status: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppPreferences.init(this)
        initUi()
        setTimeAndDate()

//        val intStr = "09"
//
//        val intInt = intStr.toInt()
//
//        Toast.makeText(this, intInt.toString(), Toast.LENGTH_SHORT).show()

        // set ID of Activity
        var helloText = findViewById<TextView>(R.id.helloText)
        var testText = findViewById<TextView>(R.id.textTest)

        imageView = findViewById(R.id.imageView)
//        imageView.setImageResource(R.drawable.ic_attendance)

        testText.setOnClickListener {
            Intent(this, ThirdManageActivity::class.java).also { intent -> startActivity(intent) }
        }

        helloText.setOnClickListener {
            Intent(this, FirstManageActivity::class.java).also { intent ->
                startActivity(intent)
            }
        }
        //API
        try {
            val call = ApiObject.apiObject.getDataUser(AppPreferences.branch_id!!.toInt())
            Toast.makeText(this@MainActivity, "" + AppPreferences.branch_id, Toast.LENGTH_SHORT)
                .show()
            call.enqueue(object : Callback<List<DataUser>> {
                override fun onResponse(
                    call: Call<List<DataUser>>,
                    response: Response<List<DataUser>>
                ) {

                    DataHolder.allDataUser = response.body()
                    userList = DataHolder.allDataUser

                    Log.e("dataholder", "" + DataHolder.allDataUser)
                }

                override fun onFailure(call: Call<List<DataUser>>, t: Throwable) {
                    Log.e("dataholdererror", "" + DataHolder.allDataUser)

                }

            })
        } catch (e: Exception) {
            Toast.makeText(this, "catch get datauser", Toast.LENGTH_SHORT).show()
        }

        //fingerprint
        startFingerprintSensor()
        initDevice()
        userList = DataHolder.allDataUser
        Log.e("userList", userList.toString())
        checkInBtn.setOnClickListener {
            onBegin()
            status = "in"
        }
        checkOutBtn.setOnClickListener {
            onBegin()
            status = "out"
        }




        AlertDialog.loadingDialog(this)


        val someHandler = Handler(mainLooper)
        someHandler.postDelayed({ AlertDialog.dismissDialog() }, 3000)

    }

    fun setTimeAndDate() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            SimpleDateFormat("dd MMM yyyy", Locale.US).format(Date()).also { dateText.text = it }
        }

        val someHandler = Handler(mainLooper)
        someHandler.postDelayed(object : Runnable {
            override fun run() {
                timeText.setText(java.text.SimpleDateFormat("HH:mm", Locale.US).format(Date()))
                someHandler.postDelayed(this, 1000)
            }
        }, 10)

    }


    fun initUi() {
        // set ID of Activity
        helloText = findViewById(R.id.helloText)
        checkInBtn = findViewById(R.id.checkInButton)
        checkOutBtn = findViewById(R.id.checkOutButton)
        timeText = findViewById(R.id.timeTextView)
        dateText = findViewById(R.id.dateTextView)
        showText = findViewById(R.id.showText)
        nameText = findViewById(R.id.nameUser)
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
                this, TransportType.USB,
                fingerprintParams as MutableMap<String, Any>?
            )
    }

    fun initDevice() {
        val musbManager: UsbManager = this.getSystemService(Context.USB_SERVICE) as UsbManager
        var filter: IntentFilter = IntentFilter()
        filter.addAction(ACTION_USB_PERMISSION)
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_ATTACHED)

        val context: Context = this.applicationContext
        context.registerReceiver(mUsbReceiver, filter)


        for (device: UsbDevice in musbManager.deviceList.values) {
            if (device.vendorId == VID && device.productId == PID) {
                if (!musbManager.hasPermission(device)) {
                    val intent = Intent(ACTION_USB_PERMISSION)
                    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
                    musbManager.requestPermission(device, pendingIntent)
                }
            }
        }
    }

    fun getUser(id: Int): DataUser {
        return userList!!.get(id)
    }


    @Throws(FingerprintException::class)
    fun onBegin() {
        try {
            var i = 0;
            if (bstart)
                return
            fingerprintSensor!!.open(0)

            try {
                if (userList != null) {
                    for (user in userList!!) {
                        if (user != null) {
                            if (user.fingerprint != null) {
                                var fingerByte1 = Base64.decode(user.fingerprint.first_fingerprint, Base64.NO_WRAP)
                                var fingerByte2 = Base64.decode(user.fingerprint.second_fingerprint, Base64.NO_WRAP)
                                ZKFingerService.save(fingerByte1, "$i")
                                ZKFingerService.save(fingerByte2, "0$i")
                                //save finger
                                Toast.makeText(
                                    this@MainActivity,
                                    " save finger",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            else {
                                //null fingerprint
//                                Toast.makeText(
//                                    this@MainActivity,
//                                    " null fingerprint",
//                                    Toast.LENGTH_SHORT
//                                ).show()
                            }
                            i++
                        } else {
                            // null user
//                            Toast.makeText(
//                                this@MainActivity,
//                                " null user",
//                                Toast.LENGTH_SHORT
//                            ).show()
                        }

                    }


                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "else can't save finger",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (eF: FingerprintException) {
                Toast.makeText(this@MainActivity, "error save finger", Toast.LENGTH_SHORT)
                    .show()
            }


            val listenter: FingerprintCaptureListener = object : FingerprintCaptureListener {
                override fun captureOK(p0: ByteArray?) {
                    val imageWidth = fingerprintSensor!!.imageWidth
                    val imageHeight = fingerprintSensor!!.imageHeight
                    runOnUiThread {
                        if (p0 != null) {
                            ToolUtils.outputHexString(p0)
                            LogHelper.i("width=$imageWidth\nHeight=$imageHeight")
                            var bitmapImageFingerprint =
                                ToolUtils.renderCroppedGreyScaleBitmap(p0, imageWidth, imageHeight)
                            //set image
                            imageView.setImageResource(R.drawable.ic_attendance)

                        }
                    }
                }

                override fun captureError(p0: FingerprintException?) {

                    runOnUiThread {
                        LogHelper.e("capture Error " + p0?.errorCode + "\nmessage" + p0?.message)
                    }
                }

                override fun extractOK(p0: ByteArray?) {
                    runOnUiThread(Runnable {
                        if (isRegister) {
                            var bufids = ByteArray(256)
                            var ret = ZKFingerService.identify(p0, bufids, 55, 1)
                            if (ret > 0) {
                                var strRes = String(bufids).split("\t")
                                isRegister = false
                                // the finger is already enroll -> cancel
                                enrollidx = 0
                                return@Runnable
                            }

                            if (enrollidx > 0 && ZKFingerService.verify(
                                    regtemparray[enrollidx - 1],
                                    p0
                                ) <= 0
                            ) {
                                // please press the same finger 3 times for the enrollment
                                return@Runnable
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
//                                    ZKFingerService.save(regTemp, "0" + uid++)
                                    System.arraycopy(regTemp, 0, lastRegTemp, 0, ret)

                                    //enroll success
                                } else {
                                    //enroll failed
                                }
                                isRegister = false
                            } else {
                                // press finger in time bla bla bla
                            }

                        } else {
                            var bufids = ByteArray(256)
                            var ret = ZKFingerService.identify(p0, bufids, 55, 1)
                            if (ret > 0) {
                                var strRes = String(bufids).split("\t")
//                                Toast.makeText(
//                                    applicationContext,
//                                    "identity",
//                                    Toast.LENGTH_SHORT
//                                ).show()
                                //identify
                                showText.text = "threshold " + strRes[1] + "%"
//                                Toast.makeText(applicationContext, strRes[0].toInt().toString(), Toast.LENGTH_SHORT).show()
                                nameText.text = userList!![strRes[0].toInt()].name

                                var id = userList!![strRes[0].toInt()].id
                                var companyId = userList!![strRes[0].toInt()].company_id
                                var branchId = userList!![strRes[0].toInt()].branch_id
                                if (status == "in"){
                                    attendance(id,companyId,branchId, status)
                                    onBnStop()
                                }else{
                                    attendance(id,companyId,branchId, status)
                                    onBnStop()
                                }

                            } else {
                                //identify failed
                                Toast.makeText(
                                    applicationContext,
                                    "identity failed",
                                    Toast.LENGTH_SHORT
                                ).show()


                            }
                            //Base 64 Template
//                            var strBase64 = encodeToString1(p0, fingerprintSensor!!.lastTempLen)

                        }
                    })
                }

                override fun extractError(p0: Int) {
                    TODO("Not yet implemented")
                }

            }

            fingerprintSensor!!.setFingerprintCaptureListener(0, listenter)
            fingerprintSensor!!.startCapture(0)
            bstart = true
            Toast.makeText(this, "CHECK", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "can't open ", Toast.LENGTH_SHORT).show()
        }
    }

    @Throws(FingerprintException::class)
    fun onBnStop() {
        try {
            if (bstart) {
                //stop capture
                fingerprintSensor?.stopCapture(0)
                bstart = false
                fingerprintSensor?.close(0)
                Toast.makeText(this, "CLOSE SCANNER", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "ALREADY CLOSE SCANNER", Toast.LENGTH_SHORT).show()
            }
        } catch (e: FingerprintException) {
            Toast.makeText(this, "STOP FAILED", Toast.LENGTH_SHORT).show()
        }
    }


    fun attendance(id: Int,companyId : Int,branchId :Int,status:String){
        ApiObject.apiObject.saveAttendance(id,companyId,branchId,status).enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Toast.makeText(this@MainActivity," send data success",Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@MainActivity,"can't send data",Toast.LENGTH_SHORT).show()
            }
        })

    }



}