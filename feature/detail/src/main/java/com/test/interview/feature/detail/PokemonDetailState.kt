package com.test.interview.feature.detail

import androidx.compose.runtime.Immutable
import com.test.interview.feature.pokemon.model.Pokemon

@Immutable
internal data class PokemonDetailState(
    val pokemon: Pokemon? = null,
)
