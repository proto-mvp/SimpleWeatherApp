package com.protomvp.simpleweatherapp.domain.weatherinformation.model


@JvmInline
value class Cloudiness(val percentage: Int)

data class CityWeatherInformation(
    val coordinates: Coordinates,

    val weather: List<WeatherDescription>,

    val mainInfo: WeatherInformation,

    val visibility: Int,
    val wind: Wind,
    val clouds: Cloudiness,

    val updateTime: Int,
    val cityInformation: CityInformation,
    val cityName: String,
    val timeZoneDiff: Int
) {
    data class Coordinates(
        val lon: Double,
        val lat: Double
    )

    data class WeatherDescription(
        val main: String,
        val description: String,
        val iconUrl: String
    )

    data class WeatherInformation(
        val temp: Double,
        val pressure: Int,
        val humidity: Int,
        val tempMin: Double,
        val tempMax: Double,
        val feelsLike: Double
    )

    data class Wind(
        val speed: Double,
        val deg: Int
    )

    data class CityInformation(
        val country: String,
        val sunrise: Int,
        val sunset: Int
    )
}
