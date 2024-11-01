package com.undef.mendez.eventosculturales.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

data class Event(
    val name: String,
    val date: String,
    val location: String,
    var isFavorite: Boolean = false
)

class EventsViewModel : ViewModel() {

    // Lista de todos los eventos
    private val _events = mutableStateListOf(
        Event("Concierto de Jazz", "24 Nov 2023", "Teatro Principal"),
        Event("Exposición de Arte", "1 Dic 2023", "Museo de Arte Moderno"),
        Event("Obra de Teatro", "15 Dic 2023", "Auditorio Central"),
        Event("Festival de Cine Independiente", "20 Ene 2024", "Cinepolis"),
        Event("Concierto de Música Clásica", "5 Feb 2024", "Auditorio Nacional"),
        Event("Feria del Libro", "12 Mar 2024", "Centro de Convenciones"),
        Event("Exposición de Fotografía", "25 Mar 2024", "Galería Urbana")
    )
    val events: List<Event> get() = _events

    // Estado para el evento seleccionado
    var selectedEvent = mutableStateOf<Event?>(null)
        private set

    // Estado para la búsqueda
    var searchQuery by mutableStateOf("")
        private set

    // Lista filtrada de eventos según la búsqueda
    val filteredEvents: List<Event>
        get() = if (searchQuery.isEmpty()) {
            _events
        } else {
            _events.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.location.contains(searchQuery, ignoreCase = true)
            }
        }

    // Lista de eventos marcados como favoritos
    val favoriteEvents: List<Event>
        get() = _events.filter { it.isFavorite }

    // Función para seleccionar un evento (para detalles)
    fun selectEvent(eventName: String) {
        selectedEvent.value = _events.find { it.name == eventName }
    }

    // Función para alternar el estado de favorito de un evento
    fun toggleFavorite(event: Event) {
        val index = _events.indexOf(event)
        if (index >= 0) {
            _events[index] = _events[index].copy(isFavorite = !event.isFavorite)
        }
    }

    // Función para actualizar el texto de búsqueda
    fun updateSearchQuery(query: String) {
        searchQuery = query
    }
}
