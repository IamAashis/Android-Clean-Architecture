package com.android.cleanarchitecture.utils.exceptions

import com.android.cleanarchitecture.domain.base.BaseResponse


fun <T> BaseResponse<T>.onSuccess(action: (T?) -> Unit): BaseResponse<T> {
    if (this is BaseResponse.Success) {
        action(data)
    }
    return this
}

fun <T> BaseResponse<T>.onFailure(action: (Throwable?) -> Unit): BaseResponse<T> {
    if (this is BaseResponse.Error) {
        action(error)
    }
    return this
}