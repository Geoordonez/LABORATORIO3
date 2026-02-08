package com.example.laboratorio3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.laboratorio3.ui.navigation.*
import com.example.laboratorio3.ui.screens.*
import com.example.laboratorio3.ui.viewmodel.LabViewModel


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
                    composable<HomeDestination> {
                        // Aquí iría tu HomeScreen existente [cite: 123]
                    }
                    composable<HighlightsDestination> {
                        HighlightsScreen(labViewModel)
                    }
                }
            }
        }
    }
}