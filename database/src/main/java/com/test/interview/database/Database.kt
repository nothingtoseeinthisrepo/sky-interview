package com.test.interview.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.interview.database.pokemon.dao.PokemonDao
import com.test.interview.database.pokemon.entity.PokemonEntity

@Database(
    entities = [
        PokemonEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(ListTypeConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun pokedexDao(): PokemonDao
}