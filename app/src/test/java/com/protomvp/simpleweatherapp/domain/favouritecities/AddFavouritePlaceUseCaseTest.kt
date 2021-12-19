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

class AddFavouritePlaceUseCaseTest {
    private val favouritesRepository: FavouritesRepository = mockk()

    private lateinit var subject: AddFavouritePlaceUseCase

    @Before
    fun setUp() {
        subject = AddFavouritePlaceUseCase(favouritesRepository)
    }

    @Test
    fun `execute invokes repository`() = runBlockingTest {
        val city = "test"

        coEvery { favouritesRepository.add(any()) } returns RepositoryResult.Success(Unit)

        val result = subject.execute(city)

        expectThat(result).isA<UseCaseResult.Success<Unit>>()
        coVerify { favouritesRepository.add(city) }
    }
}