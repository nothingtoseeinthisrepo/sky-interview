package com.test.interview.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.interview.feature.pokemon.model.Pokemon
import com.test.interview.feature.pokemon.usecase.GetPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    getPokemon: GetPokemon,
): ViewModel() {

    val uiState: StateFlow<Pokemon?> = getPokemon("bulbasaur")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = null
        )
}