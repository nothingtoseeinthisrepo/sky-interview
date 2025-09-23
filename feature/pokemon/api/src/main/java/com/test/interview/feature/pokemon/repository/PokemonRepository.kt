package com.test.interview.feature.pokemon.repository

import com.test.interview.feature.pokemon.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun get(count: Int): Flow<List<Pokemon>>
    fun getDetail(pokemonId: String): Flow<Pokemon>
}