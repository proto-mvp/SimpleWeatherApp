package com.protomvp.simpleweatherapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.protomvp.simpleweatherapp.ui.theme.AppTheme
import com.protomvp.simpleweatherapp.weatherinformation.FavouritePlace

@Composable
fun FavouriteCities(
    cities: List<FavouritePlace>,
    onChooseCity: (String) -> Unit,
    onRemoveCity: (String) -> Unit
) {
    if (cities.isEmpty()) {
        return
    } else
        LazyRow() {
            items(cities) { item: FavouritePlace ->
                Card(
                    backgroundColor = Color.LightGray,
                    border = BorderStroke(AppTheme.dimensions.borderStrokeSize, Color.LightGray),
                    elevation = 3.dp,
                    modifier = Modifier
                        .height(60.dp)
                        .width(100.dp)
                        .padding(5.dp)
                        .clickable(true) {
                            onChooseCity(item.city)
                        }) {
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.padding(AppTheme.dimensions.paddingSmaller)
                    ) {
                        Text(text = item.city)

                        Box(
                            modifier = Modifier
                                .align(Alignment.End)
                                .clickable(true) {
                                    onRemoveCity(item.city)
                                }
                                .size(
                                    AppTheme.dimensions.sizeSquare,
                                    AppTheme.dimensions.sizeSquare
                                )
                                .clip(RoundedCornerShape(AppTheme.dimensions.paddingSmall))
                                .background(Color.Red)
                        ) {
                            Icon(
                                Icons.Filled.Delete,
                                modifier = Modifier.align(Alignment.Center),
                                tint = Color.White,
                                contentDescription = "delete"
                            )
                        }
                    }
                }
            }
        }
}