package com.undef.mendez.eventosculturales.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.undef.mendez.eventosculturales.viewmodel.*

@Composable
fun FavoritesScreen(
    viewModel: EventsViewModel = viewModel(),
    onEventClick: (Event) -> Unit
) {
    val favoriteEvents = viewModel.favoriteEvents

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
                    onFavoriteToggle = { viewModel.toggleFavorite(event) },
                    onClick = { onEventClick(event) }
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen(viewModel = EventsViewModel(), onEventClick = {})
}