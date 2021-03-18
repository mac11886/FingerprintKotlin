//package com.example.fingerprintkotlin
//
//import android.util.Base64
//import android.view.View
//import com.zkteco.android.biometric.core.utils.LogHelper
//import com.zkteco.android.biometric.core.utils.ToolUtils
//import com.zkteco.android.biometric.module.fingerprintreader.FingerprintCaptureListener
//import com.zkteco.android.biometric.module.fingerprintreader.ZKFingerService
//import com.zkteco.android.biometric.module.fingerprintreader.exception.FingerprintException
//
//@Throws(FingerprintException::class)
//fun OnBnBegin(view: View?) {
//    try {
//        if (bstart) return
//        fingerprintSensor!!.open(0)
//        val listener: FingerprintCaptureListener = object : FingerprintCaptureListener {
//            override fun captureOK(fpImage: ByteArray) {
//                val width = fingerprintSensor!!.imageWidth
//                val height = fingerprintSensor!!.imageHeight
//                runOnUiThread {
//                    if (null != fpImage) {
//                        ToolUtils.outputHexString(fpImage)
//                        LogHelper.i("width=$width\nHeight=$height")
//                        val bitmapFp =
//                            ToolUtils.renderCroppedGreyScaleBitmap(fpImage, width, height)
//                        //saveBitmap(bitmapFp);
//                        imageView.setImageBitmap(bitmapFp)
//                    }
//                    //textView.setText("FakeStatus:" + fingerprintSensor.getFakeStatus());
//                }
//            }
//
//            override fun captureError(e: FingerprintException) {
//                runOnUiThread {
//                    LogHelper.d(
//                        "captureError  errno=" + e.errorCode +
//                                ",Internal error code: " + e.internalErrorCode + ",message=" + e.message
//                    )
//                }
//            }
//
//            override fun extractError(err: Int) {
//                runOnUiThread { textView.setText("extract fail, errorcode:$err") }
//            }
//
//            override fun extractOK(fpTemplate: ByteArray) {
//                runOnUiThread(Runnable {
//                    if (isRegister) {
//                        val bufids = ByteArray(256)
//                        var ret = ZKFingerService.identify(fpTemplate, bufids, 55, 1)
//                        if (ret > 0) {
//                            val strRes = String(bufids).split("\t").toTypedArray()
//                            textView.setText("the finger already enroll by " + strRes[0] + ",cancel enroll")
//                            isRegister = false
//                            enrollidx = 0
//                            return@Runnable
//                        }
//                        if (enrollidx > 0 && ZKFingerService.verify(
//                                regtemparray[enrollidx - 1],
//                                fpTemplate
//                            ) <= 0
//                        ) {
//                            textView.setText("please press the same finger 3 times for the enrollment")
//                            return@Runnable
//                        }
//                        System.arraycopy(
//                            fpTemplate, 0,
//                            regtemparray[enrollidx], 0, 2048
//                        )
//                        enrollidx++
//                        if (enrollidx == 3) {
//                            val regTemp = ByteArray(2048)
//                            if (0 < ZKFingerService.merge(regtemparray[0], regtemparray[1], regtemparray[2], regTemp).also {
//                                    ret = it
//                                }
//                            ) {
//                                ZKFingerService.save(regTemp, "test" + uid++)
//                                System.arraycopy(regTemp, 0, lastRegTemp, 0, ret)
//                                //Base64 Template
//                                val strBase64 =
//                                    Base64.encodeToString(regTemp, 0, ret, Base64.NO_WRAP)
//                                textView.setText("enroll succ, uid:" + uid + "count:" + ZKFingerService.count())
//                            } else {
//                                textView.setText("enroll fail")
//                            }
//                            isRegister = false
//                        } else {
//                            textView.setText("You need to press the " + (3 - enrollidx) + "time fingerprint")
//                        }
//                    } else {
//                        val bufids = ByteArray(256)
//                        val ret = ZKFingerService.identify(fpTemplate, bufids, 55, 1)
//                        if (ret > 0) {
//                            val strRes = String(bufids).split("\t").toTypedArray()
//                            textView.setText("identify succ, userid:" + strRes[0] + ", score:" + strRes[1])
//                        } else {
//                            textView.setText("identify fail")
//                        }
//                        //Base64 Template
//                        //String strBase64 = Base64.encodeToString(tmpBuffer, 0, fingerprintSensor.getLastTempLen(), Base64.NO_WRAP);
//                    }
//                })
//            }
//        }
//        fingerprintSensor!!.setFingerprintCaptureListener(0, listener)
//        fingerprintSensor!!.startCapture(0)
//        bstart = true
//        textView.setText("start capture succ")
//    } catch (e: FingerprintException) {
//        textView.setText("begin capture fail.errorcode:" + e.errorCode + "err message:" + e.message + "inner code:" + e.internalErrorCode)
//    }
//}