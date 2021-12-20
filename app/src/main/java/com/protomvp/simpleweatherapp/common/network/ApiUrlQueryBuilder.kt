package com.protomvp.simpleweatherapp.common.network

import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource.WeatherApiKeyProvider
import javax.inject.Inject

enum class Units(val key: String) {
    IMPERIAL("imperial"),
    METRIC("metric"),
    STANDARD("standard")
}

class ApiUrlQueryBuilder @Inject constructor(
    private val apiKeyProvider: WeatherApiKeyProvider
) {
    fun createCityUrl(
        city: String,
        units: Units = Units.METRIC
    ): String =
        "?q=$city&units=${units.key}&appid=${apiKeyProvider.provide()}"

    fun createLatLngUrl(lat: String, lon: String, units: Units = Units.METRIC): String =
        "?lat=${lat}&lon=${lon}&units=${units.key}&appid=${apiKeyProvider.provide()}"
}
