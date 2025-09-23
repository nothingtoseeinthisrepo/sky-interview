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

internal class GetPokemonImpl @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) : GetPokemon {
    override fun invoke(pokemonId: String): Flow<Pokemon> {
        return pokemonRepository.get(pokemonId)
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class GetPokemonModule {
    @Singleton
    @Binds
    abstract fun provide(impl: GetPokemonImpl): GetPokemon
}
