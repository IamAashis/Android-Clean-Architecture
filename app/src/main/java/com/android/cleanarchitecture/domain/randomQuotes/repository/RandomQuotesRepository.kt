package com.android.cleanarchitecture.domain.randomQuotes.repository

import com.android.cleanarchitecture.data.common.utils.WrappedResponse
import com.android.cleanarchitecture.data.randomQuotes.model.RandomQuotesResponse
import com.android.cleanarchitecture.domain.base.BaseResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by Aashis on 05,December,2023
 */
interface RandomQuotesRepository {
//    suspend fun getRandomQuotes(): Flow<BaseResult<LoginEntity, WrappedResponse<RandomQuotesResponse>>>

    suspend fun getRandomQuotes(): Flow<BaseResult<RandomQuotesResponse, WrappedResponse<List<RandomQuotesResponse>>>>
}