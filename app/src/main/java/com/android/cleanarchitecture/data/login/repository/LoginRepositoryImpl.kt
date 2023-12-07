package com.android.cleanarchitecture.data.login.repository

import com.android.cleanarchitecture.data.common.network.ApiService
import com.android.cleanarchitecture.data.common.utils.WrappedResponse
import com.android.cleanarchitecture.data.login.remote.dto.LoginRequest
import com.android.cleanarchitecture.data.login.remote.dto.LoginResponse
import com.android.cleanarchitecture.domain.login.repository.LoginRepository
import com.android.cleanarchitecture.domain.login.model.LoginEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Aashis on 04,December,2023
 */
class LoginRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, WrappedResponse<LoginResponse>>> {
        return flow {
            val response = apiService.login(loginRequest)
            if (response.isSuccessful) {
                val body = response.body()!!
                val loginEntity = LoginEntity(
                    body.data?.id ?: 0,
                    body.data?.name ?: "",
                    body.data?.email ?: "",
                    body.data?.token ?: ""
                )
                emit(BaseResult.Success(loginEntity))
            } else {
                val type = object : TypeToken<WrappedResponse<LoginResponse>>() {}.type
                val err: WrappedResponse<LoginResponse> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.code = response.code()
                emit(BaseResult.Error(err))
            }
        }
    }
}