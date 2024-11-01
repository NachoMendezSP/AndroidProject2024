package com.undef.mendez.eventosculturales.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    var username = mutableStateOf("Ignacio Mendez")
    var email = mutableStateOf("imendez@ejemplo.com")
    var notificationsEnabled = mutableStateOf(true)
    var notificationDaysBefore = mutableIntStateOf(3)

    // Función para simular guardar cambios
    fun saveChanges(onComplete: () -> Unit) {
        viewModelScope.launch {
            // Lógica para guardar los cambios
            onComplete()
        }
    }

    // Función para cerrar sesión
    fun logout(onLogout: () -> Unit) {
        viewModelScope.launch {
            onLogout()
        }
    }
}
