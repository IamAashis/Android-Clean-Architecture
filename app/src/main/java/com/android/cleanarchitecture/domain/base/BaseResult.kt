package com.android.cleanarchitecture.domain.base


/**
 * Created by Aashis on 04,December,2023
 */
sealed class BaseResult<out T : Any, out U : Any> {
    data class Success<T : Any>(val data: T?) : BaseResult<T, Nothing>()
    data class SuccessList<T : Any>(val data: List<T>?) : BaseResult<T, Nothing>()
    data class Error<U : Any>(val rawResponse: U) : BaseResult<Nothing, U>()
}