package com.example.laboratorio3.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.laboratorio3.data.model.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LabViewModel : ViewModel() {
    // Estado privado (Mutable) y público (Inmutable) [cite: 180]
    private val _songs = MutableStateFlow<List<Song>>(
        listOf(
            Song("1", "Bohemian Rhapsody", "Queen"),
            Song("2", "Stairway to Heaven", "Led Zeppelin"),
            Song("3", "Highway to Hell", "AC/DC")
        )
    )
    val songs = _songs.asStateFlow()

    // Evento para cambiar el estado de favorito (UI -> ViewModel) [cite: 171, 241]
    fun toggleFavorite(songId: String) {
        _songs.value = _songs.value.map {
            if (it.id == songId) it.copy(isFavorite = !it.isFavorite) else it
        }
    }
}