package com.protomvp.simpleweatherapp.weatherinformation

import com.protomvp.simpleweatherapp.common.presentation.SideEffect
import com.protomvp.simpleweatherapp.common.presentation.ViewModelIntent
import com.protomvp.simpleweatherapp.common.presentation.ViewModelState
import com.protomvp.simpleweatherapp.domain.weatherinformation.model.CityWeatherInformation

sealed class WeatherInformationState : ViewModelState {
    object Introduction : WeatherInformationState()
    data class NewWeatherInfo(val weatherResponse: CityWeatherInformation) :
        WeatherInformationState()

    data class ScreenInfo(val presentationModel: PresentationModel) : WeatherInformationState()

    object FailedGettingResults : WeatherInformationState()
    object FailedOnFavouritesAction : WeatherInformationState()
}

sealed class WeatherInformationIntent : ViewModelIntent {
    class NewCityRequest(val query: String) : WeatherInformationIntent()
    class RemoveCityFavourite(val cityName: String) : WeatherInformationIntent()
    class AddCityFavourite(val cityName: String) : WeatherInformationIntent()
}

sealed class WeatherInformationSideEffect : SideEffect {
    object Loading : WeatherInformationSideEffect()
    object FailedGettingFavourites : WeatherInformationSideEffect()
    object FailedGettingResults : WeatherInformationSideEffect()

}