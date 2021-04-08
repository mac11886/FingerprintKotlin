package com.example.kotlinv10.model

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.kotlinv10.Controller.EditProfileActivity
import com.example.kotlinv10.Controller.FirstManageActivity
import com.example.kotlinv10.Controller.ThirdManageActivity
import com.example.kotlinv10.R
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AlertDialog {
    lateinit var dialog: Dialog
     var fingerprint1 : String = ""
     var fingerprint2 : String = ""
    var getName : String = ""



    fun loadingDialog(activity: Activity) {
        val builder = AlertDialog.Builder(activity)
        val inflater: LayoutInflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.progress_dialog_view, null))
        builder.setCancelable(false)

        dialog = builder.create()
        dialog.show()
    }

    fun confirmDialog(activity: Activity,context: Context,strBase64: String,whichFinger : String,name : String){
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater.inflate(R.layout.confirm_dialog, null)
        builder.setView(inflater)
        builder.setCancelable(true)
        dialog = builder.create()

        var button = inflater.findViewById<Button>(R.id.confirmBtn)
        dialog = builder.create()
        dialog.show()
        button.setOnClickListener {
            if (whichFinger == "First Finger") {
                Intent(context, EditProfileActivity::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.putExtra("base64FirstFinger", strBase64)
                    fingerprint1 = strBase64
                    getName = name
                    context.startActivity(intent)
                    activity.finish()
                }
            } else {
                Intent(context, EditProfileActivity::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.putExtra("base64SecondFinger", strBase64)
                    fingerprint2 = strBase64
                    context.startActivity(intent)
                    activity.finish()
                }
            }


        }

    }

    fun confirmEditDialog(activity: Activity,context: Context) {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater.inflate(R.layout.confirm_dialog, null)
        builder.setView(inflater)
        builder.setCancelable(true)
        dialog = builder.create()
        Intent(context, ThirdManageActivity::class.java).also { intent ->
            context.startActivity(intent)
            activity.finish()
        }

        dialog.show()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun editCompanyDialog(activity: Activity, companyName: String) {


        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater.inflate(R.layout.input_company_dialog, null)

        builder.setView(inflater)
        builder.setCancelable(true)
        dialog = builder.create()

        var button = inflater.findViewById<Button>(R.id.buttonCompany)

        var timePicker: TimePicker = inflater.findViewById(R.id.timePicker)
        timePicker.setIs24HourView(true)

//        var nameCompany = inflater.findViewById<TextView>(R.id.companyName1)

        button.setOnClickListener {
            if (AppPreferences.company_id != "") {
                val time: String = timePicker.hour.toString() + ":" + timePicker.minute.toString()
//                Log.e("test", time)
                ApiObject.apiObject.setLateTime(AppPreferences.company_id!!.toInt(), time)
                    .enqueue(object : Callback<String>{
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if (response.isSuccessful){
                                dialog.dismiss()
                                Toast.makeText(activity, "late time is updated", Toast.LENGTH_SHORT).show()

                            } else {
                                Log.e("debug", response.message())
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                        }

                    })
            }

        }
        dialog.show()
    }

    fun inputDialog(activity: Activity, isEdit: Boolean, admin: DataAdmin?) {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater.inflate(R.layout.input_dialog, null)

        builder.setView(inflater)
        builder.setCancelable(true)

        dialog = builder.create()
        var button = inflater.findViewById<Button>(R.id.button3)
        var branchEdit = inflater.findViewById<EditText>(R.id.branchName)
        var nameEdit = inflater.findViewById<EditText>(R.id.adminName)
        var usernameEdit = inflater.findViewById<EditText>(R.id.adminUsername)
        var passwordEdit = inflater.findViewById<EditText>(R.id.password1)
        var rePasswordEdit = inflater.findViewById<EditText>(R.id.password2)
        var titleText = inflater.findViewById<TextView>(R.id.textView13)
        if (isEdit) {
            titleText.text = "Edit Branch"
            nameEdit.setText(admin?.name)
            usernameEdit.setText(admin?.username)
            branchEdit.setText(admin?.branch?.name)
            passwordEdit.setText(admin?.password)

            usernameEdit.isEnabled = false

        }

        button.setOnClickListener {
            var valid = true
            if (branchEdit.text.toString().isNullOrEmpty()) {
                branchEdit.error = "Please enter branch name"
                valid = false
            }
            if (nameEdit.text.toString().isNullOrEmpty()) {
                nameEdit.error = "Please enter admin name"
                valid = false
            }
            if (usernameEdit.text.toString().isNullOrEmpty()) {
                usernameEdit.error = "Please enter admin username"
                valid = false
            }
            if (passwordEdit.text.toString().isNullOrEmpty()) {
                passwordEdit.error = "Please enter admin password"
                valid = false
            }
            if (!isEdit) {
                if (rePasswordEdit.text.toString().isNullOrEmpty()) {
                    rePasswordEdit.error = "Please enter re-password"
                    valid = false
                }

                if (rePasswordEdit.text.toString() != passwordEdit.text.toString()) {
                    rePasswordEdit.error = "Please enter same password"
                    valid = false
                }
            }

            if (valid) {
                if (isEdit) {
//                    Log.e("test",  branchEdit.text.toString() + " " +  nameEdit.text.toString() + " " + passwordEdit.text.toString() + " " + admin?.branch_id.toString())
                    ApiObject.apiObject.editBranch(
                        branchEdit.text.toString(),
                        nameEdit.text.toString(),
                        passwordEdit.text.toString(),
                        admin?.branch_id,
                        admin?.id
                    ).enqueue(object : Callback<String> {
                        override fun onResponse(
                            call: Call<String>,
                            response: Response<String>
                        ) {
                            if (response.isSuccessful) {
                                dialog.dismiss()
                                activity.finish()
//                                activity.recreate()
                                activity.overridePendingTransition(0, 0)
                                activity.startActivity(activity.intent)
                                activity.overridePendingTransition(0, 0)
                            }
//                            Toast.makeText(activity.applicationContext, response.message().toString(), Toast.LENGTH_SHORT).show()
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Toast.makeText(
                                activity.applicationContext,
                                "fail1",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
                } else {
                    if (AppPreferences.branch_id != "") {
                        ApiObject.apiObject.saveBranch(
                            AppPreferences.company_id?.toInt(),
                            branchEdit.text.toString(),
                            nameEdit.text.toString(),
                            usernameEdit.text.toString(),
                            passwordEdit.text.toString()
                        ).enqueue(object : Callback<String> {
                            override fun onResponse(
                                call: Call<String>,
                                response: Response<String>
                            ) {
                                if (response.isSuccessful) {
                                    dialog.dismiss()
                                    activity.finish()
                                    activity.overridePendingTransition(0, 0)
                                    activity.startActivity(activity.intent)
                                    activity.overridePendingTransition(0, 0)
                                } else {
                                    Toast.makeText(
                                        activity.applicationContext,
                                        "fail1",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            override fun onFailure(call: Call<String>, t: Throwable) {
                                Toast.makeText(
                                    activity.applicationContext,
                                    "fail2",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        })

                    }
                }

            }
        }


        var textView = inflater.findViewById<TextView>(R.id.textView)
        textView.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    fun dismissDialog() {
        dialog.dismiss()
    }
}