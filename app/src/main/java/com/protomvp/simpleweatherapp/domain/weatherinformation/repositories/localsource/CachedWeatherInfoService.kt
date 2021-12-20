package com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.localsource

import com.protomvp.simpleweatherapp.common.domain.Repository
import com.protomvp.simpleweatherapp.common.domain.RepositoryResult
import com.protomvp.simpleweatherapp.common.domain.repositoryStorageResult
import com.protomvp.simpleweatherapp.common.localpersistence.LocalStorage
import com.protomvp.simpleweatherapp.common.localpersistence.StorageResult
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource.models.WeatherResponse
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CachedWeatherInfoService @Inject constructor(private val localStorage: LocalStorage) :
    Repository {
    private companion object {
        const val KEY_DB = "CacheWeather"
    }

    suspend fun save(weatherResponse: WeatherResponse): RepositoryResult<Unit> =
        repositoryStorageResult {
            val json = Json.encodeToString(WeatherResponse.serializer(), weatherResponse)
            localStorage.persist(KEY_DB, json)
            StorageResult.Success(Unit)
        }

    suspend fun read(): RepositoryResult<WeatherResponse> {
        val resultJson = localStorage.getKey(KEY_DB)
        return if (resultJson.isEmpty())
            RepositoryResult.Fail.GenericError("no cache found")
        else {
            val result = Json.decodeFromString(WeatherResponse.serializer(), resultJson)
            RepositoryResult.Success(result)
        }
    }
}