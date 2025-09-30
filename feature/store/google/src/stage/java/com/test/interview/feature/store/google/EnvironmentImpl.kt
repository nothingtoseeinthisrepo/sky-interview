package com.test.interview.feature.store.google

import com.test.interview.feature.store.Environment
import com.test.interview.feature.store.Store
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.Instant
import javax.inject.Singleton

internal object EnvironmentImpl : Environment {
    override val name: String
        get() = "Stage"
}

@Module
@InstallIn(SingletonComponent::class)
internal object EnvironmentModule {
    @Provides
    @Singleton
    fun provides(): Environment = EnvironmentImpl
}
