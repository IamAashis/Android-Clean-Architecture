package com.android.cleanarchitecture.data.randomQuotes.repository

import android.util.Log
import com.android.cleanarchitecture.data.common.network.ApiService
import com.android.cleanarchitecture.data.common.utils.WrappedResponse
import com.android.cleanarchitecture.data.randomQuotes.model.RandomQuotesResponse
import com.android.cleanarchitecture.domain.base.BaseResponse
import com.android.cleanarchitecture.domain.base.ErrorResponse
import com.android.cleanarchitecture.domain.randomQuotes.repository.RandomQuotesRepository
import com.android.cleanarchitecture.presentation.base.BaseRepository
import com.android.cleanarchitecture.utils.constants.ErrorConstants
import com.android.cleanarchitecture.utils.constants.HttpCodeConstants
import com.android.cleanarchitecture.utils.exceptions.UnAuthorizedException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Created by Aashis on 04,December,2023
 */
class RandomQuotesRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    RandomQuotesRepository, BaseRepository() {
    override suspend fun getRandomQuotes(): Flow<BaseResponse<List<RandomQuotesResponse>>> {
        return flow {
            try {
                val response = apiService.randomQuotes(/*"https://api.quotable.io/quotes/random"*/)
                if (response.isSuccessful) {
                    val body = response.body()!!
                    emit(BaseResponse.Success(body))
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

