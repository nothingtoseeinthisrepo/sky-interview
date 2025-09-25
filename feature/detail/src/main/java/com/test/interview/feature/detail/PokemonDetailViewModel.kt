package com.test.interview.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.interview.feature.pokemon.model.Pokemon
import com.test.interview.feature.pokemon.usecase.GetPokemonDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    getPokemonDetail: GetPokemonDetail,
): ViewModel() {

    val uiState: StateFlow<Pokemon?> = getPokemonDetail(1)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = null
        )
}