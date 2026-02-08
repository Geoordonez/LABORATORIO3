package com.example.laboratorio3.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val isFavorite: Boolean = false // Campo requerido para el reto
)