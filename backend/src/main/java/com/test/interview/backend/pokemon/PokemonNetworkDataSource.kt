package com.test.interview.backend.pokemon

import com.test.interview.backend.pokemon.dto.PokemonDto
import javax.inject.Inject

class PokemonNetworkDataSource @Inject constructor(
    private val api: PokemonApi,
) {
    suspend fun getPokemon(name: String): PokemonDto = api.getPokemon(name)
}