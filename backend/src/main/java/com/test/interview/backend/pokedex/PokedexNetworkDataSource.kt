package com.test.interview.backend.pokedex

import com.test.interview.backend.core.dto.NamedApiResourceDto
import com.test.interview.backend.core.dto.PageDto
import com.test.intreview.backend.pokedex.dto.PokedexDto
import javax.inject.Inject

class PokedexNetworkDataSource @Inject constructor(
    private val pokedexApi: PokedexApi,
) {
    suspend fun getPokedex(name: String): PokedexDto = pokedexApi.getPokedex(name)
}
