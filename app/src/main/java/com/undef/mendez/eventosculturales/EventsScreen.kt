package com.undef.mendez.eventosculturales

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview

data class Event(
    val name: String,
    val date: String,
    val location: String
)

@Composable
fun EventsScreen(
    events: List<Event>,
    favoriteEvents: List<Event>,
    onFavoriteToggle: (Event) -> Unit,
    onEventClick: (Event) -> Unit // Nueva función para manejar el clic en un evento
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // Filtrar eventos basándose en el texto de búsqueda
    val filteredEvents = events.filter { event ->
        event.name.contains(searchQuery.text, ignoreCase = true) ||
                event.location.contains(searchQuery.text, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Barra de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar eventos") },
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Lista de eventos filtrados
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredEvents) { event ->
                EventCard(
                    event = event,
                    isFavorite = favoriteEvents.contains(event),
                    onFavoriteToggle = onFavoriteToggle,
                    onClick = { onEventClick(event) } // Al hacer clic en el evento
                )
            }
        }
    }
}

@Composable
fun EventCard(
    event: Event,
    isFavorite: Boolean,
    onFavoriteToggle: (Event) -> Unit,
    onClick: () -> Unit // Función de clic para navegar al detalle
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }, // Detecta clics para ir a la pantalla de detalle
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = event.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Fecha: ${event.date}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ubicación: ${event.location}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            IconButton(onClick = { onFavoriteToggle(event) }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isFavorite) "Quitar de favoritos" else "Agregar a favoritos",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventsScreenPreview() {
    val sampleEvents = listOf(
        Event("Concierto de Jazz", "24 Nov 2023", "Teatro Principal"),
        Event("Exposición de Arte", "1 Dic 2023", "Museo de Arte Moderno"),
        Event("Obra de Teatro", "15 Dic 2023", "Auditorio Central")
    )
    EventsScreen(events = sampleEvents, favoriteEvents = listOf(), onFavoriteToggle = {}, onEventClick = {})
}
