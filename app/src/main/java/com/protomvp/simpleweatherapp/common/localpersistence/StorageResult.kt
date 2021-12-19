package com.protomvp.simpleweatherapp.common.localpersistence


sealed class StorageResult<T> {
    data class Success<T>(val value: T) : StorageResult<T>()
    data class Fail(val exception: Exception) : StorageResult<Nothing>()
}

fun <T> StorageResult<T>.success(block: (StorageResult.Success<T>) -> Unit): StorageResult<T> {
    if (this is StorageResult.Success) block(this)
    return this
}

fun <T> StorageResult<T>.fail(block: (StorageResult.Fail) -> Unit): StorageResult<T> {
    if (this is StorageResult.Fail) block(this)
    return this
}