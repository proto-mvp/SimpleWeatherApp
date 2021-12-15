package com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource

import javax.inject.Inject


interface ConfigurationProvider<T> {
    fun provide(): T
}

typealias ConfigurationStringProvider = ConfigurationProvider<String>


class WeatherApiKeyProvider @Inject constructor() : ConfigurationStringProvider {
    override fun provide() = "bbf447cd77a7208eb8d7d83cf57b8ae5"
}

class ApiUrlProvider @Inject constructor() : ConfigurationStringProvider {
    override fun provide() = "https://api.openweathermap.org/data/2.5/weather/"
}