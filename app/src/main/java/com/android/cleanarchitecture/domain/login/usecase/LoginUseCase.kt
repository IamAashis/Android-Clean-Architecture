package com.android.cleanarchitecture.domain.login.usecase

import com.android.cleanarchitecture.data.common.utils.WrappedResponse
import com.android.cleanarchitecture.data.login.remote.dto.LoginRequest
import com.android.cleanarchitecture.data.login.remote.dto.LoginResponse
import com.android.cleanarchitecture.domain.base.BaseResult
import com.android.cleanarchitecture.domain.login.repository.LoginRepository
import com.android.cleanarchitecture.domain.login.model.LoginEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend fun execute(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, WrappedResponse<LoginResponse>>> {
        return loginRepository.login(loginRequest)
    }
}