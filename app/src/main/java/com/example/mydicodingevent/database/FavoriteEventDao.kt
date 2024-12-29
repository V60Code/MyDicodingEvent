package com.example.mydicodingevent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Delete
import androidx.room.Query
import com.example.mydicodingevent.model.FavoriteEvent

@Dao
interface FavoriteEventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(event: FavoriteEvent)

    @Delete
    suspend fun removeFavorite(event: FavoriteEvent)

    @Query("SELECT COUNT(*) > 0 FROM favorite_events WHERE id = :eventId")
    fun isFavorite(eventId: String): LiveData<Boolean>

    @Query("SELECT * FROM favorite_events")
    fun getAllFavorites(): LiveData<List<FavoriteEvent>>
}
