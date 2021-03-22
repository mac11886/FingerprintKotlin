package com.example.kotlinv10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinv10.model.SectorAdapter

class SecondManageActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_manage)

        recyclerView = findViewById(R.id.sectorList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var sectors = arrayListOf<String>("Developer", "Developer", "Developer", "Developer", "Developer", "Developer", "Developer")
        var numJob = arrayListOf<Int>(4, 5, 6, 7, 2, 4, 9)

        var sectorAdapter = SectorAdapter(sectors, numJob, this, false)
        recyclerView.adapter = sectorAdapter
    }
}