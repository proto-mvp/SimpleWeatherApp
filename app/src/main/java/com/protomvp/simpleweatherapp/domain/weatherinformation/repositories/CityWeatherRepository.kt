package com.protomvp.simpleweatherapp.domain.weatherinformation.repositories

import com.protomvp.simpleweatherapp.common.domain.Repository
import com.protomvp.simpleweatherapp.common.domain.RepositoryResult
import com.protomvp.simpleweatherapp.common.domain.repositoryResult
import com.protomvp.simpleweatherapp.domain.weatherinformation.model.CityWeatherInformation
import com.protomvp.simpleweatherapp.domain.weatherinformation.model.mappers.WeatherResponseMapper
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource.CurrentWeatherService
import javax.inject.Inject

class CityWeatherRepository @Inject constructor(
    private val currentWeatherService: CurrentWeatherService,
    private val weatherResponseMapper: WeatherResponseMapper

) : Repository {

    suspend fun getWeather(city: String): RepositoryResult<CityWeatherInformation> =
        repositoryResult(mapper = { weatherResponseMapper(it) }) {
            currentWeatherService.getWeatherForCity(city)
        }
}