package com.example.mydicodingevent.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mydicodingevent.database.FavoriteEventDao

class FavoriteViewModelFactory(private val favoriteEventDao: FavoriteEventDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(favoriteEventDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
