package com.protomvp.simpleweatherapp.domain.weatherinformation.repositories

import com.protomvp.simpleweatherapp.common.domain.Repository
import com.protomvp.simpleweatherapp.common.domain.RepositoryResult
import com.protomvp.simpleweatherapp.common.domain.repositoryResult
import com.protomvp.simpleweatherapp.common.network.ApiResult
import com.protomvp.simpleweatherapp.common.network.noNetwork
import com.protomvp.simpleweatherapp.domain.weatherinformation.model.CityWeatherInformation
import com.protomvp.simpleweatherapp.domain.weatherinformation.model.mappers.WeatherResponseMapper
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.localsource.CachedWeatherInfoService
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource.CurrentWeatherService
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource.models.WeatherResponse
import javax.inject.Inject

class CityWeatherRepository @Inject constructor(
    private val currentWeatherService: CurrentWeatherService,
    private val weatherResponseMapper: WeatherResponseMapper,
    private val cachedWeatherInfoService: CachedWeatherInfoService
) : Repository {

    suspend fun getWeather(city: String): RepositoryResult<CityWeatherInformation> =
        repositoryResult(mapper = { weatherResponseMapper(it) }) {

            var result = currentWeatherService.getWeatherForCity(city)
            when (result) {
                is ApiResult.Fail -> {
                    result.noNetwork {
                        when (val cachedResult = cachedWeatherInfoService.read()) {
                            is RepositoryResult.Success -> result =
                                ApiResult.Success(cachedResult.value, 201)
                        }
                    }
                }
                is ApiResult.Success -> cachedWeatherInfoService.save((result as ApiResult.Success<WeatherResponse>).value)
            }
            return@repositoryResult result
        }
}