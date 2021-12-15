package com.protomvp.simpleweatherapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource.models.WeatherResponse

@Composable
fun WeatherCard(weatherResponse: WeatherResponse) {
    Card {
        Column {
            Text(text = weatherResponse.main.temp.toString())
        }
    }
}