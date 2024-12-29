package com.example.mydicodingevent.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydicodingevent.database.FavoriteEventDao
import com.example.mydicodingevent.model.FavoriteEvent
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteEventDao: FavoriteEventDao) : ViewModel() {

    // Holds all favorite events
    val favoriteEvents: LiveData<List<FavoriteEvent>> = favoriteEventDao.getAllFavorites()

    // Adds a favorite event
    fun addFavorite(event: FavoriteEvent) {
        viewModelScope.launch {
            favoriteEventDao.addFavorite(event)
        }
    }

    // Removes a favorite event
    fun removeFavorite(event: FavoriteEvent) {
        viewModelScope.launch {
            favoriteEventDao.removeFavorite(event)
        }
    }

    // Checks if an event is a favorite
    fun isFavorite(eventId: String): LiveData<Boolean> {
        return favoriteEventDao.isFavorite(eventId)
    }
}
