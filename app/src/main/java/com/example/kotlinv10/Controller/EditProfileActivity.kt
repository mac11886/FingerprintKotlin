package com.example.kotlinv10.Controller

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.icu.text.SimpleDateFormat
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.kotlinv10.R
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*


class EditProfileActivity : AppCompatActivity() {


    lateinit var imageProfile: ImageView
    lateinit var nameText: EditText

    lateinit var imageBtn: Button
    lateinit var firstFinger: ImageView
    lateinit var secondFinger: ImageView
    lateinit var submitBtn: Button


    lateinit var imageURI: Uri

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        initUi()

        imageProfile.setOnClickListener {
            selectImage(this)
        }
        firstFinger.setOnClickListener{
            Intent(this, FingerprintActivity::class.java).also { nextIntent -> startActivity(
                nextIntent
            )
            }}

        secondFinger.setOnClickListener{
            Intent(this, FingerprintActivity::class.java).also { nextIntent -> startActivity(
                nextIntent
            )
            }}

    }

    private fun initUi() {
        imageProfile = findViewById(R.id.imageProfile)
        imageBtn = findViewById(R.id.selectImage)
        firstFinger = findViewById(R.id.firstFinger)
        secondFinger = findViewById(R.id.secondFinger)

        firstFinger.setImageResource(R.drawable.ic__one_finger)
        secondFinger.setImageResource(R.drawable.ic__two_finger)
        imageProfile.setImageResource(R.drawable.ic_copywriter)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun takeImage() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        createImageFile()
                    } else {
                        TODO("VERSION.SDK_INT < N")
                    }
                } catch (ex: IOException) {
                    // Error occurred while creating the File

                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.kotlinv10",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    takePictureIntent.putExtra(
                        MediaStore.EXTRA_SCREEN_ORIENTATION,
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    )
                    startActivityForResult(takePictureIntent, 0)
                }
            }
        }

    }

    var currentPhotoPath: String = ""

    @RequiresApi(Build.VERSION_CODES.N)
    @Throws(IOException::class)
    fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    fun pickGallery() {
        Intent(Intent.ACTION_PICK).also { galleryIntent ->
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, 1)
        }
    }

    fun setImageFromGallery(data: Intent) {
        try {
            var uri = data.data
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            imageProfile.setImageBitmap(bitmap)

        } catch (e: FileNotFoundException) {

        }
    }

    fun addToGallery() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
        }
    }

    private fun setPic() {
        // Get the dimensions of the View
        val targetW: Int = imageProfile.width
        val targetH: Int = imageProfile.height

        // Get the dimensions of the bitmap
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        // Determine how much to scale down the image
//        val scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH))

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false
//        bmOptions.inSampleSize = scaleFactor
        bmOptions.inPurgeable = true
        val bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions)

        imageProfile.setImageBitmap(bitmap)
        //---------------------------------------------------------------------------------------------------
        Log.e("messsage","current"+ currentPhotoPath)
//        val ei = ExifInterface(currentPhotoPath)
//        val orientation = ei.getAttributeInt(
//            ExifInterface.TAG_ORIENTATION,
//            ExifInterface.ORIENTATION_UNDEFINED
//        )
//        Log.e("check", "orientation$orientation")
//        var rotatedBitmapTest: Bitmap?
//        when(orientation){
//            ExifInterface.ORIENTATION_ROTATE_90 -> {
//                rotatedBitmapTest = rotateImage(bitmap, 90F)
//                Log.e("check", "bitmap$rotatedBitmapTest")
//                imageProfile.setImageBitmap(rotatedBitmapTest)
//            }
//            ExifInterface.ORIENTATION_ROTATE_180 -> {
//                rotatedBitmapTest = rotateImage(bitmap, 180F)
//                Log.e("check", "bitmap$rotatedBitmapTest")
//                imageProfile.setImageBitmap(rotatedBitmapTest)
//            }
//            ExifInterface.ORIENTATION_ROTATE_270 -> {
//                rotatedBitmapTest = rotateImage(bitmap, 270F)
//                Log.e("check", "bitmap$rotatedBitmapTest")
//                imageProfile.setImageBitmap(rotatedBitmapTest)
//            }
//
//            ExifInterface.ORIENTATION_NORMAL -> {
//                rotatedBitmapTest = bitmap
//                Log.e("check", "bitmap$rotatedBitmapTest")
//                imageProfile.setImageBitmap(rotatedBitmapTest)
//            }
//        }

    }
    fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun selectImage(context: Context) {
        val options = arrayOf<CharSequence>("ถ่ายรูป", "เลือกจาก Gallery", "ยกเลิก")
        var builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("เลือกรูปภาพ")
        builder.setItems(options, DialogInterface.OnClickListener { dialog, which ->
            if (options[which] == "ถ่ายรูป") {
                takeImage()
            } else if (options[which] == "เลือกจาก Gallery") {
                pickGallery()
            } else if (options[which] == "ยกเลิก") {
                dialog.dismiss()
            }
        })

        builder.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_CANCELED) {
            when (requestCode) {
                0 -> if (resultCode == RESULT_OK) {
                    Log.e("check ", "0")
                    addToGallery()
                    try {
                        Log.e("check ", "image take show")
                        setPic()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                1 -> if (resultCode == RESULT_OK) {

                    try {
                        if (data != null) {
                            setImageFromGallery(data)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

}
