package com.test.interview.feature.pokemon.repository

import com.test.interview.backend.pokedex.PokedexNetworkDataSource
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

private const val Pokedex = "national"

internal class PokemonRepositoryImpl @Inject constructor(
    private val pokedexNetworkDataSource: PokedexNetworkDataSource,
    private val pokemonNetworkDataSource: PokemonNetworkDataSource,
    private val pokemonDatabaseDataSource: PokemonDatabaseDataSource,
): PokemonRepository{
    override fun get(count: Int): Flow<List<Pokemon>> {
        return flow {
            val pokedex = pokedexNetworkDataSource.getPokedex(Pokedex)
            val pokemons = pokedex.pokemonEntries.take(count).map { pekemonEntry ->
                pokemonNetworkDataSource.getPokemon(pekemonEntry.pokemonSpecies.name)
            }

            pokemonDatabaseDataSource.upsertPokemon(
                pokemons.map {
                    it.toEntity(pokedex.pokemonEntries.find { pokemonEntry ->
                        pokemonEntry.pokemonSpecies.name == it.name
                    }?.entryNumber ?: 0)
                }
            )

            emit(
                pokemons.map {
                    it.toEntity(pokedex.pokemonEntries.find { pokemonEntry ->
                        pokemonEntry.pokemonSpecies.name == it.name
                    }?.entryNumber ?: 0).toDomain()
                }
            )
        }
    }

    override fun get(pokemonId: String): Flow<Pokemon> {
        return flow { emit(pokemonNetworkDataSource.getPokemon(pokemonId).toEntity(0).toDomain()) }
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PokemonRepositoryModule {
    @Singleton
    @Binds
    abstract fun provides(impl: PokemonRepositoryImpl): PokemonRepository
}