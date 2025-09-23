package com.test.interview.database

import android.content.Context
import androidx.room.Room
import com.test.interview.database.pokemon.dao.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DatabaseModule {

    companion object {
        @Singleton
        @Provides
        fun provideDatabase(
            @ApplicationContext context: Context,
            listTypeConverter: ListTypeConverter
        ): Database {
            val databaseBuilder = Room.databaseBuilder(
                context = context,
                klass = Database::class.java,
                name = "interview-pokemon-db"
            )

            databaseBuilder.addTypeConverter(listTypeConverter)

            return databaseBuilder.fallbackToDestructiveMigration(true).build()
        }

        @Singleton
        @Provides
        fun providePokedexDao(database: Database): PokemonDao = database.pokedexDao()
    }
}