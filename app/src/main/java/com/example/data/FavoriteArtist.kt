package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_artists")
data class FavoriteArtist(
    @PrimaryKey val artistName: String,
    val isFavorite: Boolean = true,
    val updatedAt: Long = System.currentTimeMillis()
)
