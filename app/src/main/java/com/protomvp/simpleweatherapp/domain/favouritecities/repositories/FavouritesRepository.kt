package com.protomvp.simpleweatherapp.domain.favouritecities.repositories

import com.protomvp.simpleweatherapp.common.domain.Repository
import com.protomvp.simpleweatherapp.common.domain.RepositoryResult
import com.protomvp.simpleweatherapp.common.domain.repositoryStorageResult
import com.protomvp.simpleweatherapp.common.localpersistence.LocalStorage
import com.protomvp.simpleweatherapp.common.localpersistence.StorageResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import javax.inject.Inject

@Serializable
data class Favourites(
    @SerialName("places")
    val places: List<String>
)

class FavouritesRepository @Inject constructor(private val localStorage: LocalStorage) :
    Repository {
    private companion object {
        const val KEY_DB = "Favourites"
    }

    suspend fun add(newCity: String): RepositoryResult<Unit> = repositoryStorageResult {
        val resultJson = localStorage.getKey(KEY_DB)

        val altered = if (resultJson.isNotEmpty()) {
            val items = Json.decodeFromString(Favourites.serializer(), resultJson)
            if (items.places.contains(newCity)) {
                items.places
            } else {
                items.places.toMutableList().apply {
                    add(newCity)
                }
            }
        } else {
            listOf(newCity)
        }

        val json = Json.encodeToString(Favourites.serializer(), Favourites(altered))
        localStorage.persist(KEY_DB, json)
        StorageResult.Success(Unit)
    }

    suspend fun getCities(): RepositoryResult<List<String>> = repositoryStorageResult {
        val resultJson = localStorage.getKey(KEY_DB)

        val result = if (resultJson.isNotEmpty()) {
            Json.decodeFromString(Favourites.serializer(), resultJson)
        } else {
            Favourites(listOf())
        }
        StorageResult.Success(result.places)
    }

    suspend fun remove(city: String): RepositoryResult<Unit> = repositoryStorageResult {
        val resultJson = localStorage.getKey(KEY_DB)

        val altered = if (resultJson.isNotEmpty()) {
            val items = Json.decodeFromString(Favourites.serializer(), resultJson)
            items.places.toMutableList().apply {
                remove(city)
            }
        } else {
            listOf()
        }

        val json = Json.encodeToString(Favourites.serializer(), Favourites(altered))
        localStorage.persist(KEY_DB, json)
        StorageResult.Success(Unit)
    }
}