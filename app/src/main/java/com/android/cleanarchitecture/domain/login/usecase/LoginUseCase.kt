package com.android.cleanarchitecture.domain.login.usecase

import com.android.cleanarchitecture.data.login.model.request.LoginRequest
import com.android.cleanarchitecture.data.login.model.request.LoginResponse
import com.android.cleanarchitecture.domain.base.BaseResponse
import com.android.cleanarchitecture.domain.login.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend fun execute(loginRequest: LoginRequest): Flow<BaseResponse<LoginResponse>> {
        return loginRepository.login(loginRequest)
    }
}