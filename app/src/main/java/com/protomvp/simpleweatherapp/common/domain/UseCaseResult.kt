package com.protomvp.simpleweatherapp.common.domain

interface UseCase

sealed class UseCaseResult<T> {
    data class Success<T>(val value: T) : UseCaseResult<T>()
    sealed class Fail {
        data class NetworkRepositoryFailure<T>(
            val responseCode: Int,
        ) : UseCaseResult<T>()

        class RepositoryFailure<T> : UseCaseResult<T>()
    }
}

fun <T> UseCase.success(value: T): UseCaseResult<T> = UseCaseResult.Success(value)

fun <T> UseCase.failure(failure: RepositoryResult.Fail.NetworkFail<*, *>): UseCaseResult<T> =
    UseCaseResult.Fail.NetworkRepositoryFailure(failure.fail.responseCode)

fun <T> UseCase.generalFailure(failure: RepositoryResult.Fail): UseCaseResult<T> =
    UseCaseResult.Fail.RepositoryFailure()

fun <T> UseCase.generalError(failure: RepositoryResult.Fail.GenericError<T>): UseCaseResult<T> =
    UseCaseResult.Fail.RepositoryFailure()


suspend fun <T> UseCaseResult<T>.onSuccess(block: suspend (T) -> Unit): UseCaseResult<T> {
    if (this is UseCaseResult.Success) {
        block(this.value)
    }
    return this
}

suspend fun <T> UseCaseResult<T>.onFail(block: suspend () -> Unit): UseCaseResult<T> {
    if (this !is UseCaseResult.Success) {
        block()
    }
    return this
}