package com.example.mydicodingevent.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mydicodingevent.R
import com.example.mydicodingevent.model.EventItem

class EventAdapter(
    private val events: List<EventItem>,
    private val onItemClick: (EventItem) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    class EventViewHolder(
        itemView: View,
        private val onItemClick: (EventItem) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvEventName: TextView = itemView.findViewById(R.id.tv_event_name)
        private val imgEventCover: ImageView = itemView.findViewById(R.id.img_event_cover)

        fun bind(event: EventItem) {
            tvEventName.text = event.name

            Glide.with(itemView.context)
                .load(event.mediaCover)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_broken_image)
                .override(200, 200)  // Ensure a set size
                .centerCrop()
                .into(imgEventCover)

            // Set click listener for each item
            itemView.setOnClickListener {
                onItemClick(event)
            }
        }
    }
}
