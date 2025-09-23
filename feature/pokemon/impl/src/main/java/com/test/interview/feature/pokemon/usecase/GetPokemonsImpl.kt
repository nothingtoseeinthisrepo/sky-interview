package com.test.interview.feature.pokemon.usecase

import com.test.interview.feature.pokemon.model.Pokemon
import com.test.interview.feature.pokemon.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

internal class GetPokemonsImpl @Inject constructor(
    private val pokemonRepository: PokemonRepository
): GetPokemons {
    override fun invoke(count: Int): Flow<List<Pokemon>> {
        return pokemonRepository.get(count)
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class GetPokedexModule {
    @Singleton
    @Binds
    abstract fun provide(impl: GetPokemonsImpl): GetPokemons
}
