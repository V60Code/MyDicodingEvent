package com.example.mydicodingevent.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydicodingevent.databinding.FragmentUpcomingBinding
import com.example.mydicodingevent.ui.adapter.EventAdapter
import com.example.mydicodingevent.ui.viewmodel.EventViewModel
import com.example.mydicodingevent.EventDetailActivity

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private val eventViewModel: EventViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewUpcoming.layoutManager = LinearLayoutManager(context)

        eventViewModel.isLoadingUpcoming.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.recyclerViewUpcoming.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.recyclerViewUpcoming.visibility = View.VISIBLE
            }
        }

        eventViewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            binding.recyclerViewUpcoming.adapter = EventAdapter(events.take(5)) { event ->
                val intent = Intent(context, EventDetailActivity::class.java)
                intent.putExtra("EVENT_ID", event.id)
                startActivity(intent)
            }
        }

        eventViewModel.getUpcomingEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
