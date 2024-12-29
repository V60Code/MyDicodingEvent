package com.example.mydicodingevent.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.viewModels
import com.example.mydicodingevent.EventDetailActivity
import com.example.mydicodingevent.database.AppDatabase
import com.example.mydicodingevent.databinding.FragmentFavoriteBinding
import com.example.mydicodingevent.ui.adapter.EventAdapter
import com.example.mydicodingevent.ui.adapter.FavoriteEventAdapter
import com.example.mydicodingevent.ui.viewmodel.FavoriteViewModel
import com.example.mydicodingevent.ui.viewmodel.FavoriteViewModelFactory

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory(AppDatabase.getDatabase(requireContext()).favoriteEventDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewFavorite.layoutManager = LinearLayoutManager(context)

        favoriteViewModel.favoriteEvents.observe(viewLifecycleOwner) { events ->
            if (events.isNullOrEmpty()) {
                Toast.makeText(context, "No favorite events found", Toast.LENGTH_SHORT).show()
            } else {
                binding.recyclerViewFavorite.adapter = FavoriteEventAdapter(events) { event ->
                    // Action ketika item favorit di-klik

                    val eventid = event.id.toInt()

                    val intent = Intent(context, EventDetailActivity::class.java)
                    intent.putExtra("EVENT_ID", eventid)
                    startActivity(intent)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
