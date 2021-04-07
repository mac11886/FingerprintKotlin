package com.example.kotlinv10.Controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    lateinit var editbutton : Button
    lateinit var companyButton : Button
    lateinit var companyName : TextView

//    override fun onResume() {
//        super.onResume()
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_manage)

        recyclerView = findViewById(R.id.sectorList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        companyName = findViewById(R.id.companyName1)
        AlertDialog.loadingDialog(this)
//        val call = ApiObject.apiObject.getAllData(1)

        ApiObject.apiObject.getAllData(AppPreferences.company_id!!.toInt()).enqueue(object :
            Callback<AllData> {
            override fun onResponse(call: Call<AllData>, response: Response<AllData>) {
                if (response.isSuccessful) {

//                    Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                    var companyNameList = listOf<String>()

                    val adminList = response.body()!!.dataAdmin

                    loadAdapter(adminList)
                    AlertDialog.dismissDialog()
                }
            }

            override fun onFailure(call: Call<AllData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })



    }
    fun loadAdapter(list: List<DataAdmin>){
        val adapter = BranchAdapter(this, list)
        recyclerView.adapter = adapter
        editbutton = findViewById(R.id.button2)
        companyButton = findViewById(R.id.companyButton)
        companyButton.setOnClickListener {
            AlertDialog.editCompanyDialog(this, false)
        }
        editbutton.setOnClickListener {
            AlertDialog.inputDialog(this, false, null)
        }
    }
}