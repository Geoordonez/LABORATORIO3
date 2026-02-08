package com.example.laboratorio3.data.model

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    var isFavorite: Boolean = false
)