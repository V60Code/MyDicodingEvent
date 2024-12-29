package com.example.mydicodingevent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.mydicodingevent.database.AppDatabase
import com.example.mydicodingevent.databinding.ActivityEventDetailBinding
import com.example.mydicodingevent.model.EventResponse
import com.example.mydicodingevent.model.FavoriteEvent
import com.example.mydicodingevent.network.ApiConfig
import com.example.mydicodingevent.ui.viewmodel.FavoriteViewModel
import com.example.mydicodingevent.ui.viewmodel.FavoriteViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory(AppDatabase.getDatabase(this).favoriteEventDao())
    }

    private lateinit var btnRegister: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var favoriteButton: FloatingActionButton
    private var isFavorite = false

    private var eventId: Int = 0
    private lateinit var eventName: String
    private var eventDescription: String? = null
    private var eventMediaCover: String? = null
    private var eventLink: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnRegister = binding.btnRegister
        favoriteButton = binding.fabFavorite
        progressBar = binding.progressBar

        eventId = intent.getIntExtra("EVENT_ID", 0)
        Log.d("EventDetailActivity", "Event ID: $eventId")

        showLoading(true)
        loadEventDetails(eventId)

        // Check if the event is already in favorites
        favoriteViewModel.isFavorite(eventId.toString()).observe(this) { favorite ->
            isFavorite = favorite
            updateFavoriteIcon()
        }

        // Handle favorite button click
        // Handle favorite button click
        favoriteButton.setOnClickListener {
            val favoriteEvent = FavoriteEvent(
                id = eventId.toString(),
                name = eventName,
                description = eventDescription,
                mediaCover = eventMediaCover
            )

            if (isFavorite) {
                favoriteViewModel.removeFavorite(favoriteEvent) // Pass the FavoriteEvent object
                Toast.makeText(this, "$eventName removed from favorites", Toast.LENGTH_SHORT).show()
            } else {
                favoriteViewModel.addFavorite(favoriteEvent)
                Toast.makeText(this, "$eventName added to favorites", Toast.LENGTH_SHORT).show()
            }
            isFavorite = !isFavorite
            updateFavoriteIcon()
        }

    }

    private fun loadEventDetails(eventId: Int) {
        val client = ApiConfig.getApiService().getEvents()
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    val eventResponse = response.body()
                    val event = eventResponse?.listEvents?.find { it.id == eventId }
                    Log.d("EventDetailActivity", "Event: $event")
                    if (event != null) {
                        eventName = event.name
                        eventDescription = event.description
                        eventMediaCover = event.mediaCover
                        eventLink = event.link

                        binding.tvEventName.text = eventName
                        binding.tvEventDescription.text = HtmlCompat.fromHtml(eventDescription ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
                        binding.tvEventTime.text = getString(R.string.event_time, event.beginTime)
                        binding.tvEventOrganizer.text = getString(R.string.event_organizer, event.ownerName)
                        binding.tvEventQuota.text = getString(R.string.event_quota, event.quota - event.registrants)

                        Glide.with(this@EventDetailActivity)
                            .load(eventMediaCover)
                            .into(binding.imgEventCover)

                        btnRegister.setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(eventLink))
                            startActivity(intent)
                        }
                    } else {
                        Log.d("EventDetailActivity", "Event not found")
                    }
                } else {
                    Log.d("EventDetailActivity", "Response unsuccessful: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Log.e("EventDetailActivity", "Failed to fetch event details: ${t.message}")
                showLoading(false)
            }
        })
    }

    private fun updateFavoriteIcon() {
        if (isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_favorite_filled) // Change to filled heart icon
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite) // Change to outlined heart icon
        }
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
