package com.example.kotlinv10.model

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinv10.R
import com.example.kotlinv10.SecondManageActivity
import com.example.kotlinv10.ThirdManageActivity

class SectorAdapter(
    sectors: List<String>,
    numJobs: List<Int>,
    context: Context,
    isFirst: Boolean,
    images: HashMap<Int, List<Bitmap>>
) :
    RecyclerView.Adapter<SectorAdapter.ViewHolder>() {

    var sectors = sectors
    var numJobs = numJobs
    var context = context
    var isFirst = isFirst
    var images = images

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameText: TextView = itemView.findViewById(R.id.sectorName)
        var numJobText: TextView = itemView.findViewById(R.id.numJob)
        var cardView: CardView = itemView.findViewById(R.id.card1)
        var imageViews = listOf<ImageView>(
            itemView.findViewById(R.id.imageView),
            itemView.findViewById(R.id.imageView2),
            itemView.findViewById(R.id.imageView3),
            itemView.findViewById(R.id.imageView4),
            itemView.findViewById(R.id.imageView5)
        )
        var numpeople: TextView = itemView.findViewById(R.id.textView3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater = LayoutInflater.from(context)
        return ViewHolder(inflater.inflate(R.layout.sector_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameText.text = sectors[position]
        if (this.isFirst) {
            holder.numJobText.text = numJobs[position].toString() + " ตำแหน่ง"
            holder.cardView.setOnClickListener {
                Intent(
                    context,
                    SecondManageActivity::class.java
                ).also { intent -> context.startActivity(intent) }
            }
        } else {
            holder.numJobText.text = numJobs[position].toString() + " คน"
            if (images[position]!!.size > 4) {
                holder.imageViews[4].setImageResource(R.drawable.circle_people)
                var count = images[position]!!.size - 4
                holder.numpeople.text = "+$count"
            }
            for (i in images[position]!!.indices){
                if (i > 3){
                    break
                }
                holder.imageViews[i].setImageResource(R.drawable.ic_profile)
            }
            holder.cardView.setOnClickListener{
                Intent(context, ThirdManageActivity::class.java).also {
                    intent -> context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return this.sectors.size;
    }
}