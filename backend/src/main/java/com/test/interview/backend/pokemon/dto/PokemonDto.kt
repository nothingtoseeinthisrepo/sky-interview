package com.test.interview.backend.pokemon.dto

import com.test.interview.backend.core.dto.NamedApiResourceDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDto(
    val id: Int,
    val name: String,
    val sprites: PokemonSpritesDto,
    val weight: Int,
    val height: Int,
    val stats: List<Stat>,
    val types: List<Type>,
) {
    @Serializable
    data class PokemonSpritesDto(
        val other: Other,
    ) {
        @Serializable
        data class Other(
            @SerialName(value = "official-artwork") val officialArtwork: OfficialArtwork,
        ) {
            @Serializable
            data class OfficialArtwork(
                val frontDefault: String,
                val frontShiny: String,
            )
        }
    }

    @Serializable
    data class Stat(
        val baseStat: Int,
        val effort: Int,
        val stat: NamedApiResourceDto,
    )

    @Serializable
    data class Type(
        val slot: Int,
        val type: NamedApiResourceDto,
    )
}