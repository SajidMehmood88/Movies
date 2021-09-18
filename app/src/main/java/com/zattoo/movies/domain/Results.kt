package com.zattoo.movies.domain

sealed class Results<out T : Any>
data class Success<out T : Any>(val data: T) : Results<T>()
data class Failure(val error: Int?) : Results<Nothing>()

inline fun <T : Any> Results<T>.onSuccess(action: (T) -> Unit): Results<T> {
    if (this is Success) action(data)
    return this
}

inline fun <T : Any> Results<T>.onError(action: (Int) -> Unit) {
    if (this is Failure && error != null) action(error)
}

inline fun <T : Any, R : Any> Results<T>.mapOnSuccess(map: (T) -> R) = when (this) {
    is Success -> Success(map(data))
    is Failure -> this
}

fun <T : Any> Results<T>.dataOrNull(): T? {
    return (this as? Success)?.data
}