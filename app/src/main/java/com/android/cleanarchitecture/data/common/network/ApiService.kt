package com.android.cleanarchitecture.data.common.network

import com.android.cleanarchitecture.data.common.utils.WrappedResponse
import com.android.cleanarchitecture.data.login.model.request.LoginRequest
import com.android.cleanarchitecture.data.login.model.request.LoginResponse
import com.android.cleanarchitecture.data.randomQuotes.model.RandomQuotesOnlyResponse
import com.android.cleanarchitecture.data.randomQuotes.model.RandomQuotesResponse
import com.android.cleanarchitecture.domain.base.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<WrappedResponse<LoginResponse>>

    @GET("random")
    suspend fun randomQuotes(
//        @Url url: String
    ): Response<List<RandomQuotesResponse>>

    @GET()
    suspend fun randomQuotes2(
        @Url url: String
    ): Response<RandomQuotesOnlyResponse>

}