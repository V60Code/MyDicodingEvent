package com.example.mydicodingevent.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.mydicodingevent.MyApplication.Companion.themeDataStore

class ThemePreferences(private val context: Context) {

    // Key for theme preference
    private val themeKey = booleanPreferencesKey("theme_key")

    // Flow to observe theme settings
    val isDarkMode: Flow<Boolean> = context.themeDataStore.data
        .map { preferences ->
            preferences[themeKey] ?: false
        }

    // Save theme settings
    suspend fun saveThemeSetting(isDarkMode: Boolean) {
        context.themeDataStore.edit { preferences ->
            preferences[themeKey] = isDarkMode
        }
    }
}
