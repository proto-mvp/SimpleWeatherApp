package com.protomvp.simpleweatherapp.domain.weatherinformation.repositories

import com.protomvp.simpleweatherapp.common.domain.RepositoryResult
import com.protomvp.simpleweatherapp.common.network.ApiResult
import com.protomvp.simpleweatherapp.domain.weatherinformation.model.CityWeatherInformation
import com.protomvp.simpleweatherapp.domain.weatherinformation.model.mappers.WeatherResponseMapper
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource.CurrentWeatherService
import io.mockk.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA

class CityWeatherRepositoryTest {
    private lateinit var subject: CityWeatherRepository
    private val currentWeatherService: CurrentWeatherService = mockk()
    private val weatherResponseMapper: WeatherResponseMapper = mockk()

    @Before
    fun setUp() {
        subject = CityWeatherRepository(currentWeatherService, weatherResponseMapper)
    }

    private val input = "A"

    @Test
    fun `getWeather successful call`() = runBlockingTest {
        every { weatherResponseMapper.invoke(any()) } returns mockk()
        coEvery { currentWeatherService.getWeatherForCity(any()) } returns ApiResult.Success(
            mockk(),
            200
        )

        val result = subject.getWeather(input)
        coVerify { currentWeatherService.getWeatherForCity(input) }
        expectThat(result).isA<RepositoryResult.Success<CityWeatherInformation>>()
    }

    @Test
    fun `getWeather failed call`() = runBlockingTest {
        every { weatherResponseMapper.invoke(any()) } returns mockk()
        coEvery { currentWeatherService.getWeatherForCity(any()) } returns ApiResult.Fail(401)

        val result = subject.getWeather(input)

        expectThat(result).isA<RepositoryResult.Fail.NetworkFail<*, *>>()
        verify { weatherResponseMapper wasNot Called }
    }
}