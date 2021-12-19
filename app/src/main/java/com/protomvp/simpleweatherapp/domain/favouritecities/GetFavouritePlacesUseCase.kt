package com.protomvp.simpleweatherapp.domain.favouritecities

import com.protomvp.simpleweatherapp.common.domain.UseCase
import com.protomvp.simpleweatherapp.common.domain.UseCaseResult
import com.protomvp.simpleweatherapp.common.domain.useCaseResult
import com.protomvp.simpleweatherapp.domain.favouritecities.repositories.FavouritesRepository
import javax.inject.Inject

class GetFavouritePlacesUseCase @Inject constructor(
    private val favouritesRepository: FavouritesRepository,
) : UseCase {
    suspend fun execute(): UseCaseResult<List<String>> = useCaseResult {
        favouritesRepository.getCities()
    }
}