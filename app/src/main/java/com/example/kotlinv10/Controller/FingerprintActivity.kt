package com.example.kotlinv10.Controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.kotlinv10.R

class FingerprintActivity : AppCompatActivity()  {

    lateinit var beginBtn : Button
    lateinit var enrollBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fingerprint)

       var fingerprintService: FingerprintService = FingerprintService(this)

        beginBtn = findViewById(R.id.beginBtnFinger)
        enrollBtn = findViewById(R.id.enrollFinger)

       fingerprintService.initDevice()
        fingerprintService.startFingerprintSensor()
        fingerprintService.onBegin()

        beginBtn.setOnClickListener {
            fingerprintService.onBnVerify()
        }
        enrollBtn.setOnClickListener {
            fingerprintService.onBnEnroll()
        }


    }
}