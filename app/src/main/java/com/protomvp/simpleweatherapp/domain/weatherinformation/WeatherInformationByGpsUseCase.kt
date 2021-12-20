package com.protomvp.simpleweatherapp.domain.weatherinformation

import com.protomvp.simpleweatherapp.common.domain.UseCase
import com.protomvp.simpleweatherapp.common.domain.UseCaseResult
import com.protomvp.simpleweatherapp.common.domain.useCaseResult
import com.protomvp.simpleweatherapp.domain.weatherinformation.model.CityWeatherInformation
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.CityWeatherRepository
import javax.inject.Inject

class WeatherInformationByGpsUseCase @Inject constructor(
    private val cityWeatherRepository: CityWeatherRepository
) : UseCase {

    suspend fun execute(lat: String, lon: String): UseCaseResult<CityWeatherInformation> =
        useCaseResult {
            cityWeatherRepository.getWeather(lat, lon)
        }
}