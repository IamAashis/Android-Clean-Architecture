package com.android.cleanarchitecture.data.login.repository

import com.android.cleanarchitecture.data.common.network.ApiService
import com.android.cleanarchitecture.data.login.model.request.LoginRequest
import com.android.cleanarchitecture.data.login.model.request.LoginResponse
import com.android.cleanarchitecture.domain.base.BaseResponse
import com.android.cleanarchitecture.domain.login.repository.LoginRepository
import com.android.cleanarchitecture.domain.login.model.LoginEntity
import com.android.cleanarchitecture.presentation.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Aashis on 04,December,2023
 */
class LoginRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    LoginRepository, BaseRepository() {
    override suspend fun login(loginRequest: LoginRequest): Flow<BaseResponse<LoginResponse>> {
        return flow {
            try {
                val response = apiService.login(loginRequest)
                if (response.isSuccessful) {
                    val body = response.body()!!
                    val loginEntity = LoginEntity(
                        body.data?.id ?: 0,
                        body.data?.name ?: "",
                        body.data?.email ?: "",
                        body.data?.token ?: ""
                    )
                    emit(BaseResponse.Success(body.data))
                } else {
                    emit(
                        BaseResponse.Error(
                            getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    )
                }
            } catch (e: Exception) {
                emit(BaseResponse.Error(getError(e)))
            }
        }
    }
}