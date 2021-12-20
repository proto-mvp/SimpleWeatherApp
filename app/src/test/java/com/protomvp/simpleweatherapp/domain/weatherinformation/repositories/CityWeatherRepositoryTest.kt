package com.protomvp.simpleweatherapp.domain.weatherinformation.repositories

import com.protomvp.simpleweatherapp.common.domain.RepositoryResult
import com.protomvp.simpleweatherapp.common.network.ApiErrorCodes
import com.protomvp.simpleweatherapp.common.network.ApiResult
import com.protomvp.simpleweatherapp.domain.weatherinformation.model.CityWeatherInformation
import com.protomvp.simpleweatherapp.domain.weatherinformation.model.mappers.WeatherResponseMapper
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.localsource.CachedWeatherInfoService
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource.CurrentWeatherService
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA

@ExperimentalCoroutinesApi
class CityWeatherRepositoryTest {
    private lateinit var subject: CityWeatherRepository
    private val currentWeatherService: CurrentWeatherService = mockk()
    private val weatherResponseMapper: WeatherResponseMapper = mockk()
    private val cachedWeatherInfoService: CachedWeatherInfoService = mockk()

    @Before
    fun setUp() {
        subject = CityWeatherRepository(
            currentWeatherService,
            weatherResponseMapper,
            cachedWeatherInfoService
        )
        coEvery { cachedWeatherInfoService.save(any()) } returns RepositoryResult.Success(Unit)
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
        expectThat(result).isA<RepositoryResult.Success<CityWeatherInformation>>()

        coVerify { currentWeatherService.getWeatherForCity(input) }
        coVerify { cachedWeatherInfoService.save(any()) }
    }

    @Test
    fun `getWeather failed call and it is not a network error`() = runBlockingTest {
        every { weatherResponseMapper.invoke(any()) } returns mockk()
        coEvery { currentWeatherService.getWeatherForCity(any()) } returns ApiResult.Fail(401)

        val result = subject.getWeather(input)

        expectThat(result).isA<RepositoryResult.Fail.NetworkFail<*, *>>()
        verify { weatherResponseMapper wasNot Called }
        verify { cachedWeatherInfoService wasNot Called }
    }

    @Test
    fun `getWeather failed call and it is  a network error`() = runBlockingTest {

        every { weatherResponseMapper.invoke(any()) } returns mockk()
        coEvery { currentWeatherService.getWeatherForCity(any()) } returns ApiResult.Fail(
            ApiErrorCodes.NoNetwork
        )
        coEvery { cachedWeatherInfoService.read() } returns RepositoryResult.Success(mockk())

        val result = subject.getWeather(input)

        expectThat(result).isA<RepositoryResult.Success<CityWeatherInformation>>()
        coVerify { currentWeatherService.getWeatherForCity(input) }
        coVerify { cachedWeatherInfoService.save(any()) wasNot Called }
    }

    @Test
    fun `getWeather by lat lng successful call`() = runBlockingTest {
        every { weatherResponseMapper.invoke(any()) } returns mockk()
        coEvery {
            currentWeatherService.getWeatherForLatLng(
                any(),
                any()
            )
        } returns ApiResult.Success(
            mockk(),
            200
        )

        val result = subject.getWeather(input, input)
        expectThat(result).isA<RepositoryResult.Success<CityWeatherInformation>>()

        coVerify { currentWeatherService.getWeatherForLatLng(input, input) }
        coVerify { cachedWeatherInfoService.save(any()) }
    }
}