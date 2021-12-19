package com.protomvp.simpleweatherapp.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.protomvp.simpleweatherapp.domain.weatherinformation.model.CityWeatherInformation
import com.protomvp.simpleweatherapp.ui.theme.AppTheme
import com.protomvp.simpleweatherapp.ui.theme.Typography

@Composable
fun WeatherVisual(code: String, modifier: Modifier = Modifier) {
    Log.d("image url ", "http://openweathermap.org/img/wn/$code@2x.png")
    Image(
        painter = rememberImagePainter(
            data = "http://openweathermap.org/img/wn/$code@2x.png",
            builder = {
                transformations(CircleCropTransformation())
            }
        ),
        contentScale = ContentScale.Fit,
        contentDescription = null,
        modifier = modifier
            .size(AppTheme.dimensions.weatherImageSize)
            .border(
                AppTheme.dimensions.borderStrokeSizeLarger,
                Color.LightGray,
                RoundedCornerShape(AppTheme.dimensions.paddingSmall)
            )
            .padding(AppTheme.dimensions.paddingSmallest)
    )
}

@Composable
fun OtherWeatherDetails(information: CityWeatherInformation) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row {
            Icon(Icons.Outlined.Info, contentDescription = "")
            Text(text = "More details", style = Typography.body2)
        }
        DetailsSpacer()
        DetailsRow {
            DetailText(text = "Wind ${information.wind.speed} M/s")
            DetailText(
                text = "Pressure ${information.mainInfo.pressure} hPa"
            )
        }
        DetailsSpacer()
        DetailsRow {
            DetailText(text = "Clouds ${information.clouds.percentage}%")
            DetailText(text = "Visibility ${information.visibility}m")
        }
        DetailsSpacer()
        DetailsRow {
            DetailText(text = "Humidity ${information.mainInfo.humidity}%")
            DetailText(text = "Visibility ${information.visibility}m")
        }
        DetailsSpacer()
        DetailsRow {
            DetailText(
                text = "Sunrise at " + information.cityInformation.sunrise.timeStampToDate(
                    information.timeZoneDiff
                )
            )
            DetailText(
                text = "Sunset at " + information.cityInformation.sunset.timeStampToDate(
                    information.timeZoneDiff
                )
            )
        }
        DetailsSpacer()
        DetailText("Updated at : " + information.updateTime.timeStampToDate(information.timeZoneDiff))
    }
}

@Composable
fun DetailsRow(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

@Composable
fun DetailsSpacer() {
    Spacer(modifier = Modifier.padding(vertical = AppTheme.dimensions.paddingSmall))
}

@Composable
fun DetailText(text: String) {
    Text(text = text, style = Typography.subtitle1)
}