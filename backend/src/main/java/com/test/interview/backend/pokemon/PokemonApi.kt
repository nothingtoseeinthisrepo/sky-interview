package com.test.interview.backend.pokemon

import com.test.interview.backend.pokemon.dto.PokemonDto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

interface PokemonApi {
    @GET("pokemon/{name}")
    suspend fun getPokemon(
        @Path("name") name: String,
    ): PokemonDto
}

@Module
@InstallIn(SingletonComponent::class)
internal object PokemonApiModule {
    @Singleton
    @Provides
    fun provide(retrofit: Retrofit): PokemonApi = retrofit.create()
}