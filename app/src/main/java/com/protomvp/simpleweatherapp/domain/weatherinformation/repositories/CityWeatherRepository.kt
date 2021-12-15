package com.protomvp.simpleweatherapp.domain.weatherinformation.repositories

import com.protomvp.simpleweatherapp.common.domain.Repository
import com.protomvp.simpleweatherapp.common.domain.RepositoryResult
import com.protomvp.simpleweatherapp.common.domain.repositoryResult
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource.CurrentWeatherService
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource.models.WeatherResponse
import javax.inject.Inject

class CityWeatherRepository @Inject constructor(
    private val currentWeatherService: CurrentWeatherService
) : Repository {

    suspend fun getWeather(city: String): RepositoryResult<WeatherResponse> = repositoryResult {
        currentWeatherService.getWeatherForCity(city)
    }
}