package com.example.mydicodingevent.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mydicodingevent.EventDetailActivity
import com.example.mydicodingevent.R
import com.example.mydicodingevent.ui.adapter.EventAdapter
import com.example.mydicodingevent.ui.viewmodel.EventViewModel

class HomeFragment : Fragment() {

    private lateinit var rvUpcomingEvents: RecyclerView
    private lateinit var rvFinishedEvents: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var progressBarUpcoming: ProgressBar
    private lateinit var progressBarFinished: ProgressBar
    private val eventViewModel: EventViewModel by viewModels()

    private lateinit var upcomingAdapter: EventAdapter
    private lateinit var finishedAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        rvUpcomingEvents = view.findViewById(R.id.rv_upcoming_events)
        rvFinishedEvents = view.findViewById(R.id.rv_finished_events)
        searchView = view.findViewById(R.id.search_view)
        progressBarUpcoming = view.findViewById(R.id.progressBarUpcoming)
        progressBarFinished = view.findViewById(R.id.progressBarFinished)

        rvUpcomingEvents.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvFinishedEvents.layoutManager = LinearLayoutManager(context)

        setupSearchView()
        loadUpcomingEvents()
        loadFinishedEvents()

        return view
    }

    private fun setupSearchView() {
        searchView.setIconifiedByDefault(false)
        searchView.clearFocus()

        val searchPlate = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchPlate.hint = "Search events"
        searchPlate.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        searchPlate.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterEvents(newText.orEmpty())
                return true
            }
        })
    }

    private fun filterEvents(query: String) {
        val filteredUpcoming = eventViewModel.upcomingEvents.value?.filter {
            it.name.contains(query, true)
        } ?: emptyList()

        val filteredFinished = eventViewModel.finishedEvents.value?.filter {
            it.name.contains(query, true)
        } ?: emptyList()

        upcomingAdapter = EventAdapter(filteredUpcoming) { event ->
            val intent = Intent(context, EventDetailActivity::class.java)
            intent.putExtra("EVENT_ID", event.id)
            startActivity(intent)
        }
        rvUpcomingEvents.adapter = upcomingAdapter

        finishedAdapter = EventAdapter(filteredFinished) { event ->
            val intent = Intent(context, EventDetailActivity::class.java)
            intent.putExtra("EVENT_ID", event.id)
            startActivity(intent)
        }
        rvFinishedEvents.adapter = finishedAdapter
    }

    private fun loadUpcomingEvents() {
        eventViewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            progressBarUpcoming.visibility = View.GONE
            upcomingAdapter = EventAdapter(events) { event ->
                val intent = Intent(context, EventDetailActivity::class.java)
                intent.putExtra("EVENT_ID", event.id)
                startActivity(intent)
            }
            rvUpcomingEvents.adapter = upcomingAdapter
        }

        eventViewModel.isLoadingUpcoming.observe(viewLifecycleOwner) { isLoading ->
            progressBarUpcoming.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        eventViewModel.getUpcomingEvents()
    }

    private fun loadFinishedEvents() {
        eventViewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            progressBarFinished.visibility = View.GONE
            finishedAdapter = EventAdapter(events) { event ->
                val intent = Intent(context, EventDetailActivity::class.java)
                intent.putExtra("EVENT_ID", event.id)
                startActivity(intent)
            }
            rvFinishedEvents.adapter = finishedAdapter
        }

        eventViewModel.isLoadingFinished.observe(viewLifecycleOwner) { isLoading ->
            progressBarFinished.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        eventViewModel.getFinishedEvents()
    }
}
