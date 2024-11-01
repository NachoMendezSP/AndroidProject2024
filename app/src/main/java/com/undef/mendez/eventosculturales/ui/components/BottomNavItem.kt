package com.undef.mendez.eventosculturales.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Events : BottomNavItem("events", "Eventos", Icons.Default.Home)
    object Favorites : BottomNavItem("favorites", "Favoritos", Icons.Default.Favorite)
    object Profile : BottomNavItem("profile", "Perfil", Icons.Default.Person)
}
