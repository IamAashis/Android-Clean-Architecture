package com.android.cleanarchitecture.domain.login.repository

import com.android.cleanarchitecture.data.login.model.request.LoginRequest
import com.android.cleanarchitecture.data.login.model.request.LoginResponse
import com.android.cleanarchitecture.domain.base.BaseResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by Aashis on 04,December,2023
 */
interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest): Flow<BaseResponse<LoginResponse>>
}