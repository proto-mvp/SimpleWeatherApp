package com.protomvp.simpleweatherapp.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.protomvp.simpleweatherapp.ui.components.WeatherCard
import com.protomvp.simpleweatherapp.weatherinformation.WeatherInformationState

@Composable
fun WeatherResultsScreen(state: WeatherInformationState) {
    when (state) {
        WeatherInformationState.FailedGettingResults -> Text(text = "Something went wrong")
        is WeatherInformationState.NewWeatherInfo -> WeatherCard(weatherResponse = state.weatherResponse)
        WeatherInformationState.Introduction -> Text(text = "Type a city name")
    }
}