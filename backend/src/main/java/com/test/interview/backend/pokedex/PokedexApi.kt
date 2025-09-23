package com.test.interview.backend.pokedex

import com.test.interview.backend.core.dto.NamedApiResourceDto
import com.test.interview.backend.core.dto.PageDto
import com.test.intreview.backend.pokedex.dto.PokedexDto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

interface PokedexApi {
    @GET("pokedex/{name}")
    suspend fun getPokedex(@Path("name") name: String): PokedexDto
}

@Module
@InstallIn(SingletonComponent::class)
internal object PokedexApiModule {
    @Singleton
    @Provides
    fun provide(retrofit: Retrofit): PokedexApi = retrofit.create()
}