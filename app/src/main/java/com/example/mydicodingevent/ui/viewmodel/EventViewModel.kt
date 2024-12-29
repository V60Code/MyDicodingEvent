package com.example.mydicodingevent.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mydicodingevent.model.EventItem
import com.example.mydicodingevent.model.EventResponse
import com.example.mydicodingevent.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class EventViewModel : ViewModel() {

    private val _events = MutableLiveData<List<EventItem>>()
    val events: LiveData<List<EventItem>> = _events

    private val _upcomingEvents = MutableLiveData<List<EventItem>>()
    val upcomingEvents: LiveData<List<EventItem>> = _upcomingEvents

    private val _finishedEvents = MutableLiveData<List<EventItem>>()
    val finishedEvents: LiveData<List<EventItem>> = _finishedEvents

    private val _isLoadingUpcoming = MutableLiveData<Boolean>()
    val isLoadingUpcoming: LiveData<Boolean> = _isLoadingUpcoming

    private val _isLoadingFinished = MutableLiveData<Boolean>()
    val isLoadingFinished: LiveData<Boolean> = _isLoadingFinished

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getUpcomingEvents() {
        _isLoadingUpcoming.value = true
        val client = ApiConfig.getApiService().getEvents()
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoadingUpcoming.value = false
                if (response.isSuccessful) {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val currentTime = System.currentTimeMillis()

                    val upcomingEvents = response.body()?.listEvents?.filter { event ->
                        val eventBeginTime = dateFormat.parse(event.beginTime)?.time
                        eventBeginTime != null && eventBeginTime > currentTime
                    } ?: emptyList()

                    _upcomingEvents.value = upcomingEvents
                } else {
                    _error.postValue("Failed to load upcoming events")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoadingUpcoming.value = false
                _error.postValue(t.message)
            }
        })
    }

    fun getFinishedEvents() {
        _isLoadingFinished.value = true
        val client = ApiConfig.getApiService().getEvents()
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoadingFinished.value = false
                if (response.isSuccessful) {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val currentTime = System.currentTimeMillis()

                    val finishedEvents = response.body()?.listEvents?.filter { event ->
                        val eventEndTime = dateFormat.parse(event.endTime)?.time
                        eventEndTime != null && eventEndTime < currentTime
                    } ?: emptyList()

                    _finishedEvents.value = finishedEvents
                } else {
                    _error.postValue("Failed to load finished events")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoadingFinished.value = false
                _error.postValue(t.message)
            }
        })
    }
}
