package com.undef.mendez.eventosculturales

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FavoritesScreen(
    favoriteEvents: List<Event>,
    onFavoriteToggle: (Event) -> Unit,
    onEventClick: (Event) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (favoriteEvents.isEmpty()) {
            item {
                Text(
                    text = "No tienes eventos marcados como favoritos",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            items(favoriteEvents) { event ->
                EventCard(
                    event = event,
                    isFavorite = true,
                    onFavoriteToggle = onFavoriteToggle,
                    onClick = { onEventClick(event) }
                )
            }
        }
    }
}
