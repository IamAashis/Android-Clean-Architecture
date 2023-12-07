package com.android.cleanarchitecture.domain.base

import com.android.cleanarchitecture.utils.enums.Status

/**
 * Created by Aashis on 05,December,2023
 */
sealed class BaseResponse<T>(val data: T?, val status: Status, val throwable: Throwable?) {
    class Success<T>(data: T?) : BaseResponse<T>(data, Status.SUCCESS, null)
    data class Error<T>(val error: Throwable?) : BaseResponse<T>(null, Status.ERROR, error)
}