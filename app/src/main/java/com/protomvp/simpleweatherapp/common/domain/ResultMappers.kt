package com.protomvp.simpleweatherapp.common.domain

import com.protomvp.simpleweatherapp.common.network.ApiResult

inline fun <reified T, reified F> Repository.repositoryResult(
    mapper: (F) -> T = {
        require(it is T) { "Default Mapper didn't work. Please provide explicit mapper of ${F::class.simpleName} to ${T::class.simpleName}" }
        it
    },
    block: () -> ApiResult<F>,

    ): RepositoryResult<T> {
    return when (val result = block()) {
        is ApiResult.Success -> success(mapper(result.value))
        is ApiResult.Fail -> networkFail(result)
    }
}

inline fun <reified T> UseCase.useCaseResult(
    block: () -> RepositoryResult<T>
): UseCaseResult<T> {
    return when (val result = block()) {
        is RepositoryResult.Success -> success(result.value)
        is RepositoryResult.Fail -> generalFailure(result)
        is RepositoryResult.Fail.NetworkFail<*, *> -> failure(result)
        is RepositoryResult.Fail.GenericError -> generalError(result)
    }
}
