package com.example.kotlinv10.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinv10.R

class SectorAdapter(sectors: List<String>, numJobs: List<Int>, context: Context, isFirst: Boolean) : RecyclerView.Adapter<SectorAdapter.ViewHolder>() {

    var sectors = sectors
    var numJobs = numJobs
    var context = context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameText: TextView = itemView.findViewById(R.id.sectorName)
        var numJobText: TextView = itemView.findViewById(R.id.numJob)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater = LayoutInflater.from(context)
        return ViewHolder(inflater.inflate(R.layout.sector_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameText.text = sectors[position];
        holder.numJobText.text = numJobs[position].toString() + " ตำแหน่ง"
    }

    override fun getItemCount(): Int {
        return this.sectors.size;
    }
}