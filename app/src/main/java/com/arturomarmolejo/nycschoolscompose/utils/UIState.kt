package com.arturomarmolejo.nycschoolscompose.utils

/**
 * Sealed Class [UIState] -
 * Defines the states of the UI depending on the API response
 */

sealed class UIState<out T> {
    object LOADING: UIState<Nothing>()
    data class SUCCESS<T>(val response: T): UIState<T>()
    data class ERROR(val error: Exception): UIState<Nothing>()
}
