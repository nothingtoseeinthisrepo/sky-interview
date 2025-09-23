package com.test.interview.database.pokemon.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "pokemon",
)
data class PokemonEntity(
    @PrimaryKey val id: String,
    val name: String,
    val number: Int,
    val imageUrl: String,
    val hpStat: Int?,
    val attackStat: Int?,
    val defenseStat: Int?,
    val specialAttackStat: Int?,
    val specialDefenseStat: Int?,
    val speedStat: Int?,
    val weight: Float,
    val height: Int,
    val types: List<String>,
)
