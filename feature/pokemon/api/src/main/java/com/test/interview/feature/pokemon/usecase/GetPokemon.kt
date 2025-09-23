package com.test.interview.feature.pokemon.usecase

import com.test.interview.feature.pokemon.model.Pokemon
import kotlinx.coroutines.flow.Flow

fun interface GetPokemon {
    operator fun invoke(pokemonId: String): Flow<Pokemon>
}