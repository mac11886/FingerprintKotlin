package com.example.kotlinv10.Controller

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinv10.R
import com.example.kotlinv10.model.DataHolder
import com.example.kotlinv10.model.DataUser
import com.example.kotlinv10.model.UserAdapter

class ThirdManageActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var addPersonBtn :Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_manage)

        recyclerView = findViewById(R.id.user_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        var data =DataHolder.allDataUser

        var imageList = listOf<Bitmap>()
        var names = listOf("Mac", "Mac", "Mac", "Mac")

        loadAdapter(data!!)


        addPersonBtn = findViewById(R.id.addPerson)
        addPersonBtn.setOnClickListener {
            Intent(this,EditProfileActivity::class.java).also { intent ->  startActivity(intent)}
        }


    }


    fun loadAdapter(list: List<DataUser>){
        val adapter= UserAdapter(this, list)
        recyclerView.adapter = adapter


    }
}