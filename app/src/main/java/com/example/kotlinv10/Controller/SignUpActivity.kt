package com.example.kotlinv10.Controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinv10.R
import com.example.kotlinv10.Service.ApiService
import com.example.kotlinv10.model.ApiObject
import com.example.kotlinv10.model.DataHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity() {
    lateinit var companyText: EditText
    lateinit var nameText: EditText
    lateinit var userNameText: EditText
    lateinit var passwordText: EditText
    lateinit var secondPasswordText: EditText
    lateinit var signUp: Button
    lateinit var loginText: TextView
    val apiService = Retrofit.Builder()
        .baseUrl("https://ksta.co/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initUi()
        login()


        signUp.setOnClickListener {
            signUp()
        }

    }

    fun initUi() {
        companyText = findViewById(R.id.inputCompanySignUp)
        nameText = findViewById(R.id.inputNameSignUp)
        userNameText = findViewById(R.id.inputEmailSignUp)
        passwordText = findViewById(R.id.inputPasswordSignUp)
        secondPasswordText = findViewById(R.id.inputRePasswordSignUp)
        signUp = findViewById(R.id.signUpBtn)
        loginText = findViewById(R.id.loginText)

    }

    fun login() {
        loginText.setOnClickListener {
            val intent = Intent(this, LoginAppActivity::class.java)
            startActivity(intent)
        }
    }

    fun signUp() {
        if (!validate()) {
            return
        }
        var company: String = companyText.text.toString()
        var name: String = nameText.text.toString()
        var email: String = userNameText.text.toString()
        var password: String = passwordText.text.toString()

        val call = ApiObject.apiObject.signUp(name, email, password, company)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("Succuess", response.body().toString())
                Toast.makeText(this@SignUpActivity,"Signup success",Toast.LENGTH_SHORT).show()
                Intent(this@SignUpActivity,LoginAppActivity::class.java).also { intent ->
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("Fail", "fail")
            }

        })



//        var intent = Intent(this, MainActivity::class.java)

//        var bundle: Bundle = Bundle()
//        bundle.putStringArray("signUp", arrayOf(company, name, email, password, rePassword))
//        intent.putExtras(bundle)
//        startActivity(intent)
    }


    fun validate(): Boolean {
        var valid: Boolean = true

        var company: String = companyText.text.toString()
        var name: String = nameText.text.toString()
        var userName : String = userNameText.text.toString()
        var password: String = passwordText.text.toString()
        var rePassword: String = secondPasswordText.text.toString()

//        var dataUsernameValid  = DataHolder.allData!!.dataAdmin[0].username

        if (company.isNullOrEmpty() || company.length < 3) {
            companyText.error = "Enter at least 3 characters."
            valid = false
        } else {
            companyText.error = null
        }
        if (name.isEmpty() || name.length < 3) {
            nameText.error = "Enter at least 3 characters."
            valid = false
        } else {
            nameText.error = null
        }

        if (userName.isNullOrEmpty() ) {
            userNameText.error = "input your username"
            valid = false
        }
//        else if(userName == dataUsernameValid){
//            userNameText.error = "Username already exists, please enter a new one."
//            valid = false
//        }
        else {
            userNameText.error = null
        }

        if (password.isNullOrEmpty() || password.length < 4 || password.length > 10) {
            passwordText.error = "ใส่รหัส 4-10 ตัว"
            valid = false
        } else {
            passwordText.error = null
        }
        if (rePassword.isNullOrEmpty() || rePassword.length < 4 || rePassword.length > 10 || rePassword != password) {
            secondPasswordText.error = " รหัส Password ไม่ตรงกัน"
            valid = false
        } else {
            secondPasswordText.error = null
        }

        return valid

    }


}