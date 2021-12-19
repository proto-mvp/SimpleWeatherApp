package com.protomvp.simpleweatherapp.domain.favouritecities

import com.protomvp.simpleweatherapp.common.domain.UseCase
import com.protomvp.simpleweatherapp.common.domain.UseCaseResult
import com.protomvp.simpleweatherapp.common.domain.success
import javax.inject.Inject

class AddFavouritePlaceUseCase @Inject constructor(

) : UseCase {
    suspend fun execute(): UseCaseResult<Unit> {
        return success(Unit)
    }
}