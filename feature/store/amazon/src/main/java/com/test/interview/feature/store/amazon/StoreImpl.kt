package com.test.interview.feature.store.amazon

import com.test.interview.feature.store.StoreRating
import com.test.interview.feature.store.Store
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

internal class StoreImpl @Inject constructor(
    private val storeRating: StoreRating,
) : Store {
    override val name: String
        get() = "Amazon"
    override val rating: Int
        get() = storeRating.rating
}

@Module
@InstallIn(SingletonComponent::class)
internal object StoreModule {
    @Provides
    @Singleton
    fun provides(storeRating: StoreRating): Store = StoreImpl(storeRating)
}
