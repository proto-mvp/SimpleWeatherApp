package com.protomvp.simpleweatherapp.ui.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.protomvp.simpleweatherapp.weatherinformation.WeatherInformationSideEffect


@Composable
fun ShowAppMessage(sideEffect: WeatherInformationSideEffect) {
    val context = LocalContext.current

    fun showToast(message: String) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    when (sideEffect) {
        WeatherInformationSideEffect.FailedGettingFavourites -> showToast("Please try again")
        WeatherInformationSideEffect.FailedGettingResults -> showToast("No Results found.Please try again")
        WeatherInformationSideEffect.Loading -> showToast("Loading...")
    }
}