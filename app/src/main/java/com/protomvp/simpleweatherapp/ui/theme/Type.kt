package com.protomvp.simpleweatherapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.protomvp.simpleweatherapp.R


val weatherFontFamily =
    FontFamily(Font(R.font.avenir_heavy, FontWeight.Bold), Font(R.font.avenir_medium))

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = weatherFontFamily,
    body1 = TextStyle(
        fontFamily = weatherFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp),
    subtitle1 = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 12.sp),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)