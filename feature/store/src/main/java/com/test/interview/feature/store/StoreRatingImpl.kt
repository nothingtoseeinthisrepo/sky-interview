package com.test.interview.feature.store

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

internal class StoreRatingImpl @Inject constructor(
    private val store: Store,
): StoreRating {
    override val rating: Int
        get() = when (store.name.lowercase()) {
            "amazon" -> Random.nextInt(0, 100)
            "google" -> Random.nextInt(0, 100)
            else -> -1
        }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class StoreRatingModule {
    @Binds
    @Singleton
    abstract fun provides(impl: StoreRatingImpl): StoreRating
}
