package com.protomvp.simpleweatherapp.domain.weatherinformation

import com.protomvp.simpleweatherapp.common.domain.UseCase
import com.protomvp.simpleweatherapp.common.domain.UseCaseResult
import com.protomvp.simpleweatherapp.common.domain.useCaseResult
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.CityWeatherRepository
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource.models.WeatherResponse
import javax.inject.Inject

class WeatherInformationUseCase @Inject constructor(
    private val cityWeatherRepository: CityWeatherRepository
) : UseCase {

    suspend fun execute(city: String): UseCaseResult<WeatherResponse> = useCaseResult {
        cityWeatherRepository.getWeather(city)
    }
}