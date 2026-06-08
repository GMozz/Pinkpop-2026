package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    val allFavorites: Flow<List<FavoriteArtist>> = favoriteDao.getAllFavorites()
    
    val favoriteArtistNames: Flow<Set<String>> = favoriteDao.getAllFavorites().map { list ->
        list.map { it.artistName }.toSet()
    }

    suspend fun addFavorite(artistName: String) {
        favoriteDao.insertFavorite(FavoriteArtist(artistName = artistName))
    }

    suspend fun removeFavorite(artistName: String) {
        favoriteDao.deleteByArtistName(artistName)
    }

    suspend fun isFavorite(artistName: String): Boolean {
        return favoriteDao.isArtistFavorite(artistName)
    }
}
