package com.example.mydicodingevent

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mydicodingevent.databinding.ActivityMainBinding
import com.example.mydicodingevent.preferences.ThemePreferences
import com.example.mydicodingevent.ui.FavoriteFragment
import com.example.mydicodingevent.ui.FinishedFragment
import com.example.mydicodingevent.ui.HomeFragment
import com.example.mydicodingevent.ui.SettingsFragment
import com.example.mydicodingevent.ui.UpcomingFragment
import com.example.mydicodingevent.ui.viewmodel.ThemeViewModel
import com.example.mydicodingevent.ui.viewmodel.ThemeViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val themeViewModel: ThemeViewModel by lazy {
        ViewModelProvider(this, ThemeViewModelFactory(ThemePreferences(this)))[ThemeViewModel::class.java]
    }
    private var selectedFragmentId: Int = R.id.nav_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up theme observer
        themeViewModel.isDarkMode.observe(this) { isDarkMode ->
            setAppTheme(isDarkMode)
            Log.d("MainActivity", "Current theme isDarkMode: $isDarkMode")
        }

        // Restore selected fragment on recreation
        selectedFragmentId = savedInstanceState?.getInt("selected_fragment_id") ?: R.id.nav_home
        loadFragment(selectedFragmentId)

        // Initialize Bottom Navigation
        binding.bottomNavigation.selectedItemId = selectedFragmentId
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            if (selectedFragmentId != item.itemId) {
                selectedFragmentId = item.itemId
                loadFragment(selectedFragmentId)
            }
            true
        }
    }

    // Function to switch fragments based on selected item
    private fun loadFragment(itemId: Int) {
        val fragment: Fragment = when (itemId) {
            R.id.nav_home -> HomeFragment()
            R.id.nav_upcoming -> UpcomingFragment()
            R.id.nav_finished -> FinishedFragment()
            R.id.nav_favorite -> FavoriteFragment()
            R.id.nav_settings -> SettingsFragment()
            else -> HomeFragment()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    // Set theme based on dark mode preference
    private fun setAppTheme(isDarkMode: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selected_fragment_id", selectedFragmentId)
    }
}
