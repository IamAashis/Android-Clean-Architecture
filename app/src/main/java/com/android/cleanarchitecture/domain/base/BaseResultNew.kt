package com.android.cleanarchitecture.domain.base

import com.android.cleanarchitecture.util.enums.Status

/**
 * Created by Aashis on 05,December,2023
 */
sealed class BaseResultNew<T>(val data: T?, val status: Status, val throwable: Throwable?) {
    class Success<T>(data: T?) : BaseResultNew<T>(data, Status.SUCCESS, null)
    data class Error<T>(val error: Throwable?) : BaseResultNew<T>(null, Status.ERROR, error)
}