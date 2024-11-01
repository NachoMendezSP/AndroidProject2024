package com.undef.mendez.eventosculturales.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    var isAuthenticated = mutableStateOf(false)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    // TODO: implementar lógica
    fun login(email: String, password: String) {
        viewModelScope.launch {
            // Marcar como autenticado sin lógica real para pruebas
            isAuthenticated.value = true
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            isAuthenticated.value = true // Registro exitoso (simulación)
            errorMessage.value = null
        }
    }

    fun logout() {
        isAuthenticated.value = false
    }
}
