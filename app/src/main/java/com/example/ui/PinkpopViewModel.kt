package com.example.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.FavoriteRepository
import com.example.model.FestivalDay
import com.example.model.Performance
import com.example.model.PinkpopData
import com.example.notifications.AlarmHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PinkpopViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val repository = FavoriteRepository(database.favoriteDao())

    val favoriteArtists: StateFlow<Set<String>> = repository.favoriteArtistNames
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    private val _selectedDayIndex = MutableStateFlow(0) // Default to 0 (Vrijdag)
    val selectedDayIndex: StateFlow<Int> = _selectedDayIndex.asStateFlow()

    private val _hiddenStages = MutableStateFlow<Set<String>>(
        PinkpopData.days.flatMap { day -> day.stages.map { it.name } }.distinct().let { names ->
            names.drop(4).toSet()
        }
    )
    val hiddenStages: StateFlow<Set<String>> = _hiddenStages.asStateFlow()

    fun selectDay(index: Int) {
        if (index in PinkpopData.days.indices) {
            _selectedDayIndex.value = index
        }
    }

    fun toggleStageVisibility(stageName: String) {
        val current = _hiddenStages.value.toMutableSet()
        if (current.contains(stageName)) {
            current.remove(stageName)
        } else {
            current.add(stageName)
        }
        _hiddenStages.value = current
    }

    fun isStageVisible(stageName: String): Boolean {
        return !_hiddenStages.value.contains(stageName)
    }

    fun resetFilters() {
        _hiddenStages.value = emptySet()
    }

    fun toggleFavorite(context: Context, day: FestivalDay, performance: Performance, stageName: String) {
        viewModelScope.launch {
            val isFav = favoriteArtists.value.contains(performance.artist)
            if (isFav) {
                repository.removeFavorite(performance.artist)
                AlarmHelper.cancelAlarmForPerformance(context, performance)
            } else {
                repository.addFavorite(performance.artist)
                AlarmHelper.scheduleAlarmForPerformance(context, day, performance, stageName)
            }
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PinkpopViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PinkpopViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
