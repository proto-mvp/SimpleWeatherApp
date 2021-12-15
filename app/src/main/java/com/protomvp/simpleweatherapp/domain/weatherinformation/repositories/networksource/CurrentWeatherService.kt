package com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource

import com.protomvp.simpleweatherapp.common.network.ApiResult
import com.protomvp.simpleweatherapp.common.network.ApiUrlQueryBuilder
import com.protomvp.simpleweatherapp.common.network.NetworkClient
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource.models.WeatherResponse
import javax.inject.Inject

class CurrentWeatherService @Inject constructor(
    private val networkClient: NetworkClient,
    private val apiUrlQueryBuilder: ApiUrlQueryBuilder
) {
    suspend fun getWeatherForCity(city: String): ApiResult<WeatherResponse> =
        networkClient.getRequest(apiUrlQueryBuilder.createCityUrl(city))
}
