package com.ucfjoe.marvelcomics.feature_marvel.presentation.ui.character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ucfjoe.marvelcomics.feature_marvel.presentation.ui.UiEvent

@Composable
fun CharacterScreen(
    onPopBackStack: () -> Unit,
    onNavigation: (UiEvent.Navigation) -> Unit,
    viewModel: CharacterViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is UiEvent.Navigation -> onNavigation(event)
            }
        }
    }

    val state by remember { viewModel.state }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = 16.dp
        )
    ) {
        item {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = state.character.thumbnail,
                contentDescription = "Marvel Character",
                contentScale = ContentScale.FillWidth // Very important!!!
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = state.character.name,
                    modifier = Modifier.padding(6.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        if (state.character.description.isNotBlank()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = state.character.description)
                }
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.weight(.5f)) {
                    Text("Comics: ${state.character.comicsCounts}")
                    Text("Series: ${state.character.seriesCount}")
                }
                Column(modifier = Modifier.weight(.5f)) {
                    Text("Stories: ${state.character.storiesCount}")
                    Text("Events: ${state.character.eventsCount}")
                }
            }
        }
        if (state.showComicsOption) {
            if (state.showComicsLink) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        TextButton(
                            onClick = { viewModel.onShowComicsClicked() }
                        ) {
                            Text("Show Comics")
                        }
                    }
                }
            }
            if (state.showComics) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Comics",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                }
                items(state.comics.size) {
                    if (it >= state.comics.size - 1 && !state.endReached && !state.isLoading) {
                        viewModel.loadComicsPaginated()
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        ComicItem(comic = state.comics[it])
                    }
                }
                if (state.isLoading) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}
