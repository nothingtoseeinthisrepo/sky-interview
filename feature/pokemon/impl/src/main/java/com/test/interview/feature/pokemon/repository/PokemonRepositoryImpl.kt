package com.test.interview.feature.pokemon.repository

import com.test.interview.backend.pokemon.PokemonNetworkDataSource
import com.test.interview.database.pokemon.PokemonDatabaseDataSource
import com.test.interview.feature.pokemon.model.Pokemon
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject
import javax.inject.Singleton

internal class PokemonRepositoryImpl @Inject constructor(
    private val pokemonNetworkDataSource: PokemonNetworkDataSource,
    private val pokemonDatabaseDataSource: PokemonDatabaseDataSource,
): PokemonRepository{
    override fun get(count: Int): Flow<List<Pokemon>> {
        return emptyFlow()
    }

    override fun get(pokemonId: String): Flow<Pokemon> {
        return emptyFlow()
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PokemonRepositoryModule {
    @Singleton
    @Binds
    abstract fun provides(impl: PokemonRepositoryImpl): PokemonRepository
}