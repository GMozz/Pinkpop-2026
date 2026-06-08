package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_artists ORDER BY updatedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteArtist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteArtist)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteArtist)

    @Query("DELETE FROM favorite_artists WHERE artistName = :artistName")
    suspend fun deleteByArtistName(artistName: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_artists WHERE artistName = :artistName LIMIT 1)")
    suspend fun isArtistFavorite(artistName: String): Boolean
}
