package com.example.kotlinv10.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.kotlinv10.R

class SecondManageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_manage)

        var button: Button = findViewById(R.id.button6)
        button.setOnClickListener {
            Intent(this, FirstManageActivity::class.java).also{
                startActivity(it)
            }
        }
    }
}