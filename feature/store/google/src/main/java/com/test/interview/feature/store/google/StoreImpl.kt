package com.test.interview.feature.store.google

import com.test.interview.feature.store.Store
import com.test.interview.feature.store.StoreRating
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

internal class StoreImpl @Inject constructor(
    private val storeRating: StoreRating,
) : Store {
    override val name: String
        get() = "Google"
    override val rating: Int
        get() = storeRating.rating
}

@Module
@InstallIn(FragmentComponent::class)
internal object StoreModule {
    @Provides
    @Singleton
    fun provides(storeRating: StoreRating): Store = StoreImpl(storeRating)
}
