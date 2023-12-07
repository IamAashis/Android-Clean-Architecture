package com.android.cleanarchitecture.data.randomQuotes.repository

import com.android.cleanarchitecture.data.common.network.ApiService
import com.android.cleanarchitecture.data.common.utils.WrappedResponse
import com.android.cleanarchitecture.data.randomQuotes.model.RandomQuotesResponse
import com.android.cleanarchitecture.domain.base.BaseResult
import com.android.cleanarchitecture.domain.randomQuotes.repository.RandomQuotesRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Aashis on 04,December,2023
 */
class RandomQuotesRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    RandomQuotesRepository {
    override suspend fun getRandomQuotes(): Flow<BaseResult<RandomQuotesResponse, WrappedResponse<List<RandomQuotesResponse>>>> {
        return flow {
            val response = apiService.randomQuotes(/*"https://api.quotable.io/quotes/random"*/)
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.SuccessList(body))
            } else {
                val type = object : TypeToken<WrappedResponse<RandomQuotesResponse>>() {}.type
                val err: WrappedResponse<RandomQuotesResponse> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.code = response.code()
//                emit(BaseResult.Error(err))
            }
        }
    }
}