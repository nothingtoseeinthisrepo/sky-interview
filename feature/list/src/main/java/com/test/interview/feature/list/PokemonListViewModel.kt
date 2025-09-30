@file:SuppressLint("StaticFieldLeak")
package com.test.interview.feature.list

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.interview.feature.pokemon.model.Pokemon
import com.test.interview.feature.pokemon.usecase.GetPokemons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class PokemonListState(
    val title: String,
    val pokemons: List<Pokemon> = emptyList()
)

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    getPokemons: GetPokemons,
): ViewModel() {

    private var context: Context? = null

    val uiState: StateFlow<PokemonListState> = getPokemons(count = 20)
        .map { PokemonListState(title = context?.resources?.getString(R.string.list_title).orEmpty(), pokemons = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = PokemonListState(
                title = context?.resources?.getString(R.string.list_title).orEmpty()
            )
        )

    fun setContext(context: Context) {
        this.context = context
    }
}