package com.protomvp.simpleweatherapp.common.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import kotlinx.serialization.Serializable
import java.nio.channels.UnresolvedAddressException

object ApiErrorCodes {
    const val NoNetwork = 10_000
    const val InternalError = 20_000
}

sealed class ApiResult<T> {
    data class Success<T>(val value: T, val responseCode: Int) : ApiResult<T>()
    data class Fail<T>(val responseCode: Int) : ApiResult<T>()
}

fun <T> ApiResult<T>.success(block: (ApiResult.Success<T>) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) block(this)
    return this
}

fun <T> ApiResult<T>.fail(block: (ApiResult.Fail<T>) -> Unit): ApiResult<T> {
    if (this is ApiResult.Fail) block(this)
    return this
}

suspend fun ApiResult.Fail<*>.noNetwork(block: suspend () -> Unit) {
    if (this.responseCode == ApiErrorCodes.NoNetwork) {
        block()
    }
}

suspend inline fun <reified T> HttpResponse.result(): ApiResult<T> {
    return if (this.status.isSuccess()) {
        val result = this.receive<T>()
        ApiResult.Success(result, status.value)
    } else {
        ApiResult.Fail(status.value)
    }
}

suspend inline fun <reified T> HttpClient.getRequest(endPointUrl: String): ApiResult<T> {
    return this.use {
        it.get<HttpResponse> {
            url(endPointUrl)
            contentType(ContentType.Application.Json)
            body = NoContent()
        }.result()
    }
}

@Serializable
class NoContent : OutgoingContent.NoContent() {
    override val contentType: ContentType
        get() = ContentType.Application.Json
}

suspend inline fun <T : Any, reified R> HttpClient.postRequest(
    endPointUrl: String,
    value: T
): ApiResult<R> {
    return this.use {
        it.post<HttpResponse> {
            url(endPointUrl)
            contentType(ContentType.Application.Json)
            body = value
        }.result()
    }
}

suspend fun <T> catchNetworkError(block: suspend () -> ApiResult<T>) = try {
    block()
} catch (exception: Exception) {
    if (exception is UnresolvedAddressException) {
        ApiResult.Fail(ApiErrorCodes.NoNetwork)
    } else
        ApiResult.Fail(ApiErrorCodes.InternalError)
}