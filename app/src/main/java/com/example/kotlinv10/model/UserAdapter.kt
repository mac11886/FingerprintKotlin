package com.example.kotlinv10.model

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinv10.Controller.EditProfileActivity
import com.example.kotlinv10.R

class UserAdapter(context: Context, nameUsers: List<DataUser>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    var context = context
//    var imageUsers = imageUsers
    var nameUsers = nameUsers
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView = itemView.findViewById(R.id.name_user)
        var imageUserView: ImageView = itemView.findViewById(R.id.image_user)
        var cardView: CardView = itemView.findViewById(R.id.cardProfile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflate = LayoutInflater.from(context)
        return ViewHolder(inflate.inflate(R.layout.user_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageUserView.setImageResource(R.drawable.ic_profile)
        holder.nameTextView.text = nameUsers[position].name
        holder.cardView.setOnClickListener {
            Intent(context,EditProfileActivity::class.java).also { intent ->
                intent.putExtra("username",nameUsers[position].name)
                intent.putExtra("firstFinger",nameUsers[position].fingerprint.first_fingerprint)
                intent.putExtra("secondFinger",nameUsers[position].fingerprint.second_fingerprint)
                context.startActivity(intent)}
        }
    }

    override fun getItemCount(): Int {
        return this.nameUsers.size
    }
}