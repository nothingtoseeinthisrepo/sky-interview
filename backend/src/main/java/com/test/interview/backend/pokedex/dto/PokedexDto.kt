package com.test.intreview.backend.pokedex.dto

import com.test.interview.backend.core.dto.DescriptionDto
import com.test.interview.backend.core.dto.NamedApiResourceDto
import kotlinx.serialization.Serializable

@Serializable
data class PokedexDto(
    val id: Int,
    val name: String,
    val descriptions: List<DescriptionDto>,
    val pokemonEntries: List<PokemonEntryDto>,
) {
    @Serializable
    data class PokemonEntryDto(
        val entryNumber: Int,
        val pokemonSpecies: NamedApiResourceDto
    )
}