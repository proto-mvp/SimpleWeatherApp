package com.protomvp.simpleweatherapp.common.network


import android.util.Log
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource.ApiUrlProvider
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import javax.inject.Inject

class NetworkClient @Inject constructor(
    private val apiUrlProvider: ApiUrlProvider,
) {
    fun instance(): HttpClient = HttpClient(CIO) {
        expectSuccess = false
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    isLenient = true
                    prettyPrint = true
                    ignoreUnknownKeys = true
                }
            )
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = logger()
        }
        defaultRequest {
            // we could add any header here if needed
        }
    }

    fun apiUrl(url: String) =
        apiUrlProvider.provide() + url


    suspend inline fun <reified T> getRequest(
        endPointUrl: String
    ): ApiResult<T> =
        instance().getRequest(apiUrl(endPointUrl))

    suspend inline fun <T : Any, reified R> postRequest(
        endPointUrl: String,
        value: T
    ): ApiResult<R> = instance().postRequest(apiUrl(endPointUrl), value)

    private fun logger(): Logger {
        return object : Logger {
            override fun log(message: String) {
                Log.d("SimpleDemoApp:Net:Logger", message)
            }
        }
    }
}