package com.example.laboratorio3
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.laboratorio3.ui.navigation.*
import com.example.laboratorio3.ui.viewmodel.LabViewModel
import com.example.laboratorio3.data.model.Song // ¡Importante para que reconozca 'title', 'artist', etc!
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val labViewModel: LabViewModel = viewModel() // ViewModel compartido para sincronizar estados [cite: 245]

            Scaffold(
                bottomBar = {
                    NavigationBar {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        // Tab de Home [cite: 149, 247]
                        NavigationBarItem(
                            selected = currentDestination?.route?.contains("HomeDestination") == true,
                            onClick = { navController.navigate(HomeDestination) },
                            icon = { Icon(Icons.Default.Home, contentDescription = null) },
                            label = { Text("Home") }
                        )
                        // Tab de Highlights [cite: 247]
                        NavigationBarItem(
                            selected = currentDestination?.route?.contains("HighlightsDestination") == true,
                            onClick = { navController.navigate(HighlightsDestination) },
                            icon = { Icon(Icons.Default.Star, contentDescription = null) },
                            label = { Text("Highlights") }
                        )
                    }
                }
            ) { innerPadding ->
                // El NavHost maneja el cambio de pantallas [cite: 118, 121]
                NavHost(
                    navController = navController,
                    startDestination = HomeDestination,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    // PANTALLA DE INICIO (HOME)
                    composable<HomeDestination> {
                        // 1. "Escuchamos" la lista de canciones del ViewModel
                        val songs by labViewModel.songs.collectAsState()

                        // 2. Dibujamos la lista en pantalla
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(songs) { song ->
                                ListItem(
                                    headlineContent = { Text(song.title) },
                                    supportingContent = { Text(song.artist) },
                                    trailingContent = {
                                        // Botón de corazón
                                        IconButton(onClick = { labViewModel.toggleFavorite(song.id) }) {
                                            Icon(
                                                imageVector = if (song.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                                contentDescription = "Favorite",
                                                tint = if (song.isFavorite) Color.Red else Color.Gray
                                            )
                                        }
                                    }
                                )
                                HorizontalDivider() // Línea separadora opcional
                            }
                        }
                    }

                    // PANTALLA DE FAVORITOS (HIGHLIGHTS)
                    composable<HighlightsDestination> {
                        // Usamos la misma lista, pero filtrada
                        val songs by labViewModel.songs.collectAsState()
                        val favorites = songs.filter { it.isFavorite }

                        if (favorites.isEmpty()) {
                            // Mensaje si no hay favoritos
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("No tienes favoritos aún")
                            }
                        } else {
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(favorites) { song ->
                                    ListItem(
                                        headlineContent = { Text(song.title) },
                                        supportingContent = { Text(song.artist) },
                                        leadingContent = { Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
