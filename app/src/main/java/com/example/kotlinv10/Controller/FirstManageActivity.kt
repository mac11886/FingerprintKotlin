package com.example.kotlinv10.Controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinv10.R
import com.example.kotlinv10.model.AlertDialog
import com.example.kotlinv10.model.BranchAdapter

class FirstManageActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var button : Button
    lateinit var companyButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_manage)

        recyclerView = findViewById(R.id.sectorList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = BranchAdapter(this, listOf("hello", "world"))
        recyclerView.adapter = adapter
        button = findViewById(R.id.button2)
        companyButton = findViewById(R.id.companyButton)
        companyButton.setOnClickListener {
            AlertDialog.editCompanyDialog(this,false)
        }
        button.setOnClickListener {
            AlertDialog.inputDialog(this, false)
        }

    }
}