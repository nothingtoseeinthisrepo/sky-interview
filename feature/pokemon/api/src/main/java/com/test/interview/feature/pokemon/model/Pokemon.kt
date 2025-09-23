package com.test.interview.feature.pokemon.model

data class Pokemon(
    val id: String,
    val number: Int?,
    val name: String,
    val imageUrl: String?,
    val stats: Stats?,
    val weight: Float?,
    val height: Int?,
    val types: List<Type>,
) {
    enum class Type {
        Normal, Fire, Fighting, Water, Flying, Grass, Poison, Electric, Ground, Psychic, Rock, Ice,
        Bug, Dragon, Ghost, Dark, Steel, Fairy, Shadow, Unknown
    }

    data class Stats(
        val hpStat: Int?,
        val attackStat: Int?,
        val defenseStat: Int?,
        val specialAttackStat: Int?,
        val specialDefenseStat: Int?,
        val speedStat: Int?,
    )
}