package com.example.kotlinv10.Controller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinv10.R
import com.example.kotlinv10.model.ApiObject
import com.example.kotlinv10.model.AppPreferences
import com.example.kotlinv10.model.Branch
import com.example.kotlinv10.model.DataAdmin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginAppActivity : AppCompatActivity() {


    lateinit var usernameText: EditText
    lateinit var passwordText: EditText
    lateinit var loginBtn: Button
    lateinit var signupText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_app)
        AppPreferences.init(applicationContext)

        usernameText = findViewById(R.id.inputEmail)
        passwordText = findViewById(R.id.inputPassword)
        loginBtn = findViewById(R.id.loginBtn)
        signupText = findViewById(R.id.signupText)


        usernameText.setText("mac")
        passwordText.setText("123")


        signupText.setOnClickListener {

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            if (validate()) {
                val username = usernameText.text.toString()
                val password = passwordText.text.toString()
                ApiObject.apiObject.login(username, password).enqueue(object : Callback<DataAdmin> {
                    override fun onResponse(call: Call<DataAdmin>, response: Response<DataAdmin>) {
                        if (response.isSuccessful) {
                            val dataAdmin: DataAdmin? = response.body()
                            AppPreferences.branch_id = dataAdmin?.branch_id.toString()
                            AppPreferences.company_id = dataAdmin?.company_id.toString()

                            when (dataAdmin?.branch_id) {
                                0 -> {
                                    Intent(
                                        applicationContext,
                                        FirstManageActivity::class.java
                                    ).also {
                                        startActivity(it)
                                    }
                                    try {
                                        hideSoftKeyboard(this@LoginAppActivity)
                                    }catch (e:Exception){
                                        Log.e("Hide keyboard","error")
                                    }
                                }
                                else -> {
                                    Intent(
                                        applicationContext,
                                        MainActivity::class.java
                                    ).also {
                                        startActivity(it)
                                    }
                                    try {
                                        hideSoftKeyboard(this@LoginAppActivity)
                                    }catch (e:Exception){
                                        Log.e("Hide keyboard","error")
                                    }  }
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "username or password is wrong!",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                    }

                    override fun onFailure(call: Call<DataAdmin>, t: Throwable) {
//                        Log.e("error", t.message.toString())
                        Toast.makeText(applicationContext,"username or password is wrong!", Toast.LENGTH_SHORT).show()
                    }

                })

            }

//            if (usernameText.text.toString() == "super"){
//                Intent(this, FirstManageActivity::class.java).also { intent ->
//                    startActivity(intent)
//    //                finish()
//                }
//
//            } else {
//                Intent(this, MainActivity::class.java).also {
//                    startActivity(it)
//                }
//            }
        }

    }

    fun validate(): Boolean {
        var error = true
        if (usernameText.text.toString().isNullOrEmpty()) {
            usernameText.error = "please check your username"
            error = false
        }
        if (passwordText.text.toString().isNullOrEmpty()) {
            passwordText.error = "please check your password"
            error = false
        }
        return error
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }
}