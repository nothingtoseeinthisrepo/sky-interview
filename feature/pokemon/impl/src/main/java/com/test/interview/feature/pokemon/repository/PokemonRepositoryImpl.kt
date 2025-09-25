package com.test.interview.feature.pokemon.repository

import com.test.interview.backend.pokemon.PokemonNetworkDataSource
import com.test.interview.database.pokemon.PokemonDatabaseDataSource
import com.test.interview.feature.pokemon.mapper.toDomain
import com.test.interview.feature.pokemon.mapper.toEntity
import com.test.interview.feature.pokemon.model.Pokemon
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

internal class PokemonRepositoryImpl @Inject constructor(
    private val pokemonNetworkDataSource: PokemonNetworkDataSource,
    private val pokemonDatabaseDataSource: PokemonDatabaseDataSource,
) : PokemonRepository {
    override fun get(count: Int): Flow<List<Pokemon>> {
        return flow {

            val pokemons = pokemonNetworkDataSource.getPokemon(count)
            val pokemonEntities = pokemons.map { it.toEntity() }
            pokemonDatabaseDataSource.upsertPokemon(pokemonEntities)

            emit(pokemonEntities.map { it.toDomain() })
        }
    }

    override fun getDetail(pokemonId: Int): Flow<Pokemon> {
        return flow { emit(pokemonNetworkDataSource.getPokemonDetail(pokemonId).toEntity().toDomain()) }
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PokemonRepositoryModule {
    @Singleton
    @Binds
    abstract fun provides(impl: PokemonRepositoryImpl): PokemonRepository
}
