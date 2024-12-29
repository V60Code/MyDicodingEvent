package com.example.mydicodingevent.model

import com.google.gson.annotations.SerializedName

data class EventItem(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("mediaCover") val mediaCover: String,
    @SerializedName("ownerName") val ownerName: String,
    @SerializedName("beginTime") val beginTime: String,
    @SerializedName("endTime") val endTime: String,
    @SerializedName("quota") val quota: Int,
    @SerializedName("registrants") val registrants: Int,
    @SerializedName("description") val description: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("link")  val link: String
)