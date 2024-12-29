package com.example.mydicodingevent.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mydicodingevent.databinding.FragmentSettingsBinding
import com.example.mydicodingevent.preferences.ThemePreferences
import com.example.mydicodingevent.ui.viewmodel.ThemeViewModel
import com.example.mydicodingevent.ui.viewmodel.ThemeViewModelFactory

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val themeViewModel: ThemeViewModel by viewModels {
        ThemeViewModelFactory(ThemePreferences(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe theme setting and set switch state
        themeViewModel.isDarkMode.observe(viewLifecycleOwner) { isDarkMode ->
            binding.switchTheme.isChecked = isDarkMode
        }

        // Update theme setting when the switch is toggled
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            themeViewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
