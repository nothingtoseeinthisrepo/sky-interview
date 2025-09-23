package com.test.interview.database.pokemon

import com.test.interview.database.pokemon.dao.PokemonDao
import com.test.interview.database.pokemon.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class PokemonDatabaseDataSource @Inject constructor(
    private val pokemonDao: PokemonDao,
) {

    fun observePokemons(): Flow<List<PokemonEntity>> =
        pokemonDao.observePokemons()
            .distinctUntilChanged()

    fun observePokemon(pokemonId: String): Flow<PokemonEntity> =
        pokemonDao.observePokemon(pokemonId)
            .distinctUntilChanged()

    suspend fun upsertPokemon(pokemon: List<PokemonEntity>) = pokemonDao.upsertPokemon(pokemon)
}