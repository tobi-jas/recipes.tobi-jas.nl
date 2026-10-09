package com.emilflach.recipes.data

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Platform-specific engine factory
expect fun createHttpClientEngine(): HttpClientEngineFactory<*>

object HttpClientProvider {
    const val BASE_URL = "https://recipes.tobi-jas.nl/api"

    // Common configuration for all platforms
    fun <T : HttpClientEngineConfig> configureClient(config: HttpClientConfig<T>) {
        with(config) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }

    // Common implementation using platform-specific engine
    fun createHttpClient(): HttpClient {
        return HttpClient(createHttpClientEngine()) {
            configureClient(this)
        }
    }
}
