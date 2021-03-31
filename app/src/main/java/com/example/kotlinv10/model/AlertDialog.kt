package com.example.kotlinv10.model

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.kotlinv10.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AlertDialog {
    lateinit var dialog: Dialog

    fun loadingDialog(activity: Activity) {
        val builder = AlertDialog.Builder(activity)
        val inflater: LayoutInflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.progress_dialog_view, null))
        builder.setCancelable(false)

        dialog = builder.create()
        dialog.show()
    }

    fun inputDialog(activity: Activity, isEdit: Boolean) {
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
            branchEdit.setText("hello")
            usernameEdit.setText("chai4")
            usernameEdit.isEnabled = false
        } else {

            button.setOnClickListener {
                if (!isEdit) {
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
                    if (rePasswordEdit.text.toString().isNullOrEmpty()) {
                        rePasswordEdit.error = "Please enter re-password"
                        valid = false
                    }
                    if (rePasswordEdit.text.toString() != passwordEdit.text.toString()) {
                        rePasswordEdit.error = "Please enter same password"
                        valid = false
                    }

                    if (valid) {
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
                                        Toast.makeText(activity.applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(activity.applicationContext, "fail1", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Toast.makeText(activity.applicationContext, "fail2", Toast.LENGTH_SHORT).show()
                                }

                            })

                        }

                    }
                }

//            Toast.makeText(applicationContext, "update data", Toast.LENGTH_SHORT).show()
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