package com.test.interview.feature.pokemon.usecase

import com.test.interview.feature.pokemon.model.Pokemon
import kotlinx.coroutines.flow.Flow

fun interface GetPokemons {
    operator fun invoke(count: Int): Flow<List<Pokemon>>
}