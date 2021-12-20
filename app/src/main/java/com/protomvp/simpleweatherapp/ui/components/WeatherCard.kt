package com.protomvp.simpleweatherapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.protomvp.simpleweatherapp.domain.weatherinformation.model.CityWeatherInformation
import com.protomvp.simpleweatherapp.ui.theme.AppTheme
import com.protomvp.simpleweatherapp.ui.theme.Typography
import java.text.SimpleDateFormat
import java.util.*

val String.toUnits
    get() = "$this°C"

val Double.toUnits
    get() = "${this}°C"

fun Int.timeStampToDate(timeZoneDiff: Int = 0): String =
    Date((this + timeZoneDiff) * 1000L).run {
        SimpleDateFormat(" HH:mm:ss", Locale.getDefault()).format(this)
    }


@Composable
fun WeatherCard(information: CityWeatherInformation?, addFavourite: (String) -> Unit) {
    if (information == null)
        return
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.dimensions.paddingSmall),
        border = BorderStroke(AppTheme.dimensions.borderStrokeSize, Color.Blue)
    ) {
        Column(
            //verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(AppTheme.dimensions.paddingMedium)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = information.cityName + " , " + information.cityInformation.country,
                    style = Typography.h4
                )
                OutlinedButton(onClick = { addFavourite(information.cityName) }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Add to favourites")
                }
            }

            Divider(modifier = Modifier.padding(vertical = AppTheme.dimensions.paddingLarge))

            MainWeatherInfo(information)

            Divider(modifier = Modifier.padding(vertical = AppTheme.dimensions.paddingLarge))

            OtherWeatherDetails(information = information)
        }

    }
}

@Composable
private fun MainWeatherInfo(weatherResponse: CityWeatherInformation) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            MinMaxTemperature(weatherResponse)
            Text(
                text = weatherResponse.mainInfo.temp.toUnits, style = Typography.h3,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Feels like " + weatherResponse.mainInfo.feelsLike.toUnits,
                style = Typography.subtitle2,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            WeatherVisual(code = weatherResponse.weather.first().iconUrl)
            Text(text = weatherResponse.weather.first().main, style = Typography.h5)
        }
    }
}

@Composable
private fun MinMaxTemperature(weatherResponse: CityWeatherInformation) {
    Row {
        Icon(
            Icons.Filled.KeyboardArrowUp,
            contentDescription = null
        )
        Text(
            text = "Max " + weatherResponse.mainInfo.tempMax.toUnits,
            style = Typography.subtitle2
        )
        Spacer(modifier = Modifier.width(AppTheme.dimensions.paddingMedium))
        Icon(
            Icons.Filled.KeyboardArrowDown,
            contentDescription = null
        )
        Text(
            text = "Min " + weatherResponse.mainInfo.tempMin.toUnits,
            style = Typography.subtitle2
        )
    }
}

@Composable
fun SearchForCity(searchForCityAction: (String) -> Unit) {
    var inputText by remember { mutableStateOf("") }

    DetailsRow {
        OutlinedTextField(
            inputText,
            onValueChange = { inputText = it },
            label = { Text(text = "Search for a city's weather") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = {
                    if (inputText.isNotEmpty()) {
                        searchForCityAction(inputText)
                    }
                }) {
                    Icon(Icons.Outlined.Search, null)
                }
            }
        )
    }
}