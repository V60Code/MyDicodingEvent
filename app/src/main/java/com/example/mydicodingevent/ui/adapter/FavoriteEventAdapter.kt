package com.example.mydicodingevent.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mydicodingevent.R
import com.example.mydicodingevent.model.FavoriteEvent

class FavoriteEventAdapter(
    private val favorites: List<FavoriteEvent>,
    private val onItemClick: (FavoriteEvent) -> Unit
) : RecyclerView.Adapter<FavoriteEventAdapter.FavoriteEventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteEventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return FavoriteEventViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteEventViewHolder, position: Int) {
        val favoriteEvent = favorites[position]
        holder.tvEventName.text = favoriteEvent.name
        Glide.with(holder.itemView.context)
            .load(favoriteEvent.mediaCover)
            .centerCrop()
            .placeholder(R.drawable.ic_placeholder)  // Optional placeholder
            .error(R.drawable.ic_broken_image)      // Optional error image
            .into(holder.imgEventCover)

        holder.itemView.setOnClickListener {
            onItemClick(favoriteEvent)
        }
    }

    override fun getItemCount(): Int = favorites.size

    class FavoriteEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEventName: TextView = itemView.findViewById(R.id.tv_event_name)
        val imgEventCover: ImageView = itemView.findViewById(R.id.img_event_cover)
    }
}
