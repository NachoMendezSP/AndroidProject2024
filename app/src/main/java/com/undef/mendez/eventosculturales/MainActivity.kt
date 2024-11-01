package com.undef.mendez.eventosculturales

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.undef.mendez.eventosculturales.ui.theme.EventosCulturalesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventosCulturalesTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var showBottomBar by remember { mutableStateOf(false) }
    val favoriteEvents = remember { mutableStateListOf<Event>() }

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            showBottomBar = backStackEntry.destination.route !in listOf("auth", "splash", "register")
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("splash") {
                SplashScreen {
                    navController.navigate("auth") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            }

            composable("auth") {
                AuthScreen(
                    onLogin = { navController.navigate("events") },
                    onRegister = { navController.navigate("register") }
                )
            }

            composable("register") {
                RegisterScreen(
                    onRegisterComplete = {
                        navController.popBackStack()  // Regresa a la pantalla de inicio de sesión después del registro
                    },
                    onBackToLogin = {
                        navController.popBackStack()  // Regresa a la pantalla de inicio de sesión si se toca "Volver"
                    }
                )
            }

            composable("events") {
                val sampleEvents = listOf(
                    Event("Concierto de Jazz", "24 Nov 2023", "Teatro Principal"),
                    Event("Exposición de Arte", "1 Dic 2023", "Museo de Arte Moderno"),
                    Event("Obra de Teatro", "15 Dic 2023", "Auditorio Central")
                )
                EventsScreen(
                    events = sampleEvents,
                    favoriteEvents = favoriteEvents,
                    onFavoriteToggle = { event ->
                        if (favoriteEvents.contains(event)) {
                            favoriteEvents.remove(event)
                        } else {
                            favoriteEvents.add(event)
                        }
                    },
                    onEventClick = { event ->
                        navController.navigate("eventDetail/${event.name}")
                    }
                )
            }

            composable("favorites") {
                FavoritesScreen(
                    favoriteEvents = favoriteEvents,
                    onFavoriteToggle = { event ->
                        if (favoriteEvents.contains(event)) {
                            favoriteEvents.remove(event)
                        } else {
                            favoriteEvents.add(event)
                        }
                    },
                    onEventClick = { event ->
                        navController.navigate("eventDetail/${event.name}")
                    }
                )
            }

            composable("eventDetail/{eventName}") { backStackEntry ->
                val eventName = backStackEntry.arguments?.getString("eventName")
                val event = favoriteEvents.find { it.name == eventName }
                    ?: Event(eventName ?: "Evento Desconocido", "Fecha Desconocida", "Ubicación Desconocida")

                EventDetailScreen(event)
            }

            composable("profile") {
                val context = LocalContext.current

                ProfileScreen(onSaveChanges = {
                    Toast.makeText(context, "Cambios guardados exitosamente", Toast.LENGTH_SHORT).show()
                })
            }
        }
    }
}
