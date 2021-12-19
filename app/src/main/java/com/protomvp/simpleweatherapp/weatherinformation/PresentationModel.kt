package com.protomvp.simpleweatherapp.weatherinformation

import com.protomvp.simpleweatherapp.domain.weatherinformation.model.CityWeatherInformation

@JvmInline
value class FavouritePlace(val city: String)

data class PresentationModel(
    val cityWeatherInformation: CityWeatherInformation? = null,
    val favouritePlaces: List<FavouritePlace> = emptyList()
)