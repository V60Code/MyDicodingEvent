package com.example.mydicodingevent.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mydicodingevent.preferences.ThemePreferences
import kotlinx.coroutines.launch

class ThemeViewModel(private val preferences: ThemePreferences) : ViewModel() {

    // Observe the dark mode setting as LiveData
    val isDarkMode = preferences.isDarkMode.asLiveData()  // Remove parentheses

    // Save the dark mode setting
    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            preferences.saveThemeSetting(isDarkMode)  // Ensure this matches the function name in ThemePreferences
        }
    }
}
