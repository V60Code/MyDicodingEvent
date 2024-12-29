package com.example.mydicodingevent

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class MyApplication : Application() {
    companion object {
        // Singleton instance of DataStore for theme preferences
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("theme_preferences")

        val Context.themeDataStore: DataStore<Preferences>
            get() = dataStore
    }
}
