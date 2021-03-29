package com.example.kotlinv10.Controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinv10.R
import com.example.kotlinv10.model.SectorAdapter

class FirstManageActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_manage)

        recyclerView = findViewById(R.id.sectorList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var sectors = arrayListOf<String>("Development", "Development", "Development", "Development", "Development", "Development", "Development")
        var numJob = arrayListOf<Int>(4, 5, 6, 7, 2, 4, 9)

        var sectorAdapter = SectorAdapter(sectors, numJob, this, true, hashMapOf())
        recyclerView.adapter = sectorAdapter


    }
}