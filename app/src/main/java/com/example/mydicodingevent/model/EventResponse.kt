package com.example.mydicodingevent.model

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("listEvents")
    val listEvents: List<EventItem>
)