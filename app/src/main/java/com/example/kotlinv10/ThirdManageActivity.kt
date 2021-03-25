package com.example.kotlinv10

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinv10.model.UserAdapter

class ThirdManageActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_manage)

        recyclerView = findViewById(R.id.user_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var imageList = listOf<Bitmap>()
        var names = listOf("Mac", "Mac", "Mac", "Mac", "Mac", "Mac", "Mac", "Mac", "Mac", "Mac", "Mac", "Mac")
        var adapter = UserAdapter(this, imageList, names)

        recyclerView.adapter = adapter
    }
}