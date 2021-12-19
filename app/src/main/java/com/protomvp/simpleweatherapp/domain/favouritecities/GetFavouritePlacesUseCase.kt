package com.protomvp.simpleweatherapp.domain.favouritecities

import com.protomvp.simpleweatherapp.common.domain.UseCase
import com.protomvp.simpleweatherapp.common.domain.UseCaseResult
import com.protomvp.simpleweatherapp.common.domain.success
import javax.inject.Inject

class GetFavouritePlacesUseCase @Inject constructor(

) : UseCase {
    suspend fun execute(): UseCaseResult<List<String>> {
        return success(
            listOf()
//            listOf(
//                "London",
//                "Paris",
//                "Athens",
//                "Budapest",
//                "London",
//                "Paris",
//                "Athens",
//                "Budapest"
//            )

        )
    }
}