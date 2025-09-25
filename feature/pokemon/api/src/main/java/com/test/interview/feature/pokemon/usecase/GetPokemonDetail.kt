package com.test.interview.feature.pokemon.usecase

import com.test.interview.feature.pokemon.model.Pokemon
import kotlinx.coroutines.flow.Flow

fun interface GetPokemonDetail {
    operator fun invoke(pokemonId: Int): Flow<Pokemon>
}