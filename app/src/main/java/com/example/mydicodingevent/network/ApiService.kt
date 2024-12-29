package com.example.mydicodingevent.network

import com.example.mydicodingevent.model.EventResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("events")
    fun getEvents(): Call<EventResponse>

}
