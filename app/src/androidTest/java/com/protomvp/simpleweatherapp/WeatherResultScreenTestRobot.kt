package com.protomvp.simpleweatherapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithText
import com.protomvp.simpleweatherapp.ui.screens.WeatherResultsScreen
import com.protomvp.simpleweatherapp.weatherinformation.WeatherInformationSideEffect
import com.protomvp.simpleweatherapp.weatherinformation.WeatherInformationState

interface WeatherResultScreenTestRobot {
    fun loadForIntroduction()

    fun loadErrorResult()

    fun verifyIntroduction()

    fun verifyErrorResults()
}

class WeatherResultScreenTestRobotInteractor(private val composeTestRule: ComposeContentTestRule) :
    WeatherResultScreenTestRobot {
    override fun loadForIntroduction() {
        composeTestRule.setContent {
            WeatherResultsScreen(
                state = WeatherInformationState.Introduction,
                sideEffect = WeatherInformationSideEffect.Loading,
                sendAction = {},
                addFavouriteAction = {},
                removeFavouriteAction = {}
            )
        }
    }

    override fun loadErrorResult() {
        composeTestRule.setContent {
            WeatherResultsScreen(
                state = WeatherInformationState.FailedGettingResults,
                sideEffect = WeatherInformationSideEffect.FailedGettingResults,
                sendAction = {},
                addFavouriteAction = {},
                removeFavouriteAction = {}
            )
        }
    }

    override fun verifyIntroduction() {
        composeTestRule.onNodeWithText("Type a city name")
            .assertIsDisplayed()
    }

    override fun verifyErrorResults() {
        composeTestRule.onNodeWithText("Something went wrong getting results")
            .assertIsDisplayed()
    }
}

fun weatherScreenRobot(
    composeTestRule: ComposeContentTestRule,
    block: WeatherResultScreenTestRobot.() -> Unit
) {
    val instance = WeatherResultScreenTestRobotInteractor(composeTestRule)
    instance.block()
}