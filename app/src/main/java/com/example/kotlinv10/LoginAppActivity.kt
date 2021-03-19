package com.example.kotlinv10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LoginAppActivity : AppCompatActivity() {


    lateinit var emaitText  :EditText
    lateinit var passwordText : EditText
    lateinit var loginBtn : Button
    lateinit var signupText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_app)

        emaitText = findViewById(R.id.inputEmail)
        passwordText = findViewById(R.id.inputPassword)
        loginBtn = findViewById(R.id.loginBtn)
        signupText = findViewById(R.id.signupText)

        signupText.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}