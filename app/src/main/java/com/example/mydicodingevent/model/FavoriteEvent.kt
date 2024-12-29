package com.example.mydicodingevent.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_events")
data class FavoriteEvent(
    @PrimaryKey val id: String,
    val name: String,
    val description: String?,
    val mediaCover: String?
)