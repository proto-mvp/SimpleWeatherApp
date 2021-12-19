package com.protomvp.simpleweatherapp.domain.favouritecities

import com.protomvp.simpleweatherapp.common.domain.RepositoryResult
import com.protomvp.simpleweatherapp.common.domain.UseCaseResult
import com.protomvp.simpleweatherapp.domain.favouritecities.repositories.FavouritesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo

class GetFavouritePlacesUseCaseTest {

    private val favouritesRepository: FavouritesRepository = mockk()

    private lateinit var subject: GetFavouritePlacesUseCase

    @Before
    fun setUp() {
        subject = GetFavouritePlacesUseCase(favouritesRepository)
    }

    @Test
    fun `execute invokes repository`() = runBlockingTest {
        val cities = listOf("A", "B")
        coEvery { favouritesRepository.getCities() } returns RepositoryResult.Success(cities)

        val result = subject.execute()

        expectThat(result).isA<UseCaseResult.Success<List<String>>>().and {
            get { this.value } isEqualTo cities
        }
        coVerify { favouritesRepository.getCities() }
    }
}

