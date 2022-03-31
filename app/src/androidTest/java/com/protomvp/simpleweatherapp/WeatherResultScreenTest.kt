package com.protomvp.simpleweatherapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherResultScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_Introduction() {
        weatherScreenRobot(composeTestRule) {
            loadForIntroduction()
            verifyIntroduction()
        }
    }

    @Test
    fun test_ErrorResults() {
        weatherScreenRobot(composeTestRule) {
            loadErrorResult()
            verifyErrorResults()
        }
    }
}