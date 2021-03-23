package com.example.kotlinv10

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinv10.model.SectorAdapter

class SecondManageActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_manage)

        recyclerView = findViewById(R.id.sectorList)
        textView = findViewById(R.id.company1)
        recyclerView.layoutManager = LinearLayoutManager(this)
        var linearLayout = findViewById<LinearLayout>(R.id.linear1)

        var sectors = arrayListOf<String>(
            "Developer",
            "Developer",
            "Developer",
            "Developer",
            "Developer",
            "Developer",
            "Developer"
        )
        var numJob = arrayListOf(4, 5, 6, 7, 2, 4, 9)

        var images: HashMap<Int, List<Bitmap>> = HashMap()

        val icon = BitmapFactory.decodeResource(
            resources,
            R.drawable.ic_attendance
        )

        for (a in 0..9) {
            var list = listOf<Bitmap>()
            if (a%2 == 1){
                for (b in 1..3){
                    list += icon
                }
            } else {
                for (b in 1..6){
                    list += icon
                }
            }
            images[a] = list
//            Log.e("test", list.size.toString())
        }
//        for(a in images.keys){
//            println(images[a]?.size)
//        }


        var sectorAdapter = SectorAdapter(sectors, numJob, this, false, images)
        recyclerView.adapter = sectorAdapter
    }
}