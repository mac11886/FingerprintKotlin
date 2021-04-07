package com.example.kotlinv10.model

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinv10.R


class BranchAdapter(context: Context, admins: List<DataAdmin>):
    RecyclerView.Adapter<BranchAdapter.ViewHolder>() {

    var context = context
    var admins = admins
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var branchText: TextView = itemView.findViewById(R.id.branchName)
        var adminText: TextView = itemView.findViewById(R.id.adminName)
        var cardView: CardView = itemView.findViewById(R.id.card1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflate = LayoutInflater.from(context)
        return ViewHolder(inflate.inflate(R.layout.branch_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.adminText.text = admins[position].name
        holder.branchText.text = admins[position].branch.name
        holder.cardView.setOnClickListener{
            AlertDialog.inputDialog(context as Activity, true, admins[position], )
        }
    }

    override fun getItemCount(): Int {
        return admins.size
    }

}