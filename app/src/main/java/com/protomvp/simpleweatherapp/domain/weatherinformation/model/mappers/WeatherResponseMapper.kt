package com.protomvp.simpleweatherapp.domain.weatherinformation.model.mappers

import com.protomvp.simpleweatherapp.domain.weatherinformation.model.CityWeatherInformation
import com.protomvp.simpleweatherapp.domain.weatherinformation.model.Cloudiness
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource.models.WeatherResponse
import javax.inject.Inject

class WeatherResponseMapper @Inject constructor() : (WeatherResponse) -> CityWeatherInformation {
    override fun invoke(response: WeatherResponse): CityWeatherInformation {
        return CityWeatherInformation(
            CityWeatherInformation.Coordinates(
                response.coord.lon,
                response.coord.lat
            ),
            weather = response.weather.map {
                CityWeatherInformation.WeatherDescription(
                    main = it.main,
                    description = it.description,
                    iconUrl = it.icon
                )
            },
            mainInfo = CityWeatherInformation.WeatherInformation(
                temp = response.main.temp,
                pressure = response.main.pressure,
                humidity = response.main.humidity,
                tempMin = response.main.tempMin,
                tempMax = response.main.tempMax,
                feelsLike = response.main.feelsLike
            ),
            visibility = response.visibility,
            wind = CityWeatherInformation.Wind(response.wind.speed, response.wind.deg),
            clouds = Cloudiness(response.clouds.all),
            cityInformation = CityWeatherInformation.CityInformation(
                response.sys.country,
                response.sys.sunrise,
                response.sys.sunset
            ),
            updateTime = response.dt,
            cityName = response.name,
            timeZoneDiff = response.timeZone
        )
    }
}