package com.android.cleanarchitecture.domain.login.repository

import com.android.cleanarchitecture.data.common.utils.WrappedResponse
import com.android.cleanarchitecture.data.login.remote.dto.LoginRequest
import com.android.cleanarchitecture.data.login.remote.dto.LoginResponse
import com.android.cleanarchitecture.domain.base.BaseResult
import com.android.cleanarchitecture.domain.login.model.LoginEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Aashis on 04,December,2023
 */
interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, WrappedResponse<LoginResponse>>>
}