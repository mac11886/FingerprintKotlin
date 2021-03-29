package com.example.kotlinv10.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.kotlinv10.R
import com.example.kotlinv10.model.ApiObject
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

        usernameText = findViewById(R.id.inputEmail)
        passwordText = findViewById(R.id.inputPassword)
        loginBtn = findViewById(R.id.loginBtn)
        signupText = findViewById(R.id.signupText)

        signupText.setOnClickListener {

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            if (validate()) {
                val username = usernameText.text.toString()
                val password = passwordText.text.toString()
                ApiObject.apiObject.login(username, password).enqueue(object : Callback<String>{
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful){
                            Toast.makeText(applicationContext, "success login", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })

            }
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
}