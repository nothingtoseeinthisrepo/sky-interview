package com.test.interview.feature.pokemon.mapper

import com.test.interview.backend.pokemon.dto.PokemonDto
import com.test.interview.feature.pokemon.model.Pokemon
import com.test.interview.database.pokemon.entity.PokemonEntity

internal fun PokemonDto.toEntity(number: Int) = PokemonEntity(
    id = name,
    name = name.capitalizeWords(),
    number = number,
    imageUrl = sprites.other.officialArtwork.frontDefault,
    hpStat = stats.find { stat -> stat.stat.name == "hp" }?.baseStat,
    attackStat = stats.find { stat -> stat.stat.name == "attack" }?.baseStat,
    defenseStat = stats.find { stat -> stat.stat.name == "defense" }?.baseStat,
    specialAttackStat = stats.find { stat -> stat.stat.name == "special-attack" }?.baseStat,
    specialDefenseStat = stats.find { stat -> stat.stat.name == "special-defense" }?.baseStat,
    speedStat = stats.find { stat -> stat.stat.name == "speed" }?.baseStat,
    weight = weight / 10f,
    height = height * 10,
    types = types.map { it.type.name },
)

internal fun PokemonEntity.toDomain() = Pokemon(
    id = id,
    number = number,
    name = name,
    imageUrl = imageUrl,
    weight = weight,
    height = height,
    stats = Pokemon.Stats(
        hpStat = hpStat,
        attackStat = attackStat,
        defenseStat = defenseStat,
        specialAttackStat = specialAttackStat,
        specialDefenseStat = specialDefenseStat,
        speedStat = speedStat,
    ),
    types = types.map { type -> type.typeToDomain() },
)

private fun String.typeToDomain(): Pokemon.Type {
    return when (this) {
        "normal" -> Pokemon.Type.Normal
        "fighting" -> Pokemon.Type.Fighting
        "flying" -> Pokemon.Type.Flying
        "poison" -> Pokemon.Type.Poison
        "ground" -> Pokemon.Type.Ground
        "rock" -> Pokemon.Type.Rock
        "bug" -> Pokemon.Type.Bug
        "ghost" -> Pokemon.Type.Ghost
        "steel" -> Pokemon.Type.Steel
        "fire" -> Pokemon.Type.Fire
        "water" -> Pokemon.Type.Water
        "grass" -> Pokemon.Type.Grass
        "electric" -> Pokemon.Type.Electric
        "psychic" -> Pokemon.Type.Psychic
        "ice" -> Pokemon.Type.Ice
        "dragon" -> Pokemon.Type.Dragon
        "dark" -> Pokemon.Type.Dark
        "fairy" -> Pokemon.Type.Fairy
        "shadow" -> Pokemon.Type.Shadow
        "unknown" -> Pokemon.Type.Unknown
        else -> Pokemon.Type.Unknown
    }
}

private fun String.capitalizeWords(): String = split(" ").joinToString(" ") { word ->
    word.replaceFirstChar { it.titlecase() }
}