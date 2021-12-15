package com.protomvp.simpleweatherapp.common.domain

import com.protomvp.simpleweatherapp.common.network.ApiResult

interface Repository

sealed class RepositoryResult<T> {
    data class Success<T>(val value: T) : RepositoryResult<T>()
    sealed class Fail {
        data class NetworkFail<T, F>(val fail: ApiResult.Fail<F>) : RepositoryResult<T>()
        data class GenericError<T>(val message: String) : RepositoryResult<T>()
    }
}

fun <T> Repository.success(value: T): RepositoryResult<T> = RepositoryResult.Success(value)

fun <T, F> Repository.networkFail(netFail: ApiResult.Fail<F>): RepositoryResult<T> =
    RepositoryResult.Fail.NetworkFail(netFail)