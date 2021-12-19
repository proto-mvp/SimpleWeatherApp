package com.protomvp.simpleweatherapp.ui.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.protomvp.simpleweatherapp.ui.components.FavouriteCities
import com.protomvp.simpleweatherapp.ui.components.SearchForCity
import com.protomvp.simpleweatherapp.ui.components.ShowAppMessage
import com.protomvp.simpleweatherapp.ui.components.WeatherCard
import com.protomvp.simpleweatherapp.weatherinformation.WeatherInformationSideEffect
import com.protomvp.simpleweatherapp.weatherinformation.WeatherInformationState

@Composable
fun WeatherResultsScreen(
    state: WeatherInformationState,
    sideEffect: WeatherInformationSideEffect,
    sendAction: (String) -> Unit,
    addFavouriteAction: (String) -> Unit,
    removeFavouriteAction: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(ScrollState(0)),
    ) {

        SearchForCity(searchForCityAction = { sendAction(it) })

        Spacer(modifier = Modifier.height(24.dp))

        if (state is WeatherInformationState.ScreenInfo) {
//            listOf(
//                "London",
//                "Paris",
//                "Athens",
//                "Budapest",
//                "London",
//                "Paris",
//                "Athens",
//                "Budapest"
//            )
            FavouriteCities(
                cities = state.presentationModel.favouritePlaces,
                onChooseCity = { sendAction(it) },
                onRemoveCity = { removeFavouriteAction(it) })

        }
        Spacer(modifier = Modifier.height(24.dp))

        when (state) {
            WeatherInformationState.FailedGettingResults -> Text(text = "Something went wrong")
            is WeatherInformationState.NewWeatherInfo -> WeatherCard(information = state.weatherResponse,
                {})
            WeatherInformationState.Introduction -> Text(text = "Type a city name")
            is WeatherInformationState.ScreenInfo -> WeatherCard(
                information = state.presentationModel.cityWeatherInformation,
                addFavourite = { addFavouriteAction(it) })
        }
        ShowAppMessage(sideEffect)
    }
}