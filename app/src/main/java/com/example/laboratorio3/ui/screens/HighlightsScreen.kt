package com.example.laboratorio3.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.laboratorio3.ui.viewmodel.LabViewModel

@Composable
fun HighlightsScreen(viewModel: LabViewModel) {
    val songs by viewModel.songs.collectAsState()
    val favoriteSongs = songs.filter { it.isFavorite }

    if (favoriteSongs.isEmpty()) {
        Text("No tienes favoritos aún.")
    } else {
        LazyColumn {
            items(favoriteSongs) { song ->
                // Aquí llamarías a tu SongCard [cite: 242]
                Text("${song.title} - ${song.artist}")
            }
        }
    }
}