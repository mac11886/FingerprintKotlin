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
import com.example.kotlinv10.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirstManageActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var button : Button
    lateinit var companyButton : Button
    lateinit var companyName : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_manage)

        recyclerView = findViewById(R.id.sectorList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        companyName = findViewById(R.id.companyName1)
//        val call = ApiObject.apiObject.getAllData(1)
//
//        call.enqueue(object :Callback<AllData>{
//            override fun onResponse(call: Call<AllData>, response: Response<AllData>) {
//                DataHolder.allData = response.body()
//
//
//            }
//
//            override fun onFailure(call: Call<AllData>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        })

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