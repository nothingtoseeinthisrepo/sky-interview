package com.test.interview.database.pokemon.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.test.interview.database.pokemon.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Query(
        """
        SELECT *
        FROM pokemon
        ORDER BY pokemon.number DESC
        LIMIT 2
    """,
    )
    fun observePokemons(): Flow<List<PokemonEntity>>

    @Query(
        """
        SELECT *
        FROM pokemon
        WHERE pokemon.id = :pokemonId
    """,
    )
    fun observePokemon(pokemonId: String): Flow<PokemonEntity>

    @Upsert
    suspend fun upsertPokemon(pokemon: List<PokemonEntity>)
}
