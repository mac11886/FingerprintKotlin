package com.example.kotlinv10.Controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinv10.R

class SignUpActivity : AppCompatActivity() {
    lateinit var companyText: EditText
    lateinit var nameText: EditText
    lateinit var emailText: EditText
    lateinit var passwordText: EditText
    lateinit var secondPasswordText: EditText
    lateinit var signUp: Button
    lateinit var loginText: TextView

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
        emailText = findViewById(R.id.inputEmailSignUp)
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
        var email: String = emailText.text.toString()
        var password: String = passwordText.text.toString()
        var rePassword: String = secondPasswordText.text.toString()

        var intent = Intent(this, MainActivity::class.java)
        var bundle: Bundle = Bundle()
        bundle.putStringArray("signUp", arrayOf(company, name, email, password, rePassword))
        intent.putExtras(bundle)
        startActivity(intent)
    }


    fun validate(): Boolean {
        var valid: Boolean = true

        var company: String = companyText.text.toString()
        var name: String = nameText.text.toString()
        var email: String = emailText.text.toString()
        var password: String = passwordText.text.toString()
        var rePassword: String = secondPasswordText.text.toString()


        if (company.isNullOrEmpty() || company.length < 3) {
            companyText.error = "ใส่ชื่อบริษัทอย่างน้อย 3 ตัว "
            valid = false
        } else {
            companyText.error = null
        }
        if (name.isEmpty() || name.length < 3) {
            nameText.error = "ใส่อย่างน้อย 3 ตัวอักษร"
            valid = false
        } else {
            nameText.error = null
        }

        if (email.isNullOrEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        ) {
            emailText.error = "กรอก E-mail ไม่ถูกต้อง"
            valid = false
        } else {
            emailText.error = null
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