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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.undef.mendez.eventosculturales.ui.screens.AuthScreen
import com.undef.mendez.eventosculturales.ui.screens.BottomNavBar
import com.undef.mendez.eventosculturales.ui.screens.EventDetailScreen
import com.undef.mendez.eventosculturales.ui.screens.EventsScreen
import com.undef.mendez.eventosculturales.ui.screens.FavoritesScreen
import com.undef.mendez.eventosculturales.ui.screens.ProfileScreen
import com.undef.mendez.eventosculturales.ui.screens.RegisterScreen
import com.undef.mendez.eventosculturales.ui.screens.SplashScreen
import com.undef.mendez.eventosculturales.ui.theme.EventosCulturalesTheme
import com.undef.mendez.eventosculturales.viewmodel.*

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
    val authViewModel: AuthViewModel = viewModel()
    val eventsViewModel: EventsViewModel = viewModel()
    var showBottomBar by remember { mutableStateOf(false) }

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
                    onLoginSuccess = { navController.navigate("events") },
                    onRegister = { navController.navigate("register") },
                    viewModel = authViewModel
                )
            }

            composable("register") {
                RegisterScreen(
                    onRegisterComplete = {
                        navController.popBackStack()  // Regresa a la pantalla de inicio de sesión después del registro
                    },
                    onBackToLogin = {
                        navController.popBackStack()  // Regresa a la pantalla de inicio de sesión si se toca "Volver"
                    },
                    viewModel = authViewModel
                )
            }

            composable("events") {
                EventsScreen(
                    viewModel = eventsViewModel,
                    onEventClick = { event ->
                        eventsViewModel.selectEvent(event.name)
                        navController.navigate("eventDetail")
                    }
                )
            }

            composable("favorites") {
                FavoritesScreen(
                    viewModel = eventsViewModel,
                    onEventClick = { event ->
                        eventsViewModel.selectEvent(event.name)
                        navController.navigate("eventDetail")
                    }
                )
            }

            composable("eventDetail") {
                eventsViewModel.selectedEvent.value?.let { event ->
                    EventDetailScreen(event)
                }
            }

            composable("profile") {
                val profileViewModel: ProfileViewModel = viewModel() // Instancia el ViewModel aquí
                val context = LocalContext.current

                ProfileScreen(
                    onSaveChanges = {
                        Toast.makeText(context, "Cambios guardados exitosamente", Toast.LENGTH_SHORT).show()
                    },
                    onLogout = {
                        navController.navigate("auth") {
                            popUpTo("events") { inclusive = true }
                        }
                    },
                    authViewModel = authViewModel,
                    viewModel = profileViewModel
                )
            }

        }
    }
}
