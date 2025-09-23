package com.test.interview.backend.offline

import android.content.Context
import com.test.interview.backend.offline.OfflineApiConfig.ENABLED
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.time.Duration
import javax.inject.Inject
import javax.inject.Qualifier
import kotlin.random.Random

internal class OfflineApiInterceptor @Inject constructor(
    @param:ApplicationContext private val appContext: Context,
    @param:OfflineApiEnabled private val useOfflineApi: Boolean,
    @param:OfflineApiMinDelay private val minDelay: Duration,
    @param:OfflineApiMaxDelay private val maxDelay: Duration,
) : Interceptor {

    private val jsonMedia = "application/json; charset=utf-8".toMediaType()
    val errorJson = """{ "error": "Entry Not Found" }"""

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!useOfflineApi) return chain.proceed(request)

        val url = request.url
        // Only intercept GET https://pokeapi.co/api/v2/pokemon/{name}
        val isPokeHost = url.host == "pokeapi.co"
        val segments = url.encodedPathSegments
        val matchesPokemonEndpoint = segments.size == 4 &&
                segments[0] == "api" &&
                segments[1] == "v2" &&
                segments[2] == "pokemon"

        if (isPokeHost && matchesPokemonEndpoint && request.method == "GET") {
            val name = segments[3].lowercase()
            val assetPath = "pokeapi/pokemon/$name.json"
            return try {
                Thread.sleep(Random.nextLong(minDelay.toMillis(), maxDelay.toMillis()))

                val json = appContext.assets.open(assetPath)
                    .bufferedReader()
                    .use { it.readText() }

                Response.Builder()
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .code(200)
                    .message("OK")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Cache-Control", "public, max-age=86400")
                    .body(json.toByteArray().toResponseBody(jsonMedia))
                    .build()

            } catch (_: Throwable) {
                Response.Builder()
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .code(404)
                    .message("Not Found")
                    .addHeader("Content-Type", "application/json")
                    .body(errorJson.toByteArray().toResponseBody(jsonMedia))
                    .build()
            }
        }

        // Everything else: normal network
        return chain.proceed(request)
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class OfflineApiInterceptorModule {

    companion object {

        @Provides
        @OfflineApiMinDelay
        fun provideOfflineApiMinDelay(): Duration = Duration.ofMillis(100)

        @Provides
        @OfflineApiMaxDelay
        fun provideOfflineApiMaxDelay(): Duration = Duration.ofMillis(700)

        @Provides
        @OfflineApiEnabled
        fun provideOfflineApiEnabled(): Boolean = ENABLED
    }

    @Binds
    @IntoSet
    abstract fun bing(impl: OfflineApiInterceptor): Interceptor
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OfflineApiMinDelay

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OfflineApiMaxDelay

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OfflineApiEnabled
