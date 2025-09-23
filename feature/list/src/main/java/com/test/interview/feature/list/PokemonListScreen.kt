package com.test.interview.feature.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.interview.feature.pokemon.model.Pokemon

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel = hiltViewModel(),
    onClick: (Pokemon) -> Unit = {}
) {
    viewModel.setContext(LocalContext.current)
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    PokemonList(state.value, onClick)
}

@Composable
fun PokemonList(
    state: PokemonListState,
    onClick: (Pokemon) -> Unit
) = Column {
    Text(
        color = Color.Black,
        fontSize = 48.sp,
        text = state.title,
    )

    if (state.pokemons.isEmpty()) return
    LazyColumn {
        items(
            items = state.pokemons,
            contentType = { it.id }
        ) { pokemon ->
            PokemonListItem(pokemon, onClick)
        }
    }
}
