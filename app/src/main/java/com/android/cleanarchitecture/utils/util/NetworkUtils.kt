package com.android.cleanarchitecture.utils.util

import com.android.cleanarchitecture.domain.base.BaseResponse
import java.io.IOException

/**
 * Created by Aashis on 12,December,2023
 */
suspend fun <T: Any> safeApiCall(
    call: suspend () -> BaseResponse<T>,
    errorMessage: String
): BaseResponse<T>{
    return try {
        call()
    }catch (e: Exception){
        BaseResponse.Error(IOException(errorMessage, e))
    }
}