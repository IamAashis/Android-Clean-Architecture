package com.android.cleanarchitecture.data.common.network

import com.android.cleanarchitecture.data.common.utils.WrappedResponse
import com.android.cleanarchitecture.data.login.remote.dto.LoginRequest
import com.android.cleanarchitecture.data.login.remote.dto.LoginResponse
import com.android.cleanarchitecture.data.randomQuotes.model.RandomQuotesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<WrappedResponse<LoginResponse>>

    @GET("random")
    suspend fun randomQuotes(
//        @Url url: String
    ): Response<List<RandomQuotesResponse>>
}