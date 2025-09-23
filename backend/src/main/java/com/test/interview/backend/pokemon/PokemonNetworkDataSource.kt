package com.test.interview.backend.pokemon

import com.test.interview.backend.pokemon.dto.PokemonDto
import javax.inject.Inject

class PokemonNetworkDataSource @Inject constructor(
    private val api: PokemonApi,
) {
    suspend fun getPokemon(count: Int): List<PokemonDto> {
        return 1.until(count + 1).map { id ->
            api.getPokemon(id)
        }
    }
}
